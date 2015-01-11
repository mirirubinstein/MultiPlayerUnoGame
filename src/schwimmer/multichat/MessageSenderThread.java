package schwimmer.multichat;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import unoGame.Game;

public class MessageSenderThread extends Thread {

	private List<SocketOutStream> socketStreams;
	private BlockingQueue<Object> messages;
	private SocketEventListener listener;
	private Game game;

	public MessageSenderThread(
			List<SocketOutStream> sockets, 
			BlockingQueue<Object> messages,
			SocketEventListener listener, Game game) {
		this.socketStreams = sockets;
		this.messages = messages;
		this.listener = listener;
		this.game = game;
	}

	public void run() {

		while ( true ) {

			try {
				Object message = messages.take();

				Iterator<SocketOutStream> iter = socketStreams.iterator();
				while ( iter.hasNext() ) {
					SocketOutStream socketStream = iter.next();
					try {
					//	OutputStream out = socketStream.getOut();
						ObjectOutputStream objectOut = socketStream.getOut();
						objectOut.writeObject(message);
						objectOut.flush();
						objectOut.reset();
					} catch (IOException e) {
						e.printStackTrace();
						iter.remove();
						listener.onDisconnect(socketStream.getSocket());
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}
	public Game getGame(){
		return game;
	}


}
