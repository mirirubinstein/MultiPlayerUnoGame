package schwimmer.multichat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;

import unoGame.EmptyPileException;
import unoGame.Game;
import unoGame.Player;
import unoGame.messages.ScreenShot;
import unoGame.messages.UnoMessageFactory;

public class SocketHandler extends Thread {

	private Socket s;
	private Queue<String> messages;
	private SocketEventListener listener;
	private Game game;

	
	public SocketHandler( Socket s, SocketEventListener listener, Queue<String> messages, Game game) {
		this.s = s;
		this.messages = messages;
		this.listener = listener;
		this.game = game;
		listener.onConnect(s);
	}
	
	public void run() {
		
		try {
			InputStream in = s.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			
			String line;
			while( (line = reader.readLine()) != null ) {
				listener.onMessage(s, line);
				messages.add(line);
				UnoMessageFactory factory = new UnoMessageFactory();
				game.addPlayer(factory.getMessage(line));
			
				
			}
			
		} catch (IOException e) {
			listener.onDisconnect(s);
			e.printStackTrace();
		}
		
		
	}
	
}
