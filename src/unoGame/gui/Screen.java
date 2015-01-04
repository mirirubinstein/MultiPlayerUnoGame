package unoGame.gui;

import unoGame.Card;
import unoGame.CardColor;
import unoGame.PlayerBasicInfo;
import unoGame.messages.ScreenShot;

import javax.swing.*;
import java.awt.*;

public class Screen extends JFrame{


    private TopCardPanel topCardPanel;
    private PickCardsPanel pickCardsPanel;
    private AllPlayersPanel allPlayersPanel;
    private PlayersCardsPanel playersCardsPanel;
    private JPanel currentPlayer;
    private JLabel player;
    private UpdatesPanel activityLogPanel;
    private boolean isMyTurn;

    public Screen(ScreenShot screenShot, String playersName) {

        this.setTitle((playersName));

        topCardPanel = new TopCardPanel(screenShot.topCard.getColor().getColor(), screenShot.topCard.numberToString());

        if(screenShot.currentPlayerIndex == screenShot.myPlayerIndex){
            isMyTurn=true;
        }else{
            isMyTurn=false;
        }

        PickCardsActionListener pickCardsActionListener = new PickCardsActionListener();

        pickCardsPanel = new PickCardsPanel(isMyTurn, pickCardsActionListener);

        allPlayersPanel = new AllPlayersPanel(screenShot.playersInfo, screenShot.isInAscendingOrder);


        playersCardsPanel = new PlayersCardsPanel(isMyTurn, screenShot);

        activityLogPanel = new UpdatesPanel();
        currentPlayer = new JPanel();
        player = new JLabel(screenShot.playersInfo[screenShot.currentPlayerIndex].name + "\'s turn");
        player.setFont(new Font("verdana", Font.BOLD, 25));

        topCardPanel.setBounds(500, 300, 250, 300);
        pickCardsPanel.setBounds(300, 300, 250, 300);
        allPlayersPanel.setBounds(100, 0, 800, 1800);
        playersCardsPanel.setBounds(0, 0, 900, 200);

        setLayout(new BorderLayout());
        currentPlayer.setBounds(450, 200, 200, 50);
        currentPlayer.add(player);
        currentPlayer.setPreferredSize(new Dimension(400, 200));
        currentPlayer.setVisible(true);

        add(currentPlayer);
        add(topCardPanel);
        add(pickCardsPanel, BorderLayout.CENTER);
        add(allPlayersPanel);
        add(playersCardsPanel, BorderLayout.SOUTH);
        add(activityLogPanel, BorderLayout.EAST);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }



    public void showDisconnected() {
        setVisible(false);
    }


    public void update(ScreenShot screenShot) {
        if(screenShot.currentPlayerIndex == screenShot.myPlayerIndex){
            isMyTurn=true;
        }else{
            isMyTurn =false;
        }

        pickCardsPanel.update(isMyTurn);
        topCardPanel.setTopCard(screenShot.topCard.getColor().getColor(), screenShot.topCard.numberToString());
        allPlayersPanel.update(screenShot.playersInfo, screenShot.isInAscendingOrder);
        playersCardsPanel.update(screenShot.myCards);
        player.setText(screenShot.playersInfo[screenShot.currentPlayerIndex].getName() + "\'s turn");
    }
}
