package unoGame.gui;

import unoGame.Card;
import unoGame.CardColor;
import unoGame.PlayerBasicInfo;
import unoGame.controllers.ScreenController;
import unoGame.messages.ScreenShot;
import unoGame.gameViews.PlayingGameView;

import javax.swing.*;
import java.awt.*;

public class Screen extends JFrame implements PlayingGameView {


    private TopCardPanel topCardPanel;
    private PickCardsPanel pickCardsPanel;
    private AllPlayersPanel allPlayersPanel;
    private PlayersCardsPanel playersCardsPanel;
    private JPanel currentPlayer;
    private JLabel player;
    private UpdatesPanel activityLogPanel;

    public Screen(ScreenController controller, ScreenShot screenShot) {

        //this.setTitle((controller.getPlayerName()));
        // so it runs
        this.setTitle(("Player Tester"));

        //topCardPanel = new TopCardPanel(controller.getTopCardColor(), screenShot.topCard.numberToString());
        // so it runs
        topCardPanel = new TopCardPanel(Color.RED, "1");
        pickCardsPanel = new PickCardsPanel(controller.isMyTurn(), controller.createClosePileController());

        //allPlayersPanel = new AllPlayersPanel(screenShot.playersInfo, screenShot.isInAscendingOrder,
               // controller.createOtherPlayerController());
        //so it runs
        PlayerBasicInfo[]info = {new PlayerBasicInfo("Player Tester 2", 8, false), new PlayerBasicInfo("Player Tester 3", 5, false)};
        allPlayersPanel = new AllPlayersPanel(info, true, controller.createOtherPlayerController());


        //playersCardsPanel = new PlayersCardsPanel(controller.isMyTurn(), screenShot.myCards, controller.createPlayerCardsController());
        //so it runs
        Card[] myCards = {new Card(CardColor.BLUE, 4), new Card(CardColor.GREEN, 7), new Card(CardColor.RED, 9)};
        playersCardsPanel = new PlayersCardsPanel(controller.isMyTurn(), myCards, controller.createPlayerCardsController());

        activityLogPanel = new UpdatesPanel();
        currentPlayer = new JPanel();
        //player = new JLabel(screenShot.playersInfo[screenShot.currentPlayerIndex].name + "\'s turn");
        //so it runs
        player = new JLabel("holderName" + "\'s turn");
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



    @Override
    public void showDisconnected() {
        setVisible(false);
    }

    @Override
    public void update(ScreenController controller) {
        pickCardsPanel.update(controller.isMyTurn());
        topCardPanel.setTopCard(controller.getTopCardColor(), controller.getTopCardNumber());
        allPlayersPanel.update(controller.getPlayersInfo(), controller.isInAscendingOrder());
        playersCardsPanel.update(controller.getPlayerCards());
        player.setText(controller.getPlayerName() + "\'s turn");

    }
}
