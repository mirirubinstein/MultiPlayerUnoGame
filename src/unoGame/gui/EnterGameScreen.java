package unoGame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import unoGame.messages.ScreenShot;

public class EnterGameScreen extends JFrame {
	//Socket components
	Socket socket;

    //UI Components
    JButton join = new JButton("   Join   ");
    JLabel master = new JLabel("Master Name :");
    JLabel playerName = new JLabel("Your Name :");
    JTextField masterName = new JTextField(15);
    JTextField name = new JTextField(15);
    

    public EnterGameScreen() {
        Panel panel = new Panel();
        masterName.setText("localhost");
        panel.add(master).setFont(new Font("verdana", Font.BOLD, 22));
        panel.add(masterName).setBounds(100, 100, 100, 100);
        panel.add(playerName).setFont(new Font("verdana", Font.BOLD, 22));
        name.setFont(new Font("verdana", Font.BOLD, 18));
        name.setPreferredSize(new Dimension(20, 30));
        masterName.setPreferredSize(new Dimension(20, 30));
        masterName.setFont(new Font("verdana", Font.BOLD, 18));
        panel.add(name);
        join.setFont(new Font("verdana", Font.BOLD, 22));
        panel.add(join).setSize(100, 500);

        setSize(330, 370);
        setLocationRelativeTo(null);
        panel.setBackground(new Color(225, 224, 229));
        add(panel);
    }

    public void switchToPlayingGameJFrame(ScreenShot screenShot, String playerName) {
        JFrame screen = new Screen(screenShot, playerName);
        setVisible(false);
    }

    public void showScreen(){
        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                join(masterName.getText(), name.getText());
                join.setEnabled(false);
                join.setText("Please wait...");
            }
        });
        setVisible(true);
    }

    public void join(String serverAddress, String playerName) {
        //need to join game
    	try {
		socket = new Socket(serverAddress, 3773);
		//send player data
		OutputStream out = socket.getOutputStream();
		String message = "NEWPLAYER " + playerName.toUpperCase() + "\n";
		out.write(message.getBytes());
		out.flush();// flush the stream so that the data gets sent\
		// receive game data... chayala u need to set up protocol
    	InputStream in = socket.getInputStream();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(in));
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
        //if join successful needs to get a screenShot from the server

        //for now im creating my own screenshot
      // ScreenShot screenShot = new ScreenShot();
      //  switchToPlayingGameJFrame(screenShot, playerName);
    }
}
