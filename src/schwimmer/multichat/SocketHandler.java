package schwimmer.multichat;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import unoGame.Card;
import unoGame.CardColor;
import unoGame.EmptyPileException;
import unoGame.Game;
import unoGame.Player;
import unoGame.PlayerBasicInfo;
import unoGame.messages.ScreenShot;

public class SocketHandler extends Thread {

	private SocketInStream s;
	private Queue<Object> messages;
	private SocketEventListener listener;
	private Game game;
	private int MAX_PLAYERS = 5;

	public SocketHandler(SocketInStream socket, SocketEventListener listener, Queue<Object> messages, Game game) {
		this.s = socket;
		this.messages = messages;
		this.listener = listener;
		this.game = game;
		listener.onConnect(socket.getSocket());
	}

	public void run() {

		try {

			ObjectInputStream objectIn = s.getIn();

			String line;
			Player p;
			ScreenShot shot;
			Scanner scanner;

			while ((line = (String) objectIn.readObject()) != null && !game.isGameOver()
					&& game.getNUM_PLAYERS() <= MAX_PLAYERS - 1) {
				listener.onMessage(s.getSocket(), line);
				Player currentPlayer = null;
				try {
					currentPlayer = game.getPlayers().get(game.getTurn());
				} catch (Exception e) {

				}
				scanner = new Scanner(line);
				String message = scanner.next();

				switch (message) {
				case "DRAW":
					Card c = game.getDeck().dealCard(game.getPlayingPile());
					if (c == null) {
						break;// can't deal card because deck is empty, don't do
						// anything
					}
					// add card to players hand

					game.getPlayers().get(game.getTurn()).pickCard(c);
					game.getPlayers().get(game.getTurn()).setCalledUno(false);
					sendScreenShot(false, false, null, false);
					Player current1 = game.getPlayers().get(game.getTurn());
					game.nextTurn();

					sendScreenShot(false, true, current1, false);
					break;
				case "UNO":
					String numPlayer = scanner.next();
					Player p2 = game.getPlayers().get(Integer.parseInt(numPlayer));

					if (p2.getNumCardsInHand() == 1) {
						p2.callUno();
					} else {
						List<Player> allPlayers = game.getPlayers();
						for (int i = 0; i < allPlayers.size(); i++) {
							if (allPlayers.get(i).getNumCardsInHand() == 1 && !allPlayers.get(i).getCalledUno()) {
								int currentTurn = game.getTurn();
								allPlayers.get(i).pickCard(game.getDeck().dealCard(game.getPlayingPile()));
								allPlayers.get(i).pickCard(game.getDeck().dealCard(game.getPlayingPile()));
								game.setTurn(i);
								sendScreenShot(false, false, null, false);
								game.setTurn(currentTurn);
								sendScreenShot(false, false, null, false);
							}
						}
					}
					break;
				case "PLAY_CARD":
					String color = scanner.next();
					String number = scanner.next();

					Card c1 = new Card(stringToColor(color), Integer.parseInt(number));

					game.getPlayers().get(game.getTurn()).removeCardFromHand(c1);
					sendScreenShot(false, false, null, false);

					game.getPlayingPile().push(c1);

					if (game.getPlayers().get(game.getTurn()).getNumCardsInHand() == 0) {
						game.setWinner(game.getTurn());
						game.setGameOver(true);
						// send info to action panel
					}
					int switcher = Integer.parseInt(number);
					switch (switcher) {

					case 10:// skip next turn
						game.setNextPlayerSkip(true);
						game.nextTurn();
						sendScreenShot(false, false, null, false);
						game.setNextPlayerSkip(false);
						break;
					case 11:// reverse
						game.setReverse(!game.getReverse());
						game.nextTurn();
						break;
					case 12:// draw 2
						game.draw(2);
						game.nextTurn();
						sendScreenShot(false, false, null, false);
						game.nextTurn();
						game.setNextPlayerSkip(false);
						break;
					case 13:// draw 4
						int currentTurn = game.getTurn();
						game.draw(4);
						game.nextTurn();
						sendScreenShot(false, false, null, false);
						game.setTurn(currentTurn);
						game.setNextPlayerSkip(true);
						break;
					case 14:
						// nothing, go again
						break;
					default:
						currentPlayer = game.getPlayers().get(game.getTurn());
						game.nextTurn();
						game.setNextPlayerSkip(false);
						break;
					}

					Boolean calledUno = false;
					for (Player player : game.getPlayers()) {
						if (player.getCalledUno()) {
							calledUno = true;
						}
					}
					sendScreenShot(true, false, currentPlayer, calledUno);

					break;
				case "NEWPLAYER":
					if (game.getNUM_PLAYERS() <= MAX_PLAYERS - 1) {
						game.addPlayer(scanner.next());

						// send screen shot of last player that joined
						Player p3 = game.getPlayers().get(game.getPlayers().size() - 1);
						shot = getScreenShotData(p3);
						messages.add(shot);
						sendScreenShot(false, false, null, false);

					}
					break;
				}

			}

		} catch (Exception e) {
			listener.onDisconnect(s.getSocket());
			e.printStackTrace();
		}

	}

	private ScreenShot getScreenShotData(Player p) throws EmptyPileException {
		ScreenShot s = new ScreenShot();
		s.setMyCards(p.getHand());
		s.setCurrentPlayerIndex(game.getTurn());
		s.setTopCard(game.getPlayingPile().peek());
		s.setInAscendingOrder(game.isReverse());
		s.setMyPlayerIndex(game.getPlayers().size() - 1);

		ArrayList<Player> players = game.getPlayers();
		PlayerBasicInfo[] list = new PlayerBasicInfo[players.size()];
		for (int i = 0; i < players.size(); i++) {
			list[i] = new PlayerBasicInfo(players.get(i).getName(), players.get(i).getNumCardsInHand(), players.get(i)
					.getCalledUno());
		}
		s.setPlayersInfo(list);
		return s;
	}

	public CardColor stringToColor(String c) {
		String names[] = { "BLACK", "BLUE", "GREEN", "RED", "YELLOW" };
		CardColor colors[] = { CardColor.BLACK, CardColor.BLUE, CardColor.GREEN, CardColor.RED, CardColor.YELLOW };

		for (int i = 0; i < names.length; i++) {
			if (c.equals(names[i])) {
				return colors[i];
			}
		}
		return null;
	}

	public void sendScreenShot(boolean played, boolean draw, Player currentPlayer, boolean calledUno)
			throws EmptyPileException {
		Player p = game.getPlayers().get(game.getTurn());
		ScreenShot shot = getScreenShotData(p);
		shot.setDrawCard(draw);
		shot.setPlayedCard(played);
		shot.setCurrentPlayer(currentPlayer);
		shot.setCalledUno(calledUno);
		messages.add(shot);
	}
}
