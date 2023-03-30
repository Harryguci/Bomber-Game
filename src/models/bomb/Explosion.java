package models.bomb;

import controllers.GamePanelController;
import models.Entity;

import java.awt.*;

public class Explosion extends Entity {
    private GamePanelController gamePanelController;
    private int timer = 30;
    private boolean _alive = true;
    private Color _color = new Color(255, 0,0, 150);
    private enum Status {
        ACTION, DONE,
    }

    private Status status = Status.ACTION;

    public Explosion(int x, int y, GamePanelController gamePanelController) {
        this.gamePanelController = gamePanelController;

        _x = x;
        _y = y;

        _width = _height = gamePanelController.tileSize;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(_color);
        g2d.fillRect(_x - gamePanelController.getXOffset(), _y, _width, _height);
    }

    @Override
    public void update() {
        if (timer > 0)
            timer--;
        else _alive = false;
    }

    // GETTER && SETTER
    public int getTimer() {
        return timer;
    }

    public Status getStatus() {
        return status;
    }

    public boolean getAlive() {
        return _alive;
    }
}
