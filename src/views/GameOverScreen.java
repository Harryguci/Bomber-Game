package views;

import models.gui.GString;

import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends ScreenPanel {
    public GameOverScreen(int w, int h, JComponent[] components) {
        super(w, h, components);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(64, 64, 64));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(255, 255, 255, 150));

        GString.drawCenteredString(g2d, "PRESS 'S' to PLAY again",
                new Rectangle(getWidth() / 2 - 100, getHeight() - 100, 200, 50),
                new Font("Roboto", Font.PLAIN, 15));

        g2d.setColor(new Color(250, 50, 50));
        GString.drawCenteredString(g2d, "GAME OVER", new Rectangle(0, 50, getWidth(), 150),
                new Font("Roboto", Font.BOLD, 55));
    }
}
