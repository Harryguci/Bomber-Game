package models.gui;

import controllers.GamePanelController;
import models.Sprite;

import java.awt.*;

public class MainButtonFactory {
    final private MainButton playBtn, tutorialBtn, langBtn, backStartBtn, pauseBtn, nextGameBtn;

    public MainButtonFactory(GamePanelController gamePanelController) {
        int screenWidth = gamePanelController.screenWidth;
        int screenHeight = gamePanelController.screenHeight;

        playBtn = new MainButton("PLAY", (gamePanelController.screenWidth - 200) / 2, gamePanelController.screenHeight / 2 - 100, 200);

        tutorialBtn = new MainButton("TUTORIAL", (screenWidth - 200) / 2, screenHeight / 2, 200);

        langBtn = new MainButton(GamePanelController._language, screenWidth - 170, 30, 100);

        backStartBtn = new MainButton("BACK", (screenWidth - 200) / 2, screenHeight - 200, 200);

        pauseBtn = new MainButton("", screenWidth - 100, 30, 50, 50, Sprite.ICONS.getSubimage(5, 0), Sprite.ICONS.getSubimage(4, 0));
        nextGameBtn = new MainButton("NEXT LEVEL", screenWidth / 2 - 100, screenHeight / 2 - 100, 200);
    }

    public MainButton getPlayBtn() {
        return MainButton.clone(playBtn);
    }

    public MainButton getTutorialBtn() {
        return MainButton.clone(tutorialBtn);
    }

    public MainButton getLangBtn() {
        return MainButton.clone(langBtn);
    }

    public MainButton getBackStartBtn() {
        return MainButton.clone(backStartBtn);
    }

    public MainButton getPauseBtn() {
        return MainButton.clone(pauseBtn);
    }

    public MainButton getnextGameBtn() {
        return MainButton.clone(nextGameBtn);
    }

}
