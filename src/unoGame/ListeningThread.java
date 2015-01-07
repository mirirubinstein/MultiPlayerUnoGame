package unoGame;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListeningThread extends Thread{
	private Socket socket;


	
	public ListeningThread(Socket socket){
		this.socket = socket;
	
		
	}

	@Override
	public void run() {
		try {
			
			//InputStream in = socket.getInputStream();
			//BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			ObjectInputStream s = new ObjectInputStream(socket.getInputStream());
			

			Object o;
			while ((o = (Object)s.readObject()) != null) {
				System.out.println("Thread: ");
				System.out.println(o.toString());
				//what to do with incoming lines
				//must make protocol
				
			}
			
			
			
		} catch (IOException | ClassNotFoundException ex) {
			System.err.println(ex);
		}
		
	}
}
