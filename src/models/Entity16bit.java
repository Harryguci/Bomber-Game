package models;

import controllers.GamePanelController;
import views.RenderPixel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity16bit extends Entity {
    protected RenderPixel render;
    protected int cl, r;
    protected boolean _alive = true;
    protected GamePanelController gamePanelController;
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

    public void setGamePanel(GamePanelController gamePanelController) {
        this.gamePanelController = gamePanelController;
    }

    public boolean getAlive() {
        return _alive;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (gamePanelController != null)
            g2d.drawImage(image, _x - gamePanelController.getXOffset(), _y - gamePanelController.getYOffset(), _width, _height, null);
    }

    @Override
    public void update() {}
}
