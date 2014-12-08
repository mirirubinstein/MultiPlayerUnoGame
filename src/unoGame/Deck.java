package unoGame;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	List<Card> cards;
	
	
	public Deck(){
		//set up deck
		cards = new ArrayList<Card>();
		
		
		for(int i = 0; i < 3; i++){
			//1 card for 0
			cards.add(new Card(CardType.values()[i], 0));
		}
		
		
			for(int j = 0; j < 3; j++ ){
				for(int k = 1; k < 9; k++){
				cards.add(new Card(CardType.values()[j], k));
				cards.add(new Card(CardType.values()[j], k));
				//2 cards for all numbers 1-9
			}
	}
	}
	
	// need to set up wild cards and reverse, draw 2, skip etc
	
	//void shuffle method
	
	
	//boolean hasNext
	
	//Card getTop
	
	

}
