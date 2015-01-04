package unoGame.gui;

import unoGame.messages.ScreenShot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterGameScreen extends JFrame {


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

        //if join successful needs to get a screenShot from the server

        //for now im creating my own screenshot
        ScreenShot screenShot = new ScreenShot();
        switchToPlayingGameJFrame(screenShot, playerName);
    }
}
