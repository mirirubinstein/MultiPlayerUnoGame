package unoGame.gui;

import javax.swing.*;
import java.awt.*;

public class UpdatesPanel extends JPanel {

    private final JPanel logPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane();

    public UpdatesPanel() {
        Label activityLabel = new Label("Activity Log");
        activityLabel.setFont(new Font("verdana", Font.BOLD, 22));
        logPanel.add(activityLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logPanel.setVisible(true);
        scrollPane.getViewport().add(logPanel);

        scrollPane.setPreferredSize(new Dimension(200, 700));

        add(scrollPane);
        setVisible(true);

    }

    public void update(String player, String card) {
        logPanel.add(new TextArea(player + " played " + card));
        logPanel.revalidate();
    }
}