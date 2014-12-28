package unoGame;

import java.awt.Color;

public class Game {
	private final int NUM_PLAYERS = 4; // WE NEED TO FIGURE OUT HOW TO DEAL WITH
										// THIS
	// max number of players should be 5
	private Player players[] = new Player[NUM_PLAYERS];
	private static Deck deck = new Deck();// pick from here and when empty
	private static CardPile playingPile = new CardPile();

	private int turn;
	private boolean reverse;
	private boolean nextPlayerSkip;
	private boolean gameOver;
	private int winner;
	
	public Game() {
		turn = 0;
		nextPlayerSkip = false;
		reverse = false;
		gameOver = false;
		
		for (int i = 0; i < NUM_PLAYERS; i++) {
			players[i] = new Player(); // construct each new player in the array
		}

		for (int i = 0; i < 7; i++) // deal out 7 cards to each player
		{
			for (int j = 0; j < NUM_PLAYERS; j++) {
				players[j].pickCard(deck.dealCard());
			}
		}
		// get base card to start with
		do {
			playingPile.push(deck.dealCard());
			// lays down cards until the first card to play on is a regular
			// number card.
		} while ((deck.dealCard().getColor().equals(Color.BLACK))
				|| (deck.dealCard().getNumber() > 9));
		
	}
	
	public void play() {
		do {
			if (reverse) {
				playersTurn(players[--turn % NUM_PLAYERS]);
			}
			if (nextPlayerSkip) {
				playersTurn(players[turn+=2 % NUM_PLAYERS]);
			}
			playersTurn(players[turn++ % NUM_PLAYERS]);
		} while (!gameOver);
	}
	
	private void playersTurn(Player player){
		player.pickCard(deck.dealCard());
		// need to figure out how were going to play a turn
		//player chooses card and puts on deck.
		Card cardChosen = player.seeThisCardFromHand(0);//parameter is the index of the card in array list of cards that the player selected to place in pile.
		//chose 0 for now so error isn't thrown
		
		try {
			if (cardChosen.getColor() == CardColor.WILD
					|| cardChosen.getColor() == playingPile.peek().getColor()
					|| cardChosen.getNumber() == playingPile.peek().getNumber()) {// valid move
				
				playingPile.push(player.getThisCardFromHand(0));// replace parameter with index of
																// card in player hand pile
			
				if (player.getNumCardsInHand() == 0) {// have winner
					gameOver = true;
					winner = turn;
					return;// break out of method
				}
				// no winner yet
				
				switch (cardChosen.getNumber()) {
				case Card.SKIP:
					nextPlayerSkip = true;
					break;
				case Card.REVERSE:
					reverse = !reverse;
					break;
				case Card.DRAWFOUR:
						draw(4);
					break;
				case Card.DRAWTWO:
						draw(2);
					break;
				default:
					break;
				}
			} else {
				//move was invalid 
			}
		} catch (EmptyPileException e) {
			playingPile.push(deck.dealCard());
		}
	}
	
	public void draw(int drawCards){
		for (int i = 0; i < drawCards; i++) {
			if(reverse){
				players[turn-1 % NUM_PLAYERS].pickCard(deck.dealCard());
			}
			else{
				players[turn+1 % NUM_PLAYERS].pickCard(deck.dealCard());
			}
		}
	}

}