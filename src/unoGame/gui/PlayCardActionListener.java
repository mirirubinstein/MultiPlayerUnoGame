package unoGame.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import schwimmer.multichat.SocketOutStream;
import unoGame.Card;

public class PlayCardActionListener implements ActionListener {
	private SocketOutStream socket;
	private Card card;
	private Card topCard;

	public PlayCardActionListener(SocketOutStream socket, Card card, Card topCard) {
		this.socket = socket;
		this.card = card;
		this.topCard = topCard;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// what happens when a player picks a card
		if (topCard.canPlay(card)) {

			try {

				ObjectOutputStream out = socket.getOut();
				String message = "PLAY_CARD " + card.getColor() + " " + card.getNumber();
				out.writeObject(message);
				out.flush();// flush the stream so that the data gets sent\

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {

		}
	}
}