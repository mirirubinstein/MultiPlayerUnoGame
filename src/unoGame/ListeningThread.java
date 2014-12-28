package unoGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ListeningThread extends Thread{
	private Socket socket;


	
	public ListeningThread(Socket socket){
		this.socket = socket;
	
		
	}

	@Override
	public void run() {
		try {
			
			StringBuilder message = new StringBuilder("");
			
			
			InputStream in = socket.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			String line;
			while ((line = reader.readLine()) != null) {
				
				//what to do with incoming lines
				//must make protocol
				
			}
			
			
			
		} catch (IOException ex) {
			System.err.println(ex);
		}
		
	}
}
