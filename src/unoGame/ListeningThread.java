package unoGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import unoGame.gui.EnterGameScreen;
import unoGame.messages.ScreenShot;

public class ListeningThread extends Thread {
	private Socket socket;
	private EnterGameScreen screen;

	public ListeningThread(Socket socket, EnterGameScreen screen) {
		this.socket = socket;
		this.screen = screen;

	}

	@Override
	public void run() {
		try {

			// InputStream in = socket.getInputStream();
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(in));
			ObjectInputStream s = new ObjectInputStream(socket.getInputStream());
		
			Object o;
			ScreenShot ss;
			Card c;
			while ((o = (Object) s.readObject()) != null) {
				System.out.println("Thread: " + o.getClass());
				
				System.out.println(o.toString());
				if(o instanceof ScreenShot){
					ss = (ScreenShot)o;
					screen.switchToPlayingGameJFrame(ss, "playerName");//need to get actual name
				}else if(o instanceof Card){
					c = (Card)o;
					System.out.println("CARD: " + c);
				}
				

			}

		} catch (IOException | ClassNotFoundException ex) {
			System.err.println(ex);
		}

	}
}
