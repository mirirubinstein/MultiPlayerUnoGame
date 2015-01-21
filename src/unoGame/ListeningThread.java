package unoGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import unoGame.gui.EnterGameScreen;
import unoGame.gui.Screen;
import unoGame.messages.ScreenShot;

public class ListeningThread extends Thread {
	private Socket socket;
	private EnterGameScreen screen;
	private boolean newGame;
	private Screen playingScreen;

	public ListeningThread(Socket socket, EnterGameScreen screen) {
		this.socket = socket;
		this.screen = screen;
		this.newGame = true;

	}

	@Override
	public void run() {
		try {

			ObjectInputStream s = new ObjectInputStream(socket.getInputStream());

			Object o;
			ScreenShot ss;
			Card c;
			while ((o = (Object) s.readObject()) != null) {
				ss = (ScreenShot) o;
				if (newGame) {
					playingScreen = screen.switchToPlayingGameJFrame(ss);
					newGame = false;
				} else {
					playingScreen.update(ss);
				}

			}

		} catch (IOException | ClassNotFoundException ex) {
			System.err.println(ex);
		}

	}
}
