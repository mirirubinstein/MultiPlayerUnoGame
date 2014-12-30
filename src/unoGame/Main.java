package unoGame;

import unoGame.controllers.GameController;
import unoGame.gui.EnterGameScreen;

public class Main {
    public static void main(String[] args) {

       //Factory factory = new Factory();

        GameController controller = new GameController(/*factory*/);
        EnterGameScreen enterGameScreen = new EnterGameScreen(controller);
        enterGameScreen.showScreen();



    }
}