package views;

import util.ImageReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class Renderer {
    private final int spriteWidth;
    private final int spriteHeight;
    private final BufferedImage _spriteSheet;
    private double scale = 1;
    private int gap = 0;

    public Renderer(int spw, int sph, BufferedImage spriteSheet) {
        spriteWidth = spw;
        spriteHeight = sph;
        _spriteSheet = spriteSheet;
    }

    public Renderer(int spw, int sph, String filePath) {
        spriteWidth = spw;
        spriteHeight = sph;
        _spriteSheet = ImageReader.Read(filePath);
    }

    public void render(Graphics2D g2d, int cl, int r, int playerX, int playerY, int xOffset) {
        int x = cl * spriteWidth + (cl - 1) * gap;
        int y = r * spriteHeight;
        try {
            BufferedImage sprite = _spriteSheet.getSubimage(x, y, spriteWidth, spriteHeight);
            g2d.drawImage(sprite, playerX - xOffset, playerY, (int) (spriteWidth * scale),
                    (int) (spriteHeight * scale), null);
        } catch (RasterFormatException e) {
            System.out.println("Can not get sub image");
        }
    }

    public void render(Graphics2D g2d, int cl, int r, int playerX, int playerY, int xOffset, boolean k) {
        int x = cl * spriteWidth + (cl - 1) * gap;
        int y = r * spriteHeight;

        try {
            BufferedImage sprite = _spriteSheet.getSubimage(x, y, spriteWidth, spriteHeight);
            g2d.drawImage(sprite, playerX - xOffset + (int) (spriteWidth * scale), playerY, -(int) (spriteWidth * scale),
                    (int) (spriteHeight * scale), null);
        } catch (RasterFormatException e) {
            System.out.println("Can not get sub image");
        }
    }

    public double getScale() {
        return this.scale;
    }
    public void setGap(int gap) {
        this.gap = gap;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
    public int getSpriteWidth() {
        return spriteWidth;
    }
    public int getSpriteHeight() {
        return spriteHeight;
    }
}
