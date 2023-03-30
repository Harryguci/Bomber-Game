package models;

import controllers.GamePanelController;
import controllers.KeyInputController;

import java.awt.*;

public abstract class AbleMoveEntity extends Entity {

    protected enum Direction {
        DEFAULT, UP, DOWN, LEFT, RIGHT, DIED,
    }

    protected Direction direction = Direction.RIGHT;

    protected GamePanelController gamePanelController;
    protected KeyInputController keyInputController;
    protected int _animate = 0;
    final protected int MAX_ANIMATED = 7500;
    protected boolean isMove = false;

    protected boolean _alive = true;
    protected Rectangle _collideRect;

    protected int _speedX, _speedY;

    public AbleMoveEntity(GamePanelController gamePanelController, KeyInputController keyInputController) {
        super();
        this.gamePanelController = gamePanelController;
        this.keyInputController = keyInputController;
        this.isCollied = true;
    }

    // [GETTER & SETTER]

    public void animate() {
        _animate = _animate > MAX_ANIMATED ? 0 : _animate + 1;
    }

    public void setSpeed(int speedX, int speedY) {
        _speedX = speedX;
        _speedY = speedY;
    }

    public int getSpeedX() {
        return _speedX;
    }

    public void setCollideRect(Rectangle rect) {
        _collideRect = rect;
    }
    public String getDirection() {
        return direction.toString();
    }
    public int getSpeedY() {
        return _speedY;
    }

    public boolean isAlive() {
        return _alive;
    }
    // END OF [GETTER & SETTER]

    // HANDLING METHOD
    public abstract void move();

    public boolean canMove(int x, int y) {
        Rectangle rect = new Rectangle(x + (_width - gamePanelController.titleSize + 20) / 2,
                y + (_height - gamePanelController.titleSize + 20) / 2,
                gamePanelController.titleSize - 20, gamePanelController.titleSize - 20);

        return !gamePanelController.detectTitle(rect.x / gamePanelController.titleSize, rect.y / gamePanelController.titleSize)
                && !gamePanelController.detectTitle((rect.x + rect.width) / gamePanelController.titleSize, rect.y / gamePanelController.titleSize)
                && !gamePanelController.detectTitle(rect.x / gamePanelController.titleSize, (rect.y + rect.height) / gamePanelController.titleSize)
                && !gamePanelController.detectTitle((rect.x + rect.width) / gamePanelController.titleSize, (rect.y + rect.height) / gamePanelController.titleSize);
    }

    @Override
    public abstract void draw(Graphics2D g2d);

    @Override
    public abstract void update();
}
