package models.tiles;

import models.Entity;
import models.Entity16bit;
import models.bomb.Explosion;
import views.RenderPixel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class LowTile16bit extends Entity16bit {
    public final static LowTile16bit WALL_01 = new LowTile16bit(RenderPixel.TILES16, 4, 2);
    public final static LowTile16bit WALL_02 = new LowTile16bit(RenderPixel.TILES16, 4, 3);
    public final static LowTile16bit WALL_03 = new LowTile16bit(RenderPixel.TILES16, 5, 2);
    public final static LowTile16bit GROUND_01 = new LowTile16bit(RenderPixel.TILES16, 0, 4);
    public final static LowTile16bit GROUND_02 = new LowTile16bit(RenderPixel.TILES16, 6, 3);
    public final static LowTile16bit GROUND_03 = new LowTile16bit(RenderPixel.TILES16, 3, 4);
    public final static LowTile16bit GROUND_04 = new LowTile16bit(RenderPixel.TILES16_01, 0, 5);
    public final static LowTile16bit GROUND_05 = new LowTile16bit(RenderPixel.TILES16_01, 1, 5);
    public final static LowTile16bit WOOD_01 = new LowTile16bit(RenderPixel.TILES16, 3, 5);
    public final static LowTile16bit WOOD_02 = new LowTile16bit(RenderPixel.TILES16_01, 4, 5);


    public LowTile16bit(Entity16bit tile) {
        super(tile);
    }

    public LowTile16bit(RenderPixel render, int cl, int r) {
        super(render, cl, r);
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
    }

    @Override
    public void update() {
        _animate--;
        if (_animate < -9000) _animate = 9001;
        if (_alive) {
            handleBombed();
        }
    }

    public void handleBombed() {
        Rectangle rect = new Rectangle(_x, _y, gamePanel.tileSize, gamePanel.tileSize);

        Entity e = gamePanel.detectEntity(rect, this);
        if (e instanceof Explosion) {
            _alive = false;
        }
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
