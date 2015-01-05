package unoGame;

import java.util.ArrayList;

public class Player {
	private ArrayList<Card> hand;
	private String name;
	private boolean calledUno;

	public Player(String name) {
		this.name = name;
		hand = new ArrayList<Card>();
	}

	public Card getThisCardFromHand(int c) {
		// return a specific card from hand
		return hand.remove(c);
	}

	public Card seeThisCardFromHand(int c) {
		return hand.get(c);
	}

	public void pickCard(Card c) {
		// receives a card and puts it into the hand after all the cards.
		hand.add(c);
	}

	public int getNumCardsInHand() {
		return hand.size();
	}

	public PlayerBasicInfo returnBasicInfo() {
		return new PlayerBasicInfo(name, hand.size(), calledUno);
	}

	public void callUno() {
		calledUno = true;
	}

	public String getName() {
		return name;
	}

	public Card[] getHand() {
		Card cards[] = new Card[hand.size()];
		int i = 0;
		for(Card c: hand){
			cards[i] = c;
			i++;
		}
		return cards;
	}

}