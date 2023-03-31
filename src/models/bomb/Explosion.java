package models.bomb;

import controllers.GamePanelController;
import models.Entity;
import models.gui.Sprite;
import views.Renderer;

import java.awt.*;

public class Explosion extends Entity {
    private GamePanelController gamePanelController;
    private int timer = 30;
    private boolean _alive = true;
    private Renderer renderer = Sprite.BOMB_01;
    private Color _color = new Color(255, 0, 0, 100);

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

        int cl = Math.abs((timer - 30) % 30) / 5 + 2;

        renderer.setScale((float) (1.7 * gamePanelController.getScale() * 1.0 / 3));
        renderer.render(g2d, cl, 0, _x - 20, _y - 20, gamePanelController.getXOffset());
        renderer.setScale((float) (gamePanelController.getScale() * 1.0 / 3));
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
