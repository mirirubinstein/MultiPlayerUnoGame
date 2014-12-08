package unoGame;

import java.awt.Color;

public class Card {
	private CardType cardType; //red, blue, green, yellow, WILD
	private int number; // 0- 9
	
	public Card(CardType cardType, int number){
		this.cardType = cardType;
		this.number = number;
	}

	public CardType getType() {
		return cardType;
	}


	public int getNumber() {
		return number;
	}

	
	
	@Override
	public String toString() {
		return "Card [cardType=" + cardType + ", number=" + number + "]";
	}

	public boolean canPlay(Card otherCard){
		if(number > otherCard.getNumber()){
			return true;
		}else if(otherCard.getType() == CardType.WILD){
			return true;
		}else{
			return false;
		}
	}

}
