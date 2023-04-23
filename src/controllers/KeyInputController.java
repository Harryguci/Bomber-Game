package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputController implements KeyListener {

    private GamePanel gamePanel;
    private boolean isPressed = false;

    private final boolean[] key = new boolean[200];

    public KeyInputController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public boolean isPressed(String type) {
        type = type.toLowerCase();

        switch (type) {
            case "up" -> {
                return key[KeyEvent.VK_UP] || key[KeyEvent.VK_W];
            }
            case "down" -> {
                return key[KeyEvent.VK_DOWN] || key[KeyEvent.VK_S];
            }
            case "left" -> {
                return key[KeyEvent.VK_LEFT] || key[KeyEvent.VK_A];
            }
            case "right" -> {
                return key[KeyEvent.VK_RIGHT] || key[KeyEvent.VK_D];
            }
            case "space" -> {
                return key[KeyEvent.VK_SPACE] || key[KeyEvent.VK_ENTER];
            }
        }

        return false;
    }

    public void setReleased(int keyCode) {
        if (keyCode < 120) {
            key[keyCode] = false;
        }
    }

    public boolean isPressed(int keyCode) {
        return this.key[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 120) {
            key[e.getKeyCode()] = isPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 120) {
            key[e.getKeyCode()] = isPressed = false;
        }
    }
}
