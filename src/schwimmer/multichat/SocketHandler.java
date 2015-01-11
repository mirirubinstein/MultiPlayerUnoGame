package schwimmer.multichat;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Queue;

import unoGame.Card;
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
	private ScreenShot shot;

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
			while ((line = (String) objectIn.readObject()) != null) {
				listener.onMessage(s.getSocket(), line);

				UnoMessageFactory factory = new UnoMessageFactory();
				if (factory.getMessage(line).trim().equals("DRAW")) {
					Card c = game.getDeck().dealCard();
				//	add card to players hand
					game.getPlayers().get(game.getTurn()).pickCard(c);
				//	System.out.println("My Cards: " + game.getPlayers().get(game.getTurn()).getHand()[6] + "\n" + game.getPlayers().get(game.getTurn()).getHand()[7]);
					//need to refresh screen data
					Player p = game.getPlayers().get(game.getTurn());
					shot = getScreenShotData(p);
					messages.add(shot);
					game.nextTurn();
					
				} else {
					game.addPlayer(factory.getMessage(line));
				}
				Player p = game.getPlayers().get(game.getTurn());

				// need to set screenshot fields and send it
				shot = getScreenShotData(p);

				messages.add(shot);

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
		s.myPlayerIndex = game.getPlayers().size()-1;

		ArrayList<Player> players = game.getPlayers();
		PlayerBasicInfo[] list = new PlayerBasicInfo[players.size()];
		for (int i = 0; i < players.size(); i++) {
			list[i] = new PlayerBasicInfo(players.get(i).getName(), players
					.get(i).getNumCardsInHand(), players.get(i).getCalledUno());
		}
		s.playersInfo = list;
		return s;
	}

}
