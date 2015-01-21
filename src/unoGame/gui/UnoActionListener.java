package unoGame.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import schwimmer.multichat.SocketOutStream;

public class UnoActionListener implements ActionListener {
	private SocketOutStream socket;
	private int currentPlayerIndex;

	public UnoActionListener(SocketOutStream socket, int currentPlayerIndex) {
		this.socket = socket;
		this.currentPlayerIndex = currentPlayerIndex;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			ObjectOutputStream out = socket.getOut();
			String message = "UNO " + currentPlayerIndex + "\n";
			out.writeObject(message);
			out.flush();// flush the stream so that the data gets sent\

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
