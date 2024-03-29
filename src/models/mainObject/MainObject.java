package models.mainObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import models.MovableEntity;
import models.Entity;
import models.bomb.Bomb;
import models.bomb.Explosion;
import models.tiles.Item;
import util.GameThread;
import views.Renderer;
import models.Sprite;
import models.threat.Zombie;
import controllers.GamePanel;
import controllers.KeyInputController;
import util.ImageReader;

public class MainObject extends MovableEntity {
    private int _heart = 3;
    private int delayAfterPutBomb = 30;
    private int _numberOfBomb = 1;
    private final Renderer _render;
    private Color _color = null;

    private int _lengthBomb = 1;

    private boolean canPutBoom = true, canHurt = true, canGetItem = true;

    public MainObject(GamePanel gamePanel, KeyInputController keyInputController) {
        super(gamePanel, keyInputController);

        _speedX = (int) Math.ceil(2 * gamePanel.getScale() * 1.0 / 3); // Can only up and down
        _speedY = (int) Math.ceil(2 * gamePanel.getScale() * 1.0 / 3);

        _y = 3 * gamePanel.tileSize;
        _x = 2 * gamePanel.tileSize;

        _width = _height = gamePanel.tileSize;

        float sc = (float) (gamePanel.tileSize * 1.0 / 120);
        _render = Sprite.PLAYER_01;
        _render.setScale(sc);
    }

    public void setImage(String path) {
        super.setImage(ImageReader.Read(path));
    }

    public void handleMove() {
        if (_x + _width > gamePanel.getViewportWidth())
            _x = gamePanel.getViewportWidth() - _width;
        else if (_x - gamePanel.getXOffset() < 0)
            _x = 0;
        if (_y + _height > gamePanel.screenHeight)
            _y = gamePanel.screenHeight - _height;
        else if (_y < 0)
            _y = 0;
    }

    public Entity detectCollidedEntity() {
        Rectangle rect = new Rectangle(_x + (_width - gamePanel.tileSize + 20) / 2,
                _y + (_height - gamePanel.tileSize + 20) / 2,
                gamePanel.tileSize - 20, gamePanel.tileSize - 20);

        return gamePanel.detectEntity(rect);
    }

    public Rectangle getCollideRect() {
        return new Rectangle(_x + (_width - gamePanel.tileSize + 20) / 2,
                _y + (_height - gamePanel.tileSize + 20) / 2,
                gamePanel.tileSize - 20, gamePanel.tileSize - 20);
    }

    @Override
    public void move() {
        isMove = true;
        if (keyInputController.isPressed("up")) {
            if (canMove(_x, _y - _speedY)) {
                _y -= _speedY;  // Don't must change player's direction
            }
        } else if (keyInputController.isPressed("down")) {
            if (canMove(_x, _y + _speedY)) {
                _y += _speedY; // Don't must change player's direction
            }
        } else if (keyInputController.isPressed("left")) {
            if (canMove(_x - _speedX, _y)) {
                _x -= _speedX;
                direction = Direction.LEFT;
            }
        } else if (keyInputController.isPressed("right")) {
            if (canMove(_x + _speedX, _y)) {
                _x += _speedX;
                direction = Direction.RIGHT;
            }
        } else
            isMove = false; // the player is not moving

        handleMove();
    }


    @Override
    public void draw(Graphics2D g2d) {
        Point imgLocation = selectImage();

        if (direction == Direction.RIGHT) {
            _render.render(g2d, imgLocation.x, imgLocation.y, _x, _y, gamePanel.getXOffset());
        } else
            _render.render(g2d, imgLocation.x, imgLocation.y, _x, _y, gamePanel.getXOffset(), false);

        if (_color != null) {
            if (Math.abs(_animate % 6) < 2) _color = new Color(255, 0, 0, 100);
            else
                _color = new Color(255, 100, 100, 100);

            g2d.setColor(_color);
            g2d.fillRoundRect(_x - gamePanel.getXOffset() - 5, _y - 5, _width + 10, _height + 10, 15, 15);
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
        super.animate();
        if (direction == Direction.DIED) return;

        Entity e = detectCollidedEntity();
        if (e instanceof Bomb && !((Bomb) e).getDirection().equals("ACTION")
                && delayAfterPutBomb < 6)
            delayAfterPutBomb = 6;

        if (e instanceof Item)
            getItem((Item) e);

        move();
        putBomb();

        delayAfterPutBomb--;
        if (delayAfterPutBomb < -9000) delayAfterPutBomb = -1;
    }

    public void putBomb() {
        if (keyInputController.isPressed("space") && _numberOfBomb > 0 && canPutBoom) {
            int bombX = (_x + gamePanel.tileSize / 2) / gamePanel.tileSize * gamePanel.tileSize;
            int bombY = (_y + gamePanel.tileSize / 2) / gamePanel.tileSize * gamePanel.tileSize;

            gamePanel.addEntity(new Bomb(bombX, bombY, _width, _height, _lengthBomb, gamePanel));
            keyInputController.setReleased(KeyEvent.VK_SPACE);

            _numberOfBomb--;
            delayAfterPutBomb = 20;

            canPutBoom = false;

            GameThread.setTimeout(300, () -> {
                canPutBoom = true;
            });
        }
    }

    public void setNumberOfBomb(int n) {
        this._numberOfBomb = n;
    }

    public void setDefault() {
        direction = Direction.DEFAULT;
        _x = _y = _width = _height = gamePanel.tileSize;
        _color = null;
        _numberOfBomb = 1;
        _lengthBomb = 1;
        _heart = 3;
    }

    public void setAlive() {
        direction = Direction.DEFAULT;
    }

    public void setAlive(boolean b) {
        _alive = b;
    }

    public int getNumberOfBomb() {
        return _numberOfBomb;
    }

    public boolean isMove() {
        return isMove;
    }

    public boolean isDied() {
        if (direction == Direction.DIED) return true;

        Rectangle rect = new Rectangle(_x + (_width - gamePanel.tileSize + 20) / 2,
                _y + (_height - gamePanel.tileSize + 20) / 2,
                gamePanel.tileSize - 20, gamePanel.tileSize - 20);

        Entity e = gamePanel.detectEntity(rect);

        if (
                canHurt && ((e instanceof Explosion)
                        || (e instanceof Zombie && !((Zombie) e).getDirection().equals("DIED")))
        ) {
            _heart--;
            _color = new Color(255, 0, 0, 100);

            canHurt = false;
            GameThread.setTimeout(1000, () -> {
                canHurt = true;
            });
            GameThread.setTimeout(3000, () -> {
                _color = null;
            });

            if (_heart <= 0) {
                direction = Direction.DIED;
                return true;
            }
        }

        return false;
    }

    public void getItem(Item e) {
        if (!canGetItem) return;

        int d = e.getItem();
        switch (d) {
            case 0 -> _numberOfBomb++;
            case 1 -> _lengthBomb++;
            case 2 -> _heart = Math.min(_heart + 1, 3);
            case 3 -> gamePanel.setScores(gamePanel.getScores() + 10);
        }
        canGetItem = false;

        GameThread.setTimeout(500, () -> {
            canGetItem = true;
        });
    }


    public boolean canMove(int x, int y) {

        Rectangle rect = new Rectangle(x + (_width - gamePanel.tileSize + 20) / 2,
                y + (_height - gamePanel.tileSize + 20) / 2,
                gamePanel.tileSize - 20, gamePanel.tileSize - 20);

        Entity e = gamePanel.detectEntity(rect);

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
}
