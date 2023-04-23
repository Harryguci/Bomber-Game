package models.bomb;

import controllers.GamePanel;
import models.Entity;
import models.Sprite;
import views.Renderer;

import java.awt.*;

public class Explosion extends Entity {
    private GamePanel gamePanel;
    private int timer = 30;
    private boolean _alive = true;
    private Renderer renderer = Sprite.BOMB_01;
    private Color _color = new Color(255, 0, 0, 100);

    private enum Status {
        ACTION, DONE,
    }

    private Status status = Status.ACTION;

    public Explosion(int x, int y, GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        _x = x;
        _y = y;

        _width = _height = gamePanel.tileSize;
    }

    @Override
    public void draw(Graphics2D g2d) {
//        g2d.setColor(_color);
//        g2d.fillRect(_x - gamePanelController.getXOffset(), _y, _width, _height);

        int cl = Math.abs((timer - 30) % 30) / 5 + 2;

        renderer.setScale((float) (1.7 * gamePanel.getScale() * 1.0 / 3));
        renderer.render(g2d, cl, 0, _x - 20, _y - 20, gamePanel.getXOffset());
        renderer.setScale((float) (gamePanel.getScale() * 1.0 / 3));
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
