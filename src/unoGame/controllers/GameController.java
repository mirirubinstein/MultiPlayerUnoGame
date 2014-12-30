package unoGame.controllers;

import unoGame.gameViews.EnteringGameView;
import unoGame.gameViews.PlayingGameView;
import unoGame.messages.ScreenShot;

import java.lang.reflect.InvocationTargetException;

// will use lister and have a factory

public class GameController {
    private final ScreenController controller;
    //private Factory factory;
    private EnteringGameView enteringGameView;
    private PlayingGameView playingGameView;
    // some message channel
    //private GameClient gameClient;

    public GameController(/*Factory factory*/) {
        //this.factory = factory;
       // create the screen controller using screenshot data from the factory

        //this is temp so i dont get an error
        ScreenShot screenShot = new ScreenShot();
        controller = new ScreenController(screenShot);
    }

    public void join(String serverAddress, String playerName) {
        // need to write

        //for now so we see the game screen
        ScreenShot screenShot = new ScreenShot();
        handle(screenShot);

    }


    private void handle(ScreenShot screenShot) {
        if (playingGameView == null) {
            playingGameView = enteringGameView.switchToPlayingGameView(controller, screenShot);
        } else playingGameView.update(controller);
    }

    public void bind(EnteringGameView enteringGameView) {
        this.enteringGameView = enteringGameView;
    }

}

