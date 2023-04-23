package models.tiles;

import models.Entity;
import views.Renderer;
import models.Sprite;
import controllers.GamePanel;

import java.awt.*;

public class LowTile extends Entity {
    private Renderer render;
    private int spriteX, spriteY;

    private GamePanel gamePanel;

    public LowTile(int x, int y, int spriteX, int spriteY, int spriteWidth, int spriteHeight, String pathSprite, GamePanel gamePanel) {
        isCollied = true;
        this.gamePanel = gamePanel;
        _x = x;
        _y = y;

        render = new Renderer(spriteWidth, spriteHeight, pathSprite);
        this.spriteX = spriteX;
        this.spriteY = spriteY;

        _width = _height = gamePanel.tileSize;
    }

    public LowTile(int x, int y, LowTileKind type, GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        _x = x;
        _y = y;
        render = Sprite.LOWTILE_01;

        switch (type) {
            case ROCK_WALL_01 -> {
                this.spriteX = 4;
                this.spriteY = 2;
            }
            case ROCK_WALL_02 -> {
                this.spriteX = 5;
                this.spriteY = 2;
            }
            case BROWN_WALL_01 -> {
                this.spriteX = 3;
                this.spriteY = 6;
            }
        }

        _width = _height = gamePanel.tileSize;
        float s = (float)(_width / 16);
        render.setScale(s);
    }

    @Override
    public void draw(Graphics2D g2d) {
        render.render(g2d, spriteX, spriteY, _x, _y, gamePanel.getXOffset());
    }

    @Override
    public void update() {
        _animate--;
        if (_animate < -9000) _animate = 9001;
    }
}
