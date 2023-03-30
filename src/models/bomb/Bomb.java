package models.bomb;

import models.Entity;
import views.Renderer;
import models.gui.Sprite;
import controllers.GamePanelController;

import java.awt.*;

public class Bomb extends Entity {

    enum Direction {
        NO_ACTION, ACTION, DONE,
    }

    private boolean _alive = true;
    private Direction _direction = Direction.NO_ACTION;
    private int _length = 1;
    private GamePanelController gamePanelController;
    private boolean isExplore = false;
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
        if (!isExplore) {
            if (Math.abs(_animate % 10) < 5) {
                renderer.setScale(gamePanelController.getScale() * 1.0 / 3);
                renderer.render(g2d, 0, 0, _x, _y, gamePanelController.getXOffset());
            } else {
                renderer.setScale(gamePanelController.getScale() * 1.0 / 3);
                renderer.render(g2d, 1, 0, _x, _y, gamePanelController.getXOffset());
            }
        } else {

            g2d.setColor(new Color(255, 0, 0, 150));
            g2d.fillRect(_x - gamePanelController.getXOffset(), _y - gamePanelController.getYOffset(), _width, _height);
            int x = _x - _length * gamePanelController.titleSize - gamePanelController.getXOffset();
            int y = _y - gamePanelController.getYOffset();
            int cornerRounded = 15;
            g2d.fillRoundRect(x, y, _length * gamePanelController.titleSize * 2 + gamePanelController.titleSize, gamePanelController.titleSize, cornerRounded, cornerRounded);
            x = _x - gamePanelController.getXOffset();
            y = _y - _length * gamePanelController.titleSize - gamePanelController.getYOffset();
            g2d.fillRoundRect(x, y, gamePanelController.titleSize, _length * gamePanelController.titleSize * 2 + gamePanelController.titleSize, cornerRounded, cornerRounded);


            int d = Math.abs(_animate % 55);
            int cl = 7;

            if (d < 25) {
                cl = 7;
            } else if (d < 30) {
                cl = 6;
            } else if (d < 35) {
                cl = 5;
            } else if (d < 40) {
                cl = 4;
            } else if (d < 45) {
                cl = 3;
            } else if (d < 54) {
                cl = 2;
            }

            if (cl <= 7) {
                renderer.setScale(1.5 * gamePanelController.getScale() * 1.0 / 3);
                renderer.render(g2d, cl, 0, _x - 12, _y - 12, gamePanelController.getXOffset());
                renderer.setScale(gamePanelController.getScale() * 1.0 / 3);
            }
        }
    }

    @Override
    public void update() {
        super.handleAnimate();
        _counter--;

        if (isExplore) {
            if (_counter <= 0) {
                _alive = false;
                _animate = 50;
                gamePanelController.player1.setNumberOfBomb(gamePanelController.player1.getNumberOfBomb() + 1);
                _direction = Direction.DONE;
            }
        } else if (_counter <= 0) {
            isExplore = true;
            _direction = Direction.ACTION;
            _counter = 50;
        }
    }

    // GETTER & SETTER
    public boolean isExplore() {
        return isExplore;
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

    public boolean insideExplore(Rectangle rect) {
        if (isExplore == false || _direction != Direction.ACTION) return false;

        int d = 10;

        int x = _x - _length * gamePanelController.titleSize + d;
        int y = _y + d;
        int width = _length * gamePanelController.titleSize * 2 + gamePanelController.titleSize - 2 * d;
        int height = gamePanelController.titleSize - 2 * d;

        if (GamePanelController.isCollision(rect, new Rectangle(x, y, width, height))) return true;

        x = _x + d;
        y = _y - _length * gamePanelController.titleSize + d;
        width = gamePanelController.titleSize - 2 * d;
        height = _length * gamePanelController.titleSize * 2 + gamePanelController.titleSize - 2 * d;

        if (GamePanelController.isCollision(rect, new Rectangle(x, y, width, height))) return true;

        return false;
    }

    public boolean isAlive() {
        return _alive;
    }
}
