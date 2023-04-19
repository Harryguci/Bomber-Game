package views;

import javax.swing.JComponent;
import java.awt.*;

public class StartGameScreen extends ScreenPanel {

    public StartGameScreen(int w, int h, JComponent[] components) {
        super(w, h, components);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var backgroundGradient = GuiImage.backgroundGradient;
        var menuImage = GuiImage.menuVertical;

        Graphics2D g2d = (Graphics2D) g.create();
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(hints);

        g2d.setColor(new Color(64, 64, 64));
        g2d.fillRect(0, 0, getWidth(), getHeight());


        g2d.drawImage(backgroundGradient, 0, 0, getWidth(), getHeight(), this);

        int h = (getHeight() - 60);
        int w = (int) (menuImage.getWidth() * menuImage.getHeight() * 1.0 / h) - 100;

        g2d.drawImage(menuImage, (getWidth() - w) / 2, 30, w, h, this);
    }
}
