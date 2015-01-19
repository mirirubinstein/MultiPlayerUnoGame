package unoGame;

import java.awt.Color;
import java.util.ArrayList;

public class Game {
	private int NUM_PLAYERS;
	// max number of players should be 5
	// private Player players[] = new Player[NUM_PLAYERS];
	private ArrayList<Player> players = new ArrayList<Player>();
	private Deck deck = new Deck();// pick from here and when empty
	private CardPile playingPile = new CardPile();

	private int turn;
	private boolean reverse;
	private boolean nextPlayerSkip;
	private boolean gameOver;
	private int winner;

	public Game() throws EmptyPileException {
		NUM_PLAYERS = 0;
		turn = 0;
		nextPlayerSkip = false;
		reverse = false;
		gameOver = false;
		deck.shuffle();

		// for (int i = 0; i < NUM_PLAYERS; i++) {
		// players[i] = new Player(name); // construct each new player in the
		// array
		// }

		for (int i = 0; i < 7; i++) // deal out 7 cards to each player
		{
			for (int j = 0; j < NUM_PLAYERS; j++) {
				players.get(j).pickCard(deck.dealCard(playingPile));
			}
		}
		// get base card to start with
		do {
			playingPile.push(deck.dealCard(playingPile));
			// lays down cards until the first card to play on is a regular
			// number card.
		} while ((playingPile.peek().getColor().equals(Color.BLACK)) || (playingPile.peek().getNumber() > 9));
		// start game
		// playersTurn(players[turn % NUM_PLAYERS]);
	}

	/*
	 * public void play() { do { if (reverse) { playersTurn(players[--turn %
	 * NUM_PLAYERS]); } if (nextPlayerSkip) { playersTurn(players[turn+=2 %
	 * NUM_PLAYERS]); } playersTurn(players[turn++ % NUM_PLAYERS]); } while
	 * (!gameOver); }
	 */
	// RULES: http://unotips.org/pdf/official_rules.pdf
	private void playersTurn(Player player) {
		// reset nextPlayerSkip
		nextPlayerSkip = false;
		// check if deck is empty and reset it
		if (deck.isEmpty()) {
			try {
				Card topCard = playingPile.pop();
				deck.resetDeck(playingPile);
				playingPile.reset(topCard);
			} catch (EmptyPileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// need to figure out how were going to play a turn
		// player chooses card and puts on deck. or they draw a card
		player.pickCard(deck.dealCard(playingPile));
		// OR
		Card cardChosen = player.seeThisCardFromHand(0);// parameter is the
														// index of the card in
														// array list of cards
														// that the player
														// selected to place in
														// pile.
		// chose 0 for now so error isn't thrown

		try {
			if (cardChosen.canPlay(playingPile.peek())) {
				playingPile.push(player.getThisCardFromHand(0));// replace
																// parameter
																// with index of
																// card in
																// player hand
																// pile

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
				// move was invalid
			}
		} catch (EmptyPileException e) {
			playingPile.push(deck.dealCard(playingPile));
		}
		// pass control to the next player
		if (gameOver == false) {
			nextTurn();
		}

	}

	public void nextTurn() {//sets next turn;
		if (nextPlayerSkip == false) {
			if (!reverse) {
				turn = ++turn % NUM_PLAYERS;
				if (turn < 0)
				{
				    turn += NUM_PLAYERS;
				}
				} else {
				turn = --turn % NUM_PLAYERS;
				if (turn < 0)
				{
				    turn += NUM_PLAYERS;
				}
			}
		} else {
			if (!reverse) {
				turn = (turn + 2) % NUM_PLAYERS;
				if (turn < 0)
				{
				    turn += NUM_PLAYERS;
				}
			} else {
				turn = (turn - 2) % NUM_PLAYERS;
				if (turn < 0)
				{
				    turn += NUM_PLAYERS;
				}
			}
		}
	}
	
	public void draw(int drawCards) {
		int choose;
		if (!reverse) {
			choose = (turn + 1) % NUM_PLAYERS;
			if (choose < 0) {
				choose += NUM_PLAYERS;
			}
		} else {
			choose = (turn - 1) % NUM_PLAYERS;
			if (choose < 0) {
				choose += NUM_PLAYERS;
			}
		}
		for (int i = 0; i < drawCards; i++) {
			players.get(choose).pickCard(deck.dealCard(playingPile));
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public CardPile getPlayingPile() {
		return playingPile;
	}

	public void setPlayingPile(CardPile playingPile) {
		this.playingPile = playingPile;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public boolean isNextPlayerSkip() {
		return nextPlayerSkip;
	}

	public void setNextPlayerSkip(boolean nextPlayerSkip) {
		this.nextPlayerSkip = nextPlayerSkip;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getNUM_PLAYERS() {
		return NUM_PLAYERS;
	}

	public void addPlayer(String name) {
		Player p = new Player(name);
		for (int i = 0; i < 7; i++) {
			p.pickCard(deck.dealCard(playingPile));
		}
		players.add(p);
		NUM_PLAYERS++;
		//System.out.println("PLAYER ADDED: " + p.toString());
	}

	public Player getPlayer(String name) {
		Player player = null;
		for (Player p : players) {
			if (p.getName().equals(name)) {
				player = p;
			}
		}
		return player;
	}
	public boolean getReverse(){
		return reverse;
	}

	@Override
	public String toString() {
		return "Game [NUM_PLAYERS=" + NUM_PLAYERS + ", players=" + players.toString() + ", turn=" + turn + ", reverse="
				+ reverse + ", nextPlayerSkip=" + nextPlayerSkip + ", gameOver=" + gameOver + ", winner=" + winner
				+ "]";
	}

}