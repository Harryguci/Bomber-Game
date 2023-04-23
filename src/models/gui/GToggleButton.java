package models.gui;

import controllers.GamePanel;
import controllers.MouseInputController;

import java.awt.image.BufferedImage;

public class GToggleButton extends GButton {
    private boolean turnOn = false;

    public GToggleButton(String content, GamePanel gamePanel, MouseInputController mouseInputController) {
        super(content, gamePanel, mouseInputController);
    }

    public GToggleButton(String content, int x, int y, int width, int height, GamePanel gamePanel, MouseInputController mouseInputController) {
        super(content, x, y, width, height, gamePanel, mouseInputController);
    }

    public GToggleButton(String content, int screenWidth, int screenHeight, GamePanel gamePanel, MouseInputController mouseInputController) {
        super(content, screenWidth, screenHeight, gamePanel, mouseInputController);
    }

    public GToggleButton(String content, int x, int y, int width, int height, BufferedImage normalImage, BufferedImage clickedImage, GamePanel gamePanel, MouseInputController mouseInputController) {
        super(content, x, y, width, height, normalImage, clickedImage, gamePanel, mouseInputController);
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
