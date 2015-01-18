package unoGame.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import schwimmer.multichat.SocketOutStream;

public class PickCardsActionListener implements ActionListener {
	private SocketOutStream socket;

    public PickCardsActionListener(SocketOutStream socket){
    	this.socket = socket;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // what happens when a player picks a card
   
		try {
			
		
		ObjectOutputStream out = socket.getOut();
		String message = "DRAW \n";
		out.writeObject(message);
		out.flush();// flush the stream so that the data gets sent\
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
