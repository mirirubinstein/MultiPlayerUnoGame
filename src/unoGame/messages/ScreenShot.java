package unoGame.messages;

import unoGame.Card;
import unoGame.CardColor;
import unoGame.Player;
import unoGame.PlayerBasicInfo;

import java.io.Serializable;

public class ScreenShot implements Serializable {

	private static final long serialVersionUID = 1L;
	private Card[] myCards;
	private PlayerBasicInfo[] playersInfo;
	private int currentPlayerIndex;
	private Card topCard;
	private boolean isInAscendingOrder;
	private int myPlayerIndex;
	private boolean playedCard;
	private boolean drawCard;
	private boolean calledUno;
	private Player currentPlayer;

	public ScreenShot() {

	}

	public boolean isCalledUno() {
		return calledUno;
	}

	public void setCalledUno(boolean calledUno) {
		this.calledUno = calledUno;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Card[] getMyCards() {
		return myCards;
	}

	public void setMyCards(Card[] myCards) {
		this.myCards = myCards;
	}

	public PlayerBasicInfo[] getPlayersInfo() {
		return playersInfo;
	}

	public void setPlayersInfo(PlayerBasicInfo[] playersInfo) {
		this.playersInfo = playersInfo;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	public Card getTopCard() {
		return topCard;
	}

	public void setTopCard(Card topCard) {
		this.topCard = topCard;
	}

	public boolean isInAscendingOrder() {
		return isInAscendingOrder;
	}

	public void setInAscendingOrder(boolean isInAscendingOrder) {
		this.isInAscendingOrder = isInAscendingOrder;
	}

	public int getMyPlayerIndex() {
		return myPlayerIndex;
	}

	public void setMyPlayerIndex(int myPlayerIndex) {
		this.myPlayerIndex = myPlayerIndex;
	}

	public boolean isPlayedCard() {
		return playedCard;
	}

	public void setPlayedCard(boolean playedCard) {
		this.playedCard = playedCard;
	}

	public boolean isDrawCard() {
		return drawCard;
	}

	public void setDrawCard(boolean drawCard) {
		this.drawCard = drawCard;
	}

}