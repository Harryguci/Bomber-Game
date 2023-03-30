package models.mainObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import models.AbleMoveEntity;
import models.Entity;
import models.bomb.Bomb;
import models.bomb.Explosion;
import views.Renderer;
import models.gui.Sprite;
import models.threat.Zombie;
import controllers.GamePanelController;
import controllers.KeyInputController;
import util.ImageReader;

public class MainObject extends AbleMoveEntity {
    private int _heart = 3;
    private int delay = 0, hurtDelay = 20, delayAfterPutBomb = 30;
    private int _numberOfBomb = 1;
    private int groundY = gamePanelController.screenHeight - gamePanelController.tileSize * 4;
    private Renderer _render;
    private Color _color = new Color(20, 20, 100);

    public MainObject(GamePanelController gamePanelController, KeyInputController keyInputController) {
        super(gamePanelController, keyInputController);

        _speedX = 3; // Can only up and down
        _speedY = 3;

        _y = 3 * gamePanelController.tileSize;
        _x = 2 * gamePanelController.tileSize;

        _width = _height = gamePanelController.tileSize;

        _render = Sprite.PLAYER_01;
        double sc = gamePanelController.tileSize * 1.0 / 120;
        _render.setScale(sc);
    }

    public void setImage(String path) {
        BufferedImage img = ImageReader.Read(path);

        super.setImage(img);
    }

    public void handleMove() {
        if (_x + _width > gamePanelController.getViewportWidth())
            _x = gamePanelController.getViewportWidth() - _width;
        else if (_x - gamePanelController.getXOffset() < 0)
            _x = 0;
        if (_y + _height > gamePanelController.screenHeight)
            _y = gamePanelController.screenHeight - _height;
        else if (_y < 0)
            _y = 0;
    }

    @Override
    public void move() {
        Rectangle rect = new Rectangle(_x + (_width - gamePanelController.tileSize + 20) / 2,
                _y + (_height - gamePanelController.tileSize + 20) / 2,
                gamePanelController.tileSize - 20, gamePanelController.tileSize - 20);

        Entity e = gamePanelController.detectEntity(rect);

        if (e instanceof Bomb && ((Bomb) e).getDirection() != "ACTION")
            while (delayAfterPutBomb < 6) delayAfterPutBomb++;

        isMove = false;
        if (keyInputController.isPressed("up")) {
            if (canMove(_x, _y - _speedY)) {
                _y -= _speedY;
                isMove = true;
            }
        } else if (keyInputController.isPressed("down")) {
            if (canMove(_x, _y + _speedY)) {
                _y += _speedY;
                isMove = true;
            }
        } else if (keyInputController.isPressed("left")) {
            if (canMove(_x - _speedX, _y)) {
                _x -= _speedX;
                direction = Direction.LEFT;
                isMove = true;
            }
        } else if (keyInputController.isPressed("right")) {
            if (canMove(_x + _speedX, _y)) {
                _x += _speedX;
                direction = Direction.RIGHT;
                isMove = true;
            }
        }
    }


    @Override
    public void draw(Graphics2D g2d) {
        Point imgLocation = selectImage();

        if (direction == Direction.RIGHT) {
            _render.render(g2d, imgLocation.x, imgLocation.y, _x, _y, gamePanelController.getXOffset());
        } else
            _render.render(g2d, imgLocation.x, imgLocation.y, _x, _y, gamePanelController.getXOffset(), false);

        if (_color != null) {
            if (_animate % 6 < 2) _color = new Color(255, 0, 0, 100);
            else
                _color = new Color(255, 100, 100, 100);

            g2d.setColor(_color);
            g2d.fillRoundRect(_x - gamePanelController.getXOffset() - 5, _y - 5, _width + 10, _height + 10, 15, 15);
        }
    }

    public Point selectImage() { // column, row
        if (isMove) {
            int d = Math.abs(_animate % 15);
            if (d < 5) return new Point(2, 0);
            else if (d < 10) return new Point(3, 0);
            else
                return new Point(4, 0);
        } else {
            return (Math.abs(_animate % 10) < 5 ? new Point(0, 0) : new Point(1, 0));
        }
    }

    @Override
    public void update() {
        if (direction != Direction.DIED) {
            move();
            handleMove();
            super.animate();
            putBomb();
            delayAfterPutBomb--;
//            System.out.println(delayAfterPutBomb);
            if (delayAfterPutBomb < -9000) delayAfterPutBomb = -1;
        } else {
            // do nothing
        }
    }

    public void putBomb() {
        if (keyInputController.isPressed("space") && _numberOfBomb > 0 && delay <= 0) {
            int bombX = (int) (_x + gamePanelController.tileSize / 2) / gamePanelController.tileSize * gamePanelController.tileSize;
            int bombY = (int) (_y + gamePanelController.tileSize / 2) / gamePanelController.tileSize * gamePanelController.tileSize;

            gamePanelController.addEntity(new Bomb(bombX, bombY, _width, _height, gamePanelController));
            keyInputController.setReleased(KeyEvent.VK_SPACE);
            delay = 20;
            _numberOfBomb--;
            delayAfterPutBomb = 20;
        }

        if (delay > 0) delay--;
    }

    public void updateCollideRect() {
        setCollideRect(new Rectangle(_x - gamePanelController.getXOffset() + (_width - gamePanelController.tileSize + 20) / 2,
                _y - gamePanelController.getYOffset() + (_height - gamePanelController.tileSize + 20) / 2,
                gamePanelController.tileSize - 20, gamePanelController.tileSize - 20));
    }

    public void setNumberOfBomb(int n) {
        this._numberOfBomb = n;
    }

    public int get_numberOfBomb() {
        return _numberOfBomb;
    }

    public void setDefault() {
        direction = Direction.DEFAULT;
        _x = _y = _width = _height = gamePanelController.tileSize;
        _color = new Color(20, 20, 100);
        _numberOfBomb = 1;
    }

    public void setAlive() {
        direction = Direction.DEFAULT;
    }

    public int getNumberOfBomb() {
        return _numberOfBomb;
    }

    public boolean isMove() {
        return isMove;
    }


    public boolean isDied() {
        if (direction == Direction.DIED) return true;

        Rectangle rect = new Rectangle(_x + (_width - gamePanelController.tileSize + 20) / 2,
                _y + (_height - gamePanelController.tileSize + 20) / 2,
                gamePanelController.tileSize - 20, gamePanelController.tileSize - 20);

        Entity e = gamePanelController.detectEntity(rect);

        hurtDelay--;

        if (
                hurtDelay <= 0
                        && ((e instanceof Explosion)
                        || (e instanceof Zombie && ((Zombie) e).getDirection() != "DIED"))
        ) {
            _heart--;
            _color = new Color(255, 0, 0, 100);

            hurtDelay = 100;

            if (delay == 0)
                delay = 100;

            if (_heart <= 0) {
                direction = Direction.DIED;
                gamePanelController.setDelay(100);
                return true;
            }
        }
        if (delay <= 0) {
            _color = null;
        } else
            delay--;

        return false;
    }

    public boolean canMove(int x, int y) {

        Rectangle rect = new Rectangle(x + (_width - gamePanelController.tileSize + 20) / 2,
                y + (_height - gamePanelController.tileSize + 20) / 2,
                gamePanelController.tileSize - 20, gamePanelController.tileSize - 20);

        Entity e = gamePanelController.detectEntity(rect);

        if (e instanceof Bomb && !((Bomb) e).isExplosion()) {
            if (delayAfterPutBomb <= 0)
                return false;
        }

        return super.canMove(x, y);
    }

    public int getHeart() {
        return _heart;
    }

    public void setHeart(int h) {
        _heart = h;
    }

    public int getGroundY() {
        return groundY;
    }

    public void setGroundY(int y) {
        groundY = y;
    }
}
