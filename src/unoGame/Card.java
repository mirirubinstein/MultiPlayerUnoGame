package unoGame;

import java.awt.Color;
import java.io.Serializable;


public class Card implements Serializable{

	private static final long serialVersionUID = 1L;
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
    
    public String toUpdatesString() {
        return cardColor + " " + numberToString();
    }

    public String numberToString() {
        if (number > 9) {
            switch(number){
                case 10: return "SKIP";
                case 11: return "REVERSE";
                case 12: return "DRAW TWO";
                case 13: return "DRAW FOUR";
                case 14: return "WILD";

            }
        }
        return String.valueOf(number);
    }

    public boolean canPlay(Card otherCard) {
       if ( cardColor == otherCard.getColor() || number == otherCard.getNumber()|| otherCard.cardColor == CardColor.BLACK ||otherCard.cardColor == CardColor.BLACK ||cardColor == CardColor.BLACK )   {
    	   return true;
        } else {
            return false;
        }
    }
    public boolean equals(Card otherCard){
    	return cardColor.equals(otherCard.getColor()) && number == otherCard.getNumber();
    }

}