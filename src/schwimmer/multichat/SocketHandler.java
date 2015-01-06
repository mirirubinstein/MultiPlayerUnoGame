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
	private Queue<Object> messages;
	private SocketEventListener listener;
	private Game game;

	
	public SocketHandler( Socket s, SocketEventListener listener, Queue<Object> messages, Game game) {
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
			
				Player p = game.getPlayer(factory.getMessage(line));
				
				//need to set screenshot fields and send it
				ScreenShot s = new ScreenShot();
					s.myCards = p.getHand();
				//	s.playersInfo
				    s.currentPlayerIndex = 0;
				    s.topCard = game.getPlayingPile().peek();
				    s.isInAscendingOrder = game.isReverse();
				    s.myPlayerIndex = game.getPlayers().size();
				    
				   messages.add(s);
				   
			}
			
		} catch (IOException | EmptyPileException e) {
			listener.onDisconnect(s);
			e.printStackTrace();
		}
		
		
	}
	
}
