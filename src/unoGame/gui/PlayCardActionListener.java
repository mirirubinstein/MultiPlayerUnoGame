package unoGame.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import unoGame.Card;
import unoGame.messages.ScreenShot;


public class PlayCardActionListener implements ActionListener {
    private Card card;
    private JPanel panel;
    private Card topCard;

    public PlayCardActionListener(Card card, JPanel panel, ScreenShot screenShot) {
        this.card = card;
        this.panel = panel;
        this.topCard = screenShot.topCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if (canPlay(card, topCard)) {
            //TODO need to play the card
            button.setVisible(false);
            panel.remove(button);
            panel.revalidate();
        } else
            button.setToolTipText("You cannot play this card on " + topCard);
    }

    public boolean canPlay(Card playedCard, Card topCard) {
        if (playedCard.getNumber() == 14) {
            return true;
        }

        if (playedCard.getNumber() == topCard.getNumber()) {
            return true;
        }
        if (playedCard.getColor().equals(topCard.getColor())) {
            return true;
        }
        return false;
    }
}