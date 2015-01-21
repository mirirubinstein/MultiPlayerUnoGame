package schwimmer.multichat;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketOutStream {
	private Socket socket;
	private ObjectOutputStream out;

	public SocketOutStream(Socket socket, ObjectOutputStream out) {
		this.socket = socket;
		this.out = out;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

}
