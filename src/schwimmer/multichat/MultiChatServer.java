package schwimmer.multichat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import unoGame.Game;

public class MultiChatServer {

	private ServerSocket serverSocket;
	private List<Socket> sockets;
	private BlockingQueue<String> messages;
	private MessageSenderThread sender;
	private SocketEventListener listener;
	private Game game;
	
	public MultiChatServer(int port, SocketEventListener listener) throws IOException {
		serverSocket = new ServerSocket(port);
		sockets = new ArrayList<Socket>();
		messages = new LinkedBlockingQueue<String>();
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
				sockets.add(socket);
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
