package models.gui;

import java.awt.*;

public class GString {
    public static void drawCenteredString(Graphics2D g2d, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g2d.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g2d.setFont(font);
        // Draw the String
        g2d.drawString(text, x, y);
    }

    public static Dimension getStringSize(Graphics2D g2d, String text, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g2d.getFontMetrics(font);
        int width = metrics.stringWidth(text);
        int height = metrics.getHeight();
        return new Dimension(width, height);
    }

    public static void drawMultipleLine(Graphics2D g2d, String text, int x, int y, Font font) {
        g2d.setFont(font);
        for (String line : text.split("\n"))
            g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
    }
}
