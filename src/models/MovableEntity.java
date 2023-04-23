package models;

import controllers.GamePanel;
import controllers.KeyInputController;

import java.awt.*;

public abstract class MovableEntity extends Entity {

    protected enum Direction {
        DEFAULT, UP, DOWN, LEFT, RIGHT, DIED,
    }

    protected Direction direction = Direction.RIGHT;

    protected GamePanel gamePanel;
    protected KeyInputController keyInputController;
    protected int _animate = 0;
    final protected int MAX_ANIMATED = 7500;
    protected boolean isMove = false;
    protected boolean _alive = true;
    protected int _speedX, _speedY;

    public MovableEntity(GamePanel gamePanel, KeyInputController keyInputController) {
        super();
        this.gamePanel = gamePanel;
        this.keyInputController = keyInputController;
        this.isCollied = true;
    }

    // [GETTER & SETTER]

    public void animate() {
        _animate--;
        if (_animate < -MAX_ANIMATED) _animate = MAX_ANIMATED;
    }

    public void setSpeed(int speedX, int speedY) {
        _speedX = speedX;
        _speedY = speedY;
    }

    public int getSpeedX() {
        return _speedX;
    }

    public String getDirection() {
        return direction.toString();
    }

    public int
    getSpeedY() {
        return _speedY;
    }

    public boolean isAlive() {
        return _alive;
    }
    // END OF [GETTER & SETTER]

    // HANDLING METHOD
    public abstract void move();

    public boolean canMove(int x, int y) {
        Rectangle rect = new Rectangle(x + (_width - gamePanel.tileSize + 20) / 2,
                y + (_height - gamePanel.tileSize + 20) / 2,
                gamePanel.tileSize - 20, gamePanel.tileSize - 20);

        return !gamePanel.detectAllTitles(rect.x / gamePanel.tileSize, rect.y / gamePanel.tileSize)
                && !gamePanel.detectAllTitles((rect.x + rect.width) / gamePanel.tileSize, rect.y / gamePanel.tileSize)
                && !gamePanel.detectAllTitles(rect.x / gamePanel.tileSize, (rect.y + rect.height) / gamePanel.tileSize)
                && !gamePanel.detectAllTitles((rect.x + rect.width) / gamePanel.tileSize, (rect.y + rect.height) / gamePanel.tileSize);
    }

    @Override
    public abstract void draw(Graphics2D g2d);

    @Override
    public abstract void update();
}
