package schwimmer.multichat;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import unoGame.Game;

public class MultiChatServer {

	private ServerSocket serverSocket;
	private List<SocketStream> sockets;
	private BlockingQueue<Object> messages;
	private MessageSenderThread sender;
	private SocketEventListener listener;
	private Game game;
	
	public MultiChatServer(int port, SocketEventListener listener) throws IOException {
		serverSocket = new ServerSocket(port);
		sockets = new ArrayList<SocketStream>();
		messages = new LinkedBlockingQueue<Object>();
		game = new Game();
		sender = new MessageSenderThread(sockets, messages, listener, game);
		sender.start();
		this.listener = listener;
		listener.onServerStart(serverSocket);
	
	}

	public void run() {

		try {
			while (true) {
				Socket socket = serverSocket.accept();
				sockets.add(new SocketStream(socket, new ObjectOutputStream(socket.getOutputStream())));
				SocketHandler handler = new SocketHandler(
						socket, listener, messages, game);
				handler.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

}
