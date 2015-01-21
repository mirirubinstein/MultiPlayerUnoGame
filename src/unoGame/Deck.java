package unoGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
	private List<Card> cards;
	final static int CARDS_IN_DECK = 108;

	public Deck() {
		// set up deck
		cards = new ArrayList<Card>(CARDS_IN_DECK);

		for (int i = 0; i < CardColor.values().length - 1; i++) {
			// 1 card in each color for number 0
			cards.add(new Card(CardColor.values()[i], 0));
		}

		for (int j = 0; j < CardColor.values().length - 1; j++) {
			for (int k = 1; k < Card.SKIP; k++) {
				cards.add(new Card(CardColor.values()[j], k));
				cards.add(new Card(CardColor.values()[j], k));
				// 2 cards in each color for all numbers 1-9
			}
		}

		for (int j = 0; j < CardColor.values().length - 1; j++) {
			for (int k = Card.SKIP; k < Card.DRAWFOUR; k++) {
				cards.add(new Card(CardColor.values()[j], k));
				cards.add(new Card(CardColor.values()[j], k));
				// 2 cards in each color for skip, reverse, draw two
			}
		}

		// these special cards won't be identified by color, since don't want to
		// add color.black as enum value, don't want to iterate when creating
		// rest of deck
		for (int j = 0; j < CardColor.values().length - 1; j++) {
			for (int k = Card.DRAWFOUR; k <= Card.WILD; k++) {
				cards.add(new Card(CardColor.values()[4], k));
				// 4 cards for drawFour, wild
			}
		}

	}

	// void shuffle method
	public void shuffle() {
		Random numGenerator = new Random();
		for (int i = 0; i < cards.size(); i++) {
			int newPosition = numGenerator.nextInt(CARDS_IN_DECK);
			Card tempCard = cards.get(i);
			cards.set(i, cards.get(newPosition));
			cards.set(newPosition, tempCard);
		}
	}

	// boolean hasNext
	public boolean hasNext() {
		return cards.get(cards.size() - 1) != null;
	}

	// deal top card
	public Card dealCard(CardPile pile) {
		Card card = null;
		if (this.isEmpty()) {
			try {
				resetDeck(pile);
			} catch (EmptyPileException e) {
				// couldn't pop
			}
		}
		if (cards.size() == 0) {
			return null;
		}
		card = cards.remove(cards.size() - 1);

		return card;

	}

	public void resetDeck(CardPile pile) throws EmptyPileException {
		if (pile.getElements().size() > 1) {
			while (pile.getElements().size() > 1) {
				cards.add(pile.pop());
			}
		}
	}

	public boolean isEmpty() {
		// can't wait till its 0 in case someone has a draw 4
		return cards.size() <= 4;
	}

	public String toString() {
		StringBuilder data = new StringBuilder("\nDeck");
		for (int i = 0; i < cards.size(); i++) {
			data.append("\n");
			data.append(cards.get(i).toString());
		}
		return data.toString();
	}

	public int getLength() {
		return cards.size();
	}
}
