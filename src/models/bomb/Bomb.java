package models.bomb;

import models.Entity;
import views.Renderer;
import models.Sprite;
import controllers.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bomb extends Entity {

    enum Direction {
        NO_ACTION, ACTION, DONE,
    }

    private boolean _alive = true;
    private Direction _direction = Direction.NO_ACTION;
    private int _length = 1;
    private GamePanel gamePanel;
    private boolean isExplosion = false;
    private int _counter = 100;
    private Renderer renderer = Sprite.BOMB_01;

    public Bomb(int _x, int _y, int _width, int _height, GamePanel gamePanel) {
        this._x = _x;
        this._y = _y;
        this._width = _width;
        this._height = _height;
        this.gamePanel = gamePanel;
    }

    public Bomb(int _x, int _y, int _width, int _height, int length, GamePanel gamePanel) {
        this._x = _x;
        this._y = _y;
        this._width = _width;
        this._height = _height;
        this.gamePanel = gamePanel;
        this._length = length;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (Math.abs(_animate % 10) < 5) {
            renderer.setScale((float) (gamePanel.getScale() * 1.0 / 3));
            renderer.render(g2d, 0, 0, _x, _y, gamePanel.getXOffset());
        } else {
            renderer.setScale((float) (gamePanel.getScale() * 1.0 / 3));
            renderer.render(g2d, 1, 0, _x, _y, gamePanel.getXOffset());
        }
    }

    @Override
    public void update() {
        super.handleAnimate();
        _counter--;

        if (_counter <= 0) {
            isExplosion = true;
            _alive = false;
            _direction = Direction.DONE;
            gamePanel.player1.setNumberOfBomb(gamePanel.player1.getNumberOfBomb() + 1);
            addExplosions();
        }
    }

    public boolean addOneExplosion(int cl, int r) {
        int explosionX = cl * gamePanel.tileSize;
        int explosionY = r * gamePanel.tileSize;
        return gamePanel.addExplosion(new Explosion(explosionX, explosionY, gamePanel));
    }

    public void addExplosions() {
        int cl = _x / gamePanel.tileSize;
        int r = _y / gamePanel.tileSize;

        // Add current location
        addOneExplosion(cl, r);

        // LEFT SIDE
        for (int i = -1; i >= -_length; i--) {
            if (!addOneExplosion(cl + i, r))
                break;
        }

        // RIGHT SIDE
        for (int i = 1; i <= _length; i++) {
            if (!addOneExplosion(cl + i, r))
                break;
        }

        // UP SIDE
        for (int i = -1; i >= -_length; i--) {
            if (!addOneExplosion(cl, r + i))
                break;
        }

        // DOWN SIDE
        for (int i = 1; i <= _length; i++) {
            if (!addOneExplosion(cl, r + i))
                break;
        }
    }

    // GETTER & SETTER
    public boolean isExplosion() {
        return isExplosion;
    }

    public void setDirection(String value) {
        _direction = Direction.valueOf(value);
    }

    public void setDelay(int counter) {
        _counter = counter;
    }

    public void setAlive(boolean b) {
        _alive = b;
    }

    public String getDirection() {
        return _direction.name();
    }

    public boolean getAlive() {
        return _alive;
    }

    public boolean insideExplosion(Rectangle rect) {
        if (isExplosion == false || _direction != Direction.ACTION)
            return false;

        int d = 10;

        int x = _x + d;
        int y = _y + d;
        int width = gamePanel.tileSize - 2 * d;
        int height = width;

        return GamePanel.isCollision(rect, new Rectangle(x, y, width, height));
    }

    public boolean isAlive() {
        return _alive;
    }
}
