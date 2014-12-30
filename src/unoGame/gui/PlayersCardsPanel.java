package unoGame.gui;

import unoGame.Card;
import unoGame.CardColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PlayersCardsPanel extends JPanel {
    private JPanel cardsPanel = new JPanel();
    JPanel cardUnoPanel = new JPanel();
    private JScrollPane CardsScroller = new JScrollPane();
    private ActionListener actionListener;
    private boolean isMyTurn;

    public PlayersCardsPanel(boolean isMyTurn, Card[] cards, ActionListener actionListener) {
        this.isMyTurn = isMyTurn;
        this.actionListener = actionListener;
        setCards(cards);
    }

    public void setCards(Card[] cards) {
        addCardsToPanel(cards);
        CardsScroller.getViewport().add(cardsPanel);
        CardsScroller.setPreferredSize(new Dimension(750, 180));
        CardsScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        CardsScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        JButton uno = new JButton("UNO");
        uno.setPreferredSize(new Dimension(100, 100));
        uno.setFont(new Font("Verdana", Font.BOLD, 16));

        cardUnoPanel.add(uno);
        cardUnoPanel.add(CardsScroller);

        add(cardUnoPanel);
        uno.addActionListener(actionListener);
        setVisible(true);
    }

    private void addCardsToPanel(Card[] cards) {
        for (Card card : cards) {
            cardsPanel.add(createCardButton(card));
        }
    }

    private JButton createCardButton(Card card) {
        JButton cardButton = new JButton();
        cardButton.addActionListener(actionListener);
        cardButton.setForeground(Color.BLACK);
        cardButton.setText(card.numberToString());
        cardButton.setEnabled(isMyTurn);
        cardButton.setFont(new Font("Verdana", Font.BOLD, 24));
        cardButton.setBackground(card.getColor().getColor());
        if (card.getColor().equals(CardColor.WILD)) {
            cardButton.setForeground(Color.WHITE);
        }
        cardButton.setPreferredSize(new Dimension(100, 150));
        cardButton.setVisible(true);
        return cardButton;
    }

    public void update(Card[] cards) {
        //logic is not right needs to be changed
        //not handeled uno button
        cardsPanel.removeAll();
        addCardsToPanel(cards);
        cardsPanel.revalidate();
    }
}
