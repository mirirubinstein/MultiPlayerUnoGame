package unoGame;

import java.util.ArrayList;
import java.util.List;

public class CardPile {

	private List<Card> elements;

	public CardPile() {
		elements = new ArrayList<Card>();
	}

	public boolean isEmpty() {
		return elements.size() == 0;
	}

	public void push(Card newItem) {
		elements.add(newItem);
	}

	public Card pop() throws EmptyPileException {
		if (isEmpty()) {
			throw new EmptyPileException("Pile is empty, can not pop");
		} else {
			return elements.remove(elements.size() - 1);
		}
	}

	public Card peek() throws EmptyPileException {
		if (isEmpty()) {
			throw new EmptyPileException("Pile is empty, can not peek");
		} else {
			return elements.get(elements.size() - 1);
		}
	}

	public List<Card> getElements() {
		return elements;
	}

	public void setElements(List<Card> elements) {
		this.elements = elements;
	}

	public void reset(Card card) {
		elements = new ArrayList<Card>();
		elements.add(card);
	}

}
