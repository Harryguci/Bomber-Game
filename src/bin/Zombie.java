package bin;

import models.AbleMoveEntity;
import views.Renderer;
import controllers.GamePanelController;
import controllers.KeyInputController;

import java.awt.Graphics2D;

public class Zombie extends AbleMoveEntity {

    Renderer renderer = new Renderer(148, 181, "zombies\\walk.png");
    private int countDown = 100;

    public Zombie(GamePanelController gamePanelController, KeyInputController keyInputController) {
        super(gamePanelController, keyInputController);
        _x = _y = 20;
        renderer.setScale(0.5f);
    }

    @Override
    public void move() {

    }

    @Override
    public void draw(Graphics2D g2d) {
        if (countDown > 0) return;
        int d = Math.abs(_animate % 40);
        // int[] temps = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < 40; i++) {
            if (d <= i) {
                d = i / 4;
                break;
            }
        }
        _animate--;
        renderer.render(g2d, d, 0, _x, _y, 0);
        renderer.render(g2d, d, 0, gamePanelController.screenWidth - 100, _y, 0);
        renderer.render(g2d, d, 0, _x, gamePanelController.screenHeight - 100, 0);
        renderer.render(g2d, d, 0, gamePanelController.screenWidth - 100, gamePanelController.screenHeight - 100, 0);
    }

    @Override
    public void update() {
        super.handleAnimate();
        if (countDown > 0) countDown--;
    }
}
