package unoGame;

import java.util.ArrayList;

public class Player {
	private ArrayList<Card> hand;

	public Player() {
		hand = new ArrayList<Card>();
	}

	public Card getThisCardFromHand(int c) {
		// return a specific card from hand
		return hand.remove(c);
	}

	public void pickCard(Card c) {
		// receives a card and puts it into the hand after all the cards.
		hand.add(c);
	}

	public int getNumCardsInHand() {
		return hand.size();
	}

}
