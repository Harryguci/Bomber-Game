package models.tiles;

import models.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item extends Entity {
    public enum ITEM_CODE {
        BOMB, LENGTH_EXPLOSION, HEART, COIN
    }

    public int xOffset = 0;

    private boolean _alive = true;
    private int _itemCode;

    public Item(int x, int y, int width, int height, BufferedImage image, int itemCode) {
        super(x, y, width, height);
        _image = image;
        _itemCode = itemCode;
    }

    @Override
    public void draw(Graphics2D g2d) {
        int d = 16;

        g2d.drawImage(_image, _x - 8 - xOffset, _y - 8, _width + 16, _height + 16, null);
    }

    @Override
    public void update() {
        _animate--;
        if (_animate < -7500) _animate = 7501;
    }

    public boolean isAlive() {
        return _alive;
    }

    public int getItem() {
        _alive = false;
        return _itemCode;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public void setAlive(boolean b) {
        _alive = b;
    }
}
