package models.bomb;

import models.Entity;
import views.Renderer;
import models.gui.Sprite;
import controllers.GamePanelController;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bomb extends Entity {

    enum Direction {
        NO_ACTION, ACTION, DONE,
    }

    private boolean _alive = true;
    private Direction _direction = Direction.NO_ACTION;
    private int _length = 1;
    private GamePanelController gamePanelController;
    private boolean isExplosion = false;
    private int _counter = 100;
    private Renderer renderer = Sprite.BOMB_01;

    public Bomb(int _x, int _y, int _width, int _height, GamePanelController gamePanelController) {
        this._x = _x;
        this._y = _y;
        this._width = _width;
        this._height = _height;
        this.gamePanelController = gamePanelController;
    }

    public Bomb(int _x, int _y, int _width, int _height, int length, GamePanelController gamePanelController) {
        this._x = _x;
        this._y = _y;
        this._width = _width;
        this._height = _height;
        this.gamePanelController = gamePanelController;
        this._length = length;
    }

    @Override
    public void draw(Graphics2D g2d) {
//        if (!isExplosion) {
            if (Math.abs(_animate % 10) < 5) {
                renderer.setScale(gamePanelController.getScale() * 1.0 / 3);
                renderer.render(g2d, 0, 0, _x, _y, gamePanelController.getXOffset());
            } else {
                renderer.setScale(gamePanelController.getScale() * 1.0 / 3);
                renderer.render(g2d, 1, 0, _x, _y, gamePanelController.getXOffset());
            }
//        } else {

//            g2d.setColor(new Color(255, 0, 0, 150));
//            g2d.fillRect(_x - gamePanelController.getXOffset(), _y - gamePanelController.getYOffset(), _width, _height);
//            int x = _x - _length * gamePanelController.tileSize - gamePanelController.getXOffset();
//            int y = _y - gamePanelController.getYOffset();
//            int cornerRounded = 15;
//            g2d.fillRoundRect(x, y, _length * gamePanelController.tileSize * 2 + gamePanelController.tileSize, gamePanelController.tileSize, cornerRounded, cornerRounded);
//            x = _x - gamePanelController.getXOffset();
//            y = _y - _length * gamePanelController.tileSize - gamePanelController.getYOffset();
//            g2d.fillRoundRect(x, y, gamePanelController.tileSize, _length * gamePanelController.tileSize * 2 + gamePanelController.tileSize, cornerRounded, cornerRounded);
//
//
//            int d = Math.abs(_animate % 55);
//            int cl = 7;
//
//            if (d < 25) {
//                cl = 7;
//            } else if (d < 30) {
//                cl = 6;
//            } else if (d < 35) {
//                cl = 5;
//            } else if (d < 40) {
//                cl = 4;
//            } else if (d < 45) {
//                cl = 3;
//            } else if (d < 54) {
//                cl = 2;
//            }
//
//            if (cl <= 7) {
//                renderer.setScale(1.5 * gamePanelController.getScale() * 1.0 / 3);
//                renderer.render(g2d, cl, 0, _x - 12, _y - 12, gamePanelController.getXOffset());
//                renderer.setScale(gamePanelController.getScale() * 1.0 / 3);
//            }
//        }
    }

    @Override
    public void update() {
        super.handleAnimate();
        _counter--;

        if (_counter <= 0) {
            isExplosion = true;
            _alive = false;
            _direction = Direction.DONE;
            gamePanelController.player1.setNumberOfBomb(gamePanelController.player1.getNumberOfBomb() + 1);
            addExplosions();
        }
    }

    public boolean addOneExplosion(int cl, int r) {
        int explosionX = cl * gamePanelController.tileSize;
        int explosionY = r * gamePanelController.tileSize;
        return gamePanelController.addExplosion(new Explosion(explosionX, explosionY, gamePanelController));
    }

    public void addExplosions() {
        int cl = _x / gamePanelController.tileSize;
        int r = _y / gamePanelController.tileSize;

        addOneExplosion(cl, r);

        // LEFT SIDE
        for (int i = 1; i >= -_length; i--) {
            if (!addOneExplosion(cl + i, r))
                break;
        }

        // RIGHT SIDE
        for (int i = 1; i <= _length; i++) {
            if (!addOneExplosion(cl + i, r))
                break;
        }

        // UP SIDE
        for (int i = 1; i >= -_length; i--) {
            if (!addOneExplosion(cl, r + i))
                break;
        }

        // DOWN SIDE
        for (int i = 0; i <= _length; i++) {
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
        int width = gamePanelController.tileSize - 2 * d;
        int height = width;

        return GamePanelController.isCollision(rect, new Rectangle(x, y, width, height));
    }

    public boolean isAlive() {
        return _alive;
    }
}
