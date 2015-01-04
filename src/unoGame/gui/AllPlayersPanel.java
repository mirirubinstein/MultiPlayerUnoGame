package unoGame.gui;



import unoGame.PlayerBasicInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AllPlayersPanel extends JPanel {

    //UI components
    private JPanel players = new JPanel();
    private JLabel direction = new JLabel();
    private JScrollPane scrollPane = new JScrollPane();
    private ArrayList<JButton> playerButtons = new ArrayList<JButton>();

    private ActionListener otherPlayersActionListener;

    public AllPlayersPanel(PlayerBasicInfo[] playersInfo, boolean isInAscendingOrder) {
        OtherPlayerActionListener otherPlayersActionListener =new  OtherPlayerActionListener();

        createAllPlayerButtons(playersInfo);
        direction.setFont(new Font("verdana", Font.BOLD, 30));
        setDirection(isInAscendingOrder);

        players.setVisible(true);
        scrollPane.getViewport().add(players);
        scrollPane.setPreferredSize(new Dimension(900, 120));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
        add(direction);
        setSize(150, 150);
        setVisible(true);

    }

    private void setDirection(boolean isInAscendingOrder) {
        if (isInAscendingOrder)
            direction.setText("=>");
        else
            direction.setText("<=");
    }

    private void createAllPlayerButtons(PlayerBasicInfo[] basicInfo) {

        for (PlayerBasicInfo info : basicInfo) {
            playerButtons.add(new JButton(info.name + " : " + info.cardsInHand));
            JButton playerButton = playerButtons.get(playerButtons.size() - 1);
            playerButton.setPreferredSize(new Dimension(150, 100));
            playerButton.setFont(new Font("verdana", Font.BOLD, 14));
            final int newPlayerIndex = playerButtons.indexOf(playerButton);
            playerButton.addActionListener(otherPlayersActionListener);
            players.add(playerButton);
        }
    }

    public void update(PlayerBasicInfo[] info, boolean isInAscendingOrder) {
        for (int i = 0; i < playerButtons.size(); i++) {
            playerButtons.get(i).setText(info[i].name + " : " + info[i].cardsInHand);
        }
        setDirection(isInAscendingOrder);
    }
}
