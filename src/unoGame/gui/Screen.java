package unoGame.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import schwimmer.multichat.SocketOutStream;
import unoGame.Card;
import unoGame.messages.ScreenShot;

public class Screen extends JFrame {

	private TopCardPanel topCardPanel;
	private PickCardsPanel pickCardsPanel;
	private AllPlayersPanel allPlayersPanel;
	private PlayersCardsPanel playersCardsPanel;
	private JPanel currentPlayer;
	private JLabel player;
	private UpdatesPanel updatesPanel;
	private boolean isMyTurn;
	private SocketOutStream socket;
	private int myPlayerIndex;

	public Screen(ScreenShot screenShot, String playersName, SocketOutStream socket) {
		this.socket = socket;

		this.setTitle((playersName));

		topCardPanel = new TopCardPanel(screenShot.getTopCard().getColor().getColor(), screenShot.getTopCard()
				.numberToString());

		if (screenShot.getCurrentPlayerIndex() == screenShot.getMyPlayerIndex()) {
			isMyTurn = true;

		} else {
			isMyTurn = false;
		}

		PickCardsActionListener pickCardsActionListener = new PickCardsActionListener(socket);

		pickCardsPanel = new PickCardsPanel(isMyTurn, pickCardsActionListener);

		allPlayersPanel = new AllPlayersPanel(screenShot.getPlayersInfo(), screenShot.isInAscendingOrder());

		updatesPanel = new UpdatesPanel();

		playersCardsPanel = new PlayersCardsPanel(isMyTurn, screenShot, socket);

		currentPlayer = new JPanel();
		player = new JLabel(screenShot.getPlayersInfo()[screenShot.getCurrentPlayerIndex()].name + "\'s turn");
		player.setFont(new Font("verdana", Font.BOLD, 25));

		topCardPanel.setBounds(500, 300, 250, 300);
		pickCardsPanel.setBounds(300, 300, 250, 300);
		allPlayersPanel.setBounds(100, 0, 800, 1800);
		playersCardsPanel.setBounds(0, 0, 900, 200);

		setLayout(new BorderLayout());
		currentPlayer.setBounds(450, 200, 200, 50);
		currentPlayer.add(player);
		currentPlayer.setPreferredSize(new Dimension(400, 200));
		currentPlayer.setVisible(true);

		add(currentPlayer);
		add(topCardPanel);
		add(pickCardsPanel, BorderLayout.CENTER);
		add(allPlayersPanel);
		add(updatesPanel, BorderLayout.EAST);
		add(playersCardsPanel, BorderLayout.SOUTH);

		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public void showDisconnected() {
		setVisible(false);
	}

	public void update(ScreenShot screenShot) {
		isMyTurn = false;
		if (screenShot.getCurrentPlayerIndex() == myPlayerIndex) {
			isMyTurn = true;
			pickCardsPanel.update(isMyTurn);
			topCardPanel.setTopCard(screenShot.getTopCard().getColor().getColor(), screenShot.getTopCard()
					.numberToString());
			allPlayersPanel.update(screenShot.getPlayersInfo(), screenShot.isInAscendingOrder(),
					screenShot.getCurrentPlayerIndex());
			playersCardsPanel.update(screenShot.getMyCards(), screenShot.getTopCard());

			playersCardsPanel.enableCards(isMyTurn);

			if (screenShot.isDrawCard()) {
				updatesPanel.update(screenShot.getCurrentPlayer().getName(), "drew a card");
			}
			if (screenShot.isPlayedCard()) {
				updatesPanel.update(screenShot.getCurrentPlayer().getName(), screenShot.getTopCard().toUpdatesString());
			}
			if (screenShot.isCalledUno()) {
				updatesPanel.update("Someone", "uno");
			}

			player.setText(screenShot.getPlayersInfo()[screenShot.getCurrentPlayerIndex()].getName() + "\'s turn");
		} else {
			pickCardsPanel.update(isMyTurn);
			topCardPanel.setTopCard(screenShot.getTopCard().getColor().getColor(), screenShot.getTopCard()
					.numberToString());
			allPlayersPanel.update(screenShot.getPlayersInfo(), screenShot.isInAscendingOrder(),
					screenShot.getCurrentPlayerIndex());
			playersCardsPanel.enableCards(isMyTurn);

			if (screenShot.isDrawCard()) {
				updatesPanel.update(screenShot.getCurrentPlayer().getName(), "drew a card");
			}
			if (screenShot.isPlayedCard()) {
				updatesPanel.update(screenShot.getCurrentPlayer().getName(), screenShot.getTopCard().toUpdatesString());
			}
			if (screenShot.isCalledUno()) {
				updatesPanel.update("Someone", "uno");
			}

			player.setText(screenShot.getPlayersInfo()[screenShot.getCurrentPlayerIndex()].getName() + "\'s turn");

		}

	}

	public void setMyPlayerIndex(int length) {
		myPlayerIndex = length;

	}
}
