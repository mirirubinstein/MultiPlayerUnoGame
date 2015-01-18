package unoGame.gui;

import javax.swing.*;
import java.awt.*;

public class TopCardPanel extends JPanel {
    JButton cardPile = new JButton();

    public TopCardPanel(Color color, String sign) {
        setTopCard(color, sign);
        cardPile.setPreferredSize(new Dimension(100, 150));
        cardPile.setEnabled(false);
        cardPile.setFont(new Font("verdana", Font.BOLD, 18));
        add(cardPile);
        setVisible(true);
    }


    public void setTopCard(Color color, String sign) {
        cardPile.setText(sign);
        cardPile.setBackground(color);
    }
}
