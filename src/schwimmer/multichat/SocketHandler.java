package schwimmer.multichat;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
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

			while ((line = (String) objectIn.readObject()) != null && !game.isGameOver()) {
				listener.onMessage(s.getSocket(), line);

				UnoMessageFactory factory = new UnoMessageFactory();
				if (factory.getMessage(line).trim().equals("DRAW")) {
					if (game.getDeck().isEmpty()) {
						Card c = game.getPlayingPile().pop();
						game.getDeck().resetDeck(game.getPlayingPile());
						game.getPlayingPile().push(c);
					}
					Card c = game.getDeck().dealCard(game.getPlayingPile());
					// add card to players hand
					game.getPlayers().get(game.getTurn()).pickCard(c);
					game.getPlayers().get(game.getTurn()).setCalledUno(false);
					// need to refresh screen data
					sendScreenShot(false, false);

					game.nextTurn();

					sendScreenShot(false, true);
				} else if (factory.getMessage(line).split(" ")[0].trim()
						.equals("UNO")) {
					String numPlayer = factory.getMessage(line).split(" ")[1];
					Player p2 = game.getPlayers().get(
							Integer.parseInt(numPlayer));
					
					if (p2.getNumCardsInHand() == 1) {
						System.out.println("caaled uno");
						p2.callUno();
					} else {
						List<Player> allPlayers = game.getPlayers();
						for (int i = 0; i < allPlayers.size(); i++) {
							if (allPlayers.get(i).getNumCardsInHand() == 1
									&& !allPlayers.get(i).getCalledUno()) {
								int currentTurn = game.getTurn();
								allPlayers.get(i).pickCard(
										game.getDeck().dealCard(
												game.getPlayingPile()));
								allPlayers.get(i).pickCard(
										game.getDeck().dealCard(
												game.getPlayingPile()));
								game.setTurn(i);
								sendScreenShot(false, false);
								game.setTurn(currentTurn);
								sendScreenShot(false, false);
							}
						}
					}
				} else if (factory.getMessage(line).split(" ")[0].trim()
						.equals("PLAY_CARD")) {
					String color = factory.getMessage(line).split(" ")[1];
					String number = factory.getMessage(line).split(" ")[2];

					Card c = new Card(stringToColor(color),
							Integer.parseInt(number));

					game.getPlayers().get(game.getTurn()).removeCardFromHand(c);
					sendScreenShot(false, false);

					game.getPlayingPile().push(c);

					if(game.getPlayers().get(game.getTurn()).getNumCardsInHand() == 0){
						game.setWinner(game.getTurn());
						game.setGameOver(true);
						//send info to action panel
					}

					switch (Integer.parseInt(number)) {

					case 10:// skip next turn
						game.setNextPlayerSkip(true);
						game.nextTurn();
						sendScreenShot(false, false);
						game.setNextPlayerSkip(false);
						break;
					case 11:// reverse
						game.setReverse(!game.getReverse());
						game.nextTurn();
						break;
					case 12:// draw 2
						game.draw(2);
						game.nextTurn();
						sendScreenShot(false, false);
						game.nextTurn();
						game.setNextPlayerSkip(false);
						break;
					case 13:// draw 4
						int currentTurn = game.getTurn();
						game.draw(4);
						game.nextTurn();
						sendScreenShot(false, false);
						game.setTurn(currentTurn);
						game.setNextPlayerSkip(true);
						break;
					case 14:
						// nothing, go again
						break;
					default:
						game.nextTurn();
						game.setNextPlayerSkip(false);
						break;
					}

					sendScreenShot(true, false);
					

				} else {
					game.addPlayer(factory.getMessage(line));

					// send screen shot of last player that joined
					Player p2 = game.getPlayers().get(
							game.getPlayers().size() - 1);
					shot = getScreenShotData(p2);
					messages.add(shot);
					sendScreenShot(false, false);
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

	public void sendScreenShot(boolean played, boolean draw)
			throws EmptyPileException {
		Player p = game.getPlayers().get(game.getTurn());
		ScreenShot shot = getScreenShotData(p);
		shot.drawCard = draw;
		shot.playedCard = played;
		messages.add(shot);
	}
}
