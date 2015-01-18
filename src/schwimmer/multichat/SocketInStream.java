package schwimmer.multichat;

import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketInStream {
	private Socket socket;
	private ObjectInputStream in;

	public SocketInStream(Socket socket, ObjectInputStream in) {
		this.socket = socket;
		this.in = in;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

}
