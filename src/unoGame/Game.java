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

	public Game() {
		turn = 1;
		reverse = false;
		nextPlayerSkip = false;
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
	private void playersTurn(Player player){
		player.pickCard(deck.dealCard());
		// need to figure out how were going to play a turn
		
	}

}
