package models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity extends JPanel {
    protected int _x, _y, _width, _height;
    protected boolean isCollied = false;
    protected Rectangle colliedRect = new Rectangle();
    protected BufferedImage _image;
    protected int _animate = 0;

    public Entity() {
    }

    public Entity(int x, int y, int width, int height) {
        init(x, y, width, height);
    }

    public Entity(int x, int y, int width, int height, BufferedImage image) {
        init(x, y, width, height);
        _image = image;
    }

    public void init(int x, int y, int width, int height) {
        _x = x;
        _y = y;
        _width = width;
        _height = height;
        colliedRect = new Rectangle(0, 0, _width, _height);
    }

    // [SETTER & GETTER]
    public void setX(int x) {
        _x = x;
    }

    public void setY(int y) {
        _y = y;
    }

    public void setLocation(int x, int y) {
        _x = x;
        _y = y;
    }

    public void setSize(int width, int height) {
        _width = width;
        _height = height;
    }

    public void setColliedRect(Rectangle rect) {
        colliedRect = rect;
    }

    public void setImage(BufferedImage image) {
        _image = image;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public Point getLocation() {
        return new Point(_x, _y);
    }

    public Dimension getSize() {
        return new Dimension(_width, _height);
    }

    public BufferedImage getImage() {
        return _image;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }
    // END OF [SETTER & GETTER]

    // HANDLING METHOD
    public Entity checkCollied(Entity other) {
        Rectangle rect2 = other.colliedRect;

        return (
                colliedRect.x < rect2.x + rect2.width &&
                        colliedRect.x + colliedRect.width > rect2.x &&
                        colliedRect.y < rect2.y + rect2.height &&
                        colliedRect.height + colliedRect.y > rect2.y
        ) ? this : null;
    }

    protected void handleAnimate() {
        _animate--;
        if (_animate < -9000) _animate = 9001;
    }

    public abstract void draw(Graphics2D g2d);

    public abstract void update();
}
