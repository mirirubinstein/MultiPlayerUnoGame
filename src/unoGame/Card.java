package unoGame;

import java.awt.Color;

public class Card {

	final static int SKIP = 10;
	final static int REVERSE = 11;
	final static int DRAWTWO = 12;
	final static int DRAWFOUR = 13; // wild draw four
	final static int WILD = 14;

	private CardColor cardColor; // red, blue, green, yellow, black
	private int number; // 0 - 14

	public Card(CardColor cardColor, int number) {
		this.cardColor = cardColor;
		this.number = number;
	}

	public CardColor getColor() {
		return cardColor;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return "Card [cardType=" + cardColor + ", number=" + number + "]";
	}

	public boolean canPlay(Card otherCard) {
		if(cardColor == otherCard.getColor()){
			return true;
		}else if(number == otherCard.getNumber()){
			return true;			
		}else if(cardColor == CardColor.WILD){//wild or wild draw four
			return true;
		}else{
			return false;
		}
	}

}