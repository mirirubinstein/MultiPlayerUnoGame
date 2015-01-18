package unoGame.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UnoActionListener implements ActionListener {

    public UnoActionListener(){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == "uno") {
            // what happens when player declares uno
        }
    }
}
