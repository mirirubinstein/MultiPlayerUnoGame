package unoGame.gui;


import com.sun.xml.internal.bind.v2.TODO;
import unoGame.Card;
import unoGame.messages.ScreenShot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO need to create a game client

public class PlayCardListener implements ActionListener {
    private Card card;
    //private GameClient gameClient;
    private JPanel panel;
    private Card topCard;

    public PlayCardListener(Card card/*, GameClient gameClient*/, JPanel panel, ScreenShot screenShot) {
        this.card = card;
        //this.gameClient = gameClient;
        this.panel = panel;
        this.topCard = screenShot.topCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if (canPlay(card, topCard)) {
            //TODO need to play the card using game client
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