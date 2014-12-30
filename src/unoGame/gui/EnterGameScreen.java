package unoGame.gui;

import unoGame.controllers.GameController;
import unoGame.controllers.ScreenController;
import unoGame.messages.ScreenShot;
import unoGame.gameViews.EnteringGameView;
import unoGame.gameViews.PlayingGameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterGameScreen extends JFrame implements EnteringGameView {

    private GameController controller;

    //UI Components
    JButton join = new JButton("   Join   ");
    JLabel master = new JLabel("Master Name :");
    JLabel playerName = new JLabel("Your Name :");
    JTextField masterName = new JTextField(15);
    JTextField name = new JTextField(15);

    public EnterGameScreen(GameController controller) {
        this.controller = controller;
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

    @Override
    public PlayingGameView switchToPlayingGameView(ScreenController screenController, ScreenShot screenShot) {
        PlayingGameView view = new Screen(screenController, screenShot);
        setVisible(false);
        return view;
    }

    public void showScreen() {
        controller.bind(this);
        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.join(masterName.getText(), name.getText());
                join.setEnabled(false);
                join.setText("Please wait...");
            }
        });
        setVisible(true);
    }
}
