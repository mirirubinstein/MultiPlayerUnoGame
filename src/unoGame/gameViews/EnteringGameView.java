package unoGame.gameViews;


import unoGame.controllers.ScreenController;
import unoGame.messages.ScreenShot;

public interface EnteringGameView {
    PlayingGameView switchToPlayingGameView(ScreenController controller, ScreenShot screenShot);
}
