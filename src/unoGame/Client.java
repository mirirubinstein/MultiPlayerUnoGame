package unoGame;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket socket;
	private OutputStream out;
	private Thread thread;

	

	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 3773);
		//socket = new Socket("192.168.117.107", 3773);
		out = socket.getOutputStream();
		thread = new ListeningThread(socket);
		thread.start();
		
	}
	public void sendMessage(String message) throws IOException {
		out.write(message.toString().getBytes());
		out.flush();
	//	System.out.println(message);

	}
	
}

