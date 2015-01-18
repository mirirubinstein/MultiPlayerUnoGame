package unoGame.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UpdatesPanel extends JPanel {

    private final JPanel logPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane();
    private ArrayList<String> updates = new ArrayList<String>();
    private JTextArea text = new JTextArea();

    public UpdatesPanel() {
        //Label activityLabel = new Label("Activity Log");
    	// activityLabel.setFont(new Font("verdana", Font.BOLD, 22));
    	// logPanel.add(activityLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logPanel.setVisible(true);
        scrollPane.getViewport().add(logPanel);

        scrollPane.setPreferredSize(new Dimension(200, 700));
        logPanel.add(text);
        add(scrollPane);
        setVisible(true);
        

    }

    public void update(String player, String card) {
    	if(updates.size()==0 ||!updates.get(updates.size()-1).equals(player + " played " + card)){
    	updates.add(player + "\nplayed: \n" + card +"\n");
    	}
    	StringBuilder sb = new StringBuilder();
    	for(String update: updates){
    		sb.append(update +"\n");
    	}
    	text.setText(sb.toString());
    	
        logPanel.revalidate();
        logPanel.repaint(); 
    }
}