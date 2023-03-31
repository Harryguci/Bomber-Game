package models.gui;

import controllers.GamePanelController;
import controllers.MouseInputController;

import java.awt.image.BufferedImage;

public class GToggleButton extends GButton {
    private boolean turnOn = false;

    public GToggleButton(String content, GamePanelController gamePanelController, MouseInputController mouseInputController) {
        super(content, gamePanelController, mouseInputController);
    }

    public GToggleButton(String content, int x, int y, int width, int height, GamePanelController gamePanelController, MouseInputController mouseInputController) {
        super(content, x, y, width, height, gamePanelController, mouseInputController);
    }

    public GToggleButton(String content, int screenWidth, int screenHeight, GamePanelController gamePanelController, MouseInputController mouseInputController) {
        super(content, screenWidth, screenHeight, gamePanelController, mouseInputController);
    }

    public GToggleButton(String content, int x, int y, int width, int height, BufferedImage normalImage, BufferedImage clickedImage, GamePanelController gamePanelController, MouseInputController mouseInputController) {
        super(content, x, y, width, height, normalImage, clickedImage, gamePanelController, mouseInputController);
    }

    public void update() {
        super.update();
        if (isPressed()) turnOn = !turnOn;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public void setPressed(boolean b) {
        super.setPressed(b);
        turnOn = !turnOn;
    }
}
