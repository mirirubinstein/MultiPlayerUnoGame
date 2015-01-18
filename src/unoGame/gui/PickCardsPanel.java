package unoGame.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PickCardsPanel extends JPanel {
    private JButton pick;

    public PickCardsPanel(boolean isMyTurn, ActionListener PickCardsActionListener) {
        pick= new JButton("Draw Cards");
        pick.setPreferredSize(new Dimension(100, 150));
        pick.setEnabled(isMyTurn);
        pick.addActionListener(PickCardsActionListener);
        pick.setFont(new Font("verdana", Font.BOLD, 18));
        add(pick);
        setVisible(true);
    }

    public void update(boolean isMyTurn) {
        pick.setEnabled(isMyTurn);
    }
}