package schwimmer.multichat;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Queue;

import unoGame.Card;
import unoGame.CardColor;
import unoGame.EmptyPileException;
import unoGame.Game;
import unoGame.Player;
import unoGame.PlayerBasicInfo;
import unoGame.messages.ScreenShot;
import unoGame.messages.UnoMessageFactory;

public class SocketHandler extends Thread {

	private SocketInStream s;
	private Queue<Object> messages;
	private SocketEventListener listener;
	private Game game;

	public SocketHandler(SocketInStream socket, SocketEventListener listener,
			Queue<Object> messages, Game game) {
		this.s = socket;
		this.messages = messages;
		this.listener = listener;
		this.game = game;
		listener.onConnect(socket.getSocket());
	}

	public void run() {

		try {
			// InputStream in = s.getInputStream();
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(in));
			ObjectInputStream objectIn = s.getIn();

			String line;
			Player p;
			ScreenShot shot;

			while ((line = (String) objectIn.readObject()) != null) {
				listener.onMessage(s.getSocket(), line);

				UnoMessageFactory factory = new UnoMessageFactory();
				if (factory.getMessage(line).trim().equals("DRAW")) {
					Card c = game.getDeck().dealCard();
					// add card to players hand
					game.getPlayers().get(game.getTurn()).pickCard(c);
					// System.out.println("My Cards: " +
					// game.getPlayers().get(game.getTurn()).getHand()[6] + "\n"
					// + game.getPlayers().get(game.getTurn()).getHand()[7]);
					// need to refresh screen data
					sendScreenShot();

					game.nextTurn();

					sendScreenShot();

				} else if (factory.getMessage(line).split(" ")[0].trim()
						.equals("PLAY_CARD")) {
					String color = factory.getMessage(line).split(" ")[1];
					String number = factory.getMessage(line).split(" ")[2];
					
					Card c = new Card(stringToColor(color),
							Integer.parseInt(number));
				
					game.getPlayers().get(game.getTurn()).removeCardFromHand(c);
					sendScreenShot();

					game.getPlayingPile().push(c);
					
					switch (Integer.parseInt(number)) {

					// case 13:
					// game.nextTurn();
					// System.out.println("IT works!"+number);
					// break;
					default:
						game.nextTurn();
						break;

					}
					
					
					sendScreenShot();
					
				} else {
					game.addPlayer(factory.getMessage(line));
					
					// send screen shot of last player that joined
					Player p2 = game.getPlayers().get(
							game.getPlayers().size() - 1);
					shot = getScreenShotData(p2);
					messages.add(shot);
					sendScreenShot();
				}

			}

		} catch (Exception e) {
			listener.onDisconnect(s.getSocket());
			e.printStackTrace();
		}

	}

	private ScreenShot getScreenShotData(Player p) throws EmptyPileException {
		ScreenShot s = new ScreenShot();
		s.myCards = p.getHand();
		s.currentPlayerIndex = game.getTurn();
		s.topCard = game.getPlayingPile().peek();
		s.isInAscendingOrder = game.isReverse();
		s.myPlayerIndex = game.getPlayers().size() - 1;

		ArrayList<Player> players = game.getPlayers();
		PlayerBasicInfo[] list = new PlayerBasicInfo[players.size()];
		for (int i = 0; i < players.size(); i++) {
			list[i] = new PlayerBasicInfo(players.get(i).getName(), players
					.get(i).getNumCardsInHand(), players.get(i).getCalledUno());
		}
		s.playersInfo = list;
		return s;
	}

	public CardColor stringToColor(String c) {
		String names[] = { "BLACK", "BLUE", "GREEN", "RED", "YELLOW" };
		CardColor colors[] = { CardColor.BLACK, CardColor.BLUE,
				CardColor.GREEN, CardColor.RED, CardColor.YELLOW };

		for (int i = 0; i < names.length; i++) {
			if (c.equals(names[i])) {
				return colors[i];
			}
		}
		System.out.println("null card");
		return null;
	}

	public void sendScreenShot() throws EmptyPileException {
		Player p = game.getPlayers().get(game.getTurn());
		ScreenShot shot = getScreenShotData(p);
		messages.add(shot);
	}
}
