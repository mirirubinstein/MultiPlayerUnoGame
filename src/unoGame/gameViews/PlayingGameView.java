package unoGame.gameViews;


import unoGame.controllers.ScreenController;

public interface PlayingGameView {
    void showDisconnected();

    void update(ScreenController controller);
}