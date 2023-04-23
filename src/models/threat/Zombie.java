package models.threat;

import models.MovableEntity;
import models.Entity;
import models.bomb.Explosion;
import util.GameThread;
import views.Renderer;
import models.Sprite;
import controllers.GamePanel;
import controllers.KeyInputController;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Zombie extends MovableEntity {
    private final Renderer renderer = Sprite.ZOMBIE_01;
    private int delay = 0, countDown = 50;

    private int _heart = 3;
    private int maxHeart = 3;
    private float scale = 0.18f;
    private boolean canHurt = true;

    public Zombie(int x, int y, GamePanel gamePanel, KeyInputController keyInputController) {
        super(gamePanel, keyInputController);
        _x = x;
        _y = y;
        _width = _height = gamePanel.tileSize;
        direction = Direction.LEFT;
        setSpeed((int) Math.ceil(gamePanel.getScale() * 1.0 / 3), (int) Math.ceil(gamePanel.getScale() * 1.0 / 3));

        scale *= gamePanel.getScale() * 1.0 / 3;
        renderer.setScale(scale);
    }

    @Override
    public void move() {
        boolean checkCanMove = canMove(direction);
        delay--;
        if (delay <= 0 || !checkCanMove) {
            boolean check = false;

            while (!check) {
                int randomNumber = (int) (Math.random() * 100) % 4;
                switch (randomNumber) {
                    case 0 -> {
                        if (direction != Direction.UP && canMove(Direction.UP)) {
                            direction = Direction.UP;

                            check = true;
                        }
                    }
                    case 1 -> {
                        if (direction != Direction.DOWN && canMove(Direction.DOWN)) {
                            direction = Direction.DOWN;
                            check = true;
                        }
                    }
                    case 2 -> {
                        if (direction != Direction.LEFT && canMove(Direction.LEFT)) {
                            direction = Direction.LEFT;
                            check = true;
                        }
                    }
                    case 3 -> {
                        if (direction != Direction.RIGHT && canMove(Direction.RIGHT)) {
                            direction = Direction.RIGHT;
                            check = true;
                        }
                    }
                }

            }
            delay = (int) (Math.random() * 300) % 130 + 30;
        }

        switch (direction) {
            case UP -> _y -= _speedY;
            case DOWN -> _y += _speedY;
            case LEFT -> _x -= _speedX;
            case RIGHT -> _x += _speedX;
        }
    }

    public boolean canMove(Direction newDirection) {

        switch (newDirection) {
            case UP -> {
                return super.canMove(_x, _y - _speedY);
            }
            case DOWN -> {
                return super.canMove(_x, _y + _speedY);
            }
            case LEFT -> {
                return super.canMove(_x - _speedX, _y);
            }
            case RIGHT -> {
                return super.canMove(_x + _speedX, _y);
            }
        }
        return false;
    }

    public boolean canMove(int x, int y) {
        return super.canMove(x, y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (countDown > 0) {
            g2d.setColor(new Color(255, 0, 0, countDown * 4));
            g2d.fillRoundRect(_x, _y, _width, _height, 30, 30);
            return;
        }
        if (direction == Direction.DIED) {
            renderer.render(g2d, 7, 0, _x - 10, _y, gamePanel.getXOffset()); // render on screen
            return;
        }

        int d = Math.abs(_animate % 140); // 0 - 20; 21 - 40; 41 - 59
        for (int i = 0; i < 140; i++) {
            if (d <= i) {
                d = i / 20;
                break;
            }
        }

        if (direction == Direction.LEFT) {
            renderer.render(g2d, d, 0, _x - 10, _y, gamePanel.getXOffset()); // render on screen
        } else {
            renderer.render(g2d, d, 0, _x - 10, _y, gamePanel.getXOffset(), false); // render on screen (FLIP IMAGE 180 deg on Horizontal)
        }

        g2d.setColor(Color.RED);
        int rectW = (_width - 9) / 3;
        for (int i = 1; i <= maxHeart; i++) {
            Rectangle temp = new Rectangle(_x - gamePanel.getXOffset() + (rectW + 3) * (i - 1), _y - 10, rectW, 7);
            if (_heart >= i) {
                g2d.fillRect(temp.x, temp.y, temp.width, temp.height);
            } else {
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(temp.x, temp.y, temp.width, temp.height);
            }
        }
    }

    @Override
    public void update() {
        super.animate();

        if (countDown > 0) {
            countDown--;
            return;
        }
        _animate--;
        if (_animate < -9000)
            _animate = 9001;

        if (direction != Direction.DIED) {
            move();
            handleBombed();
        }
    }

    public void handleBombed() {
        if (!canHurt) return;

        Rectangle rect = new Rectangle(_x + (_width - gamePanel.tileSize + 20) / 2,
                _y + (_height - gamePanel.tileSize + 20) / 2,
                gamePanel.tileSize - 20, gamePanel.tileSize - 20);

        Entity e = gamePanel.detectEntity(rect, this);

        if (e instanceof Explosion) {
            _heart--;
            canHurt = false;
            GameThread.setTimeout(1000, () -> canHurt = true);

            if (_heart <= 0) {
                direction = Direction.DIED;
                GameThread.setTimeout(1000, () -> {
                    _alive = false;
                });
            }
        }
    }
}
