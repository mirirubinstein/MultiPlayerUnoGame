package unoGame.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import schwimmer.multichat.SocketOutStream;
import unoGame.Card;
import unoGame.CardColor;
import unoGame.messages.ScreenShot;

public class PlayersCardsPanel extends JPanel {
    private JPanel cardsPanel = new JPanel();
    JPanel cardUnoPanel = new JPanel();
    private JScrollPane CardsScroller = new JScrollPane();
    private ActionListener actionListener;
    private boolean isMyTurn;
    private ScreenShot screenShot;
    private SocketOutStream socket;

    public PlayersCardsPanel(boolean isMyTurn, ScreenShot screenShot, SocketOutStream socket) {

        this.isMyTurn = isMyTurn;
        this.socket = socket;
        this.screenShot = screenShot; 
        setCards(screenShot.getMyCards(), screenShot.getTopCard());
        setOpaque(false);
        
        
        
    }

    public void setCards(Card[] cards, Card top) {
    	  
        addCardsToPanel(cards, top);
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
        uno.addActionListener(new UnoActionListener(socket, screenShot.getPlayersInfo().length-1));
        setVisible(true);
    }

    private void addCardsToPanel(Card[] cards, Card topCard) {
        for (Card card : cards) {
            cardsPanel.add(createCardButton(card, topCard));
          
        }
    }

    private JButton createCardButton(Card card, Card topCard) {
        JButton cardButton = new JButton();
        cardButton.setForeground(Color.BLACK);
        cardButton.setText(card.numberToString());
        cardButton.setEnabled(isMyTurn);
        cardButton.setFont(new Font("Verdana", Font.BOLD, 10));
        cardButton.setBackground(card.getColor().getColor());
        if (card.getColor().equals(CardColor.BLACK)) {
            cardButton.setForeground(Color.WHITE);
        }
        cardButton.setPreferredSize(new Dimension(100, 150));
        cardButton.setVisible(true);
        cardButton.addActionListener(new PlayCardActionListener(socket, card, topCard));
        return cardButton;
    }
    

    public void update(Card[] cards, Card topCard) {
        //logic is not right needs to be changed
        //not handeled uno button
        cardsPanel.removeAll();
        addCardsToPanel(cards, topCard);
        cardsPanel.revalidate();
        cardsPanel.repaint();
     
    }
    	public void enableCards(boolean turn){
    	   int numCards = cardsPanel.getComponentCount();
           for(int i = 0; i < numCards; i++){
           	cardsPanel.getComponent(i).setEnabled(turn);
           	
           }
    }
}
