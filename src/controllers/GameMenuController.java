package controllers;

import views.GameMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameMenuController {
    private GameMenu menuBar;
    private GamePanelController gamePanelController;

    public GameMenuController(GameMenu menuBar, GamePanelController gamePanelController) {
        this.menuBar = menuBar;
        this.gamePanelController = gamePanelController;

        ActionListener startAction = e -> {
            if (JOptionPane.showConfirmDialog(gamePanelController, "Do you want to back to START?") == JOptionPane.YES_OPTION)
                gamePanelController.statusGame = GamePanelController.StatusGame.START;
        };
        ActionListener tutorialAction = e -> {
            if (JOptionPane.showConfirmDialog(gamePanelController, "Do you want to show TUTORIAL?") == JOptionPane.YES_OPTION)
                gamePanelController.statusGame = GamePanelController.StatusGame.GAME_TUTORIAL;
        };

        ActionListener pauseAction = e -> {
            gamePanelController.statusGame = GamePanelController.StatusGame.PAUSE;
        };

        menuBar.getStartItem().addActionListener(startAction);
        menuBar.getTutorialItem().addActionListener(tutorialAction);
        menuBar.getPauseItem().addActionListener(pauseAction);
    }
}
