package models;

import controllers.GamePanel;
import views.RenderPixel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Entity16bit extends Entity {
    protected RenderPixel render;
    protected int cl, r;
    protected boolean _alive = true;
    protected GamePanel gamePanel;
    protected BufferedImage image;

    public Entity16bit(Entity16bit tile) {
        this.cl = tile.cl;
        this.r = tile.r;
        this.image = tile.image;
        _width = tile._width;
        _height = tile._height;
    }

    public Entity16bit(RenderPixel render, int cl, int r) {
        this.cl = cl;
        this.r = r;
        this.render = render;
        this.image = render.getSubEnlargedSpriteSheet(cl, r);
        _width = image.getWidth();
        _height = image.getHeight();
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean getAlive() {
        return _alive;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (gamePanel != null)
            g2d.drawImage(image, _x - gamePanel.getXOffset(), _y - gamePanel.getYOffset(), _width, _height, null);
    }

    @Override
    public void update() {
    }
}
