package schwimmer.multichat;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Queue;

import unoGame.Game;
import unoGame.Player;
import unoGame.messages.ScreenShot;
import unoGame.messages.UnoMessageFactory;

public class SocketHandler extends Thread {

	private Socket s;
	private Queue<Object> messages;
	private SocketEventListener listener;
	private Game game;

	
	public SocketHandler( Socket socket, SocketEventListener listener, Queue<Object> messages, Game game) {
		this.s = socket;
		this.messages = messages;
		this.listener = listener;
		this.game = game;
		listener.onConnect(socket);
	}
	
	public void run() {
		
		try {
			//	InputStream in = s.getInputStream();
			//BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			ObjectInputStream objectIn = new ObjectInputStream(s.getInputStream());
	
			
			String line;
			while( (line = (String)objectIn.readObject()) != null ) {
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
			
		} catch (Exception e) {
			listener.onDisconnect(s);
			e.printStackTrace();
		}
		
		
	}
	
}
