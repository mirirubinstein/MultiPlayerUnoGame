package unoGame.controllers;

import unoGame.Card;
import unoGame.PlayerBasicInfo;
import unoGame.messages.ScreenShot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenController {
    //private GameClient gameClient;
    private ScreenShot screenShot;

    public ScreenController(/*GameClient gameClient,*/ ScreenShot screenShot) {
        //this.gameClient = gameClient;
        this.screenShot = screenShot;
    }

    public Color getTopCardColor() {
        return this.screenShot.topCard.getColor().getColor();
    }


    public ClosePileActionListener createClosePileController() {

        return new ClosePileActionListener();
    }

    public String getPlayerName() {
        return this.screenShot.playersInfo[this.screenShot.myPlayerIndex].name;
    }

    public OtherPlayerActionListener createOtherPlayerController() {
        return new OtherPlayerActionListener();
    }

    public PlayerCardsActionListener createPlayerCardsController() {
        return new PlayerCardsActionListener();
    }

    public boolean isMyTurn() {
        return screenShot.currentPlayerIndex == screenShot.myPlayerIndex;
    }

    public PlayerBasicInfo[] getPlayersInfo() {
        return screenShot.playersInfo;
    }

    public boolean isInAscendingOrder() {
        return screenShot.isInAscendingOrder;
    }

    public String getTopCardNumber() {
        return screenShot.topCard.numberToString();
    }

    public Card[] getPlayerCards() {
        return screenShot.myCards;
    }

    private class ClosePileActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class OtherPlayerActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class PlayerCardsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}