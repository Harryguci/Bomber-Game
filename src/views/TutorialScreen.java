package views;


import controllers.GamePanelController;
import models.gui.GString;
import models.gui.MainButtonFactory;
import util.ImageReader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class TutorialScreen extends ScreenPanel {
    private int scale = 3;

    public TutorialScreen(int w, int h, JComponent[] components) {
        super(w, h, components);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(hints);

        int menuHeight = getHeight() - 100;
        int menuWidth = (int) (getWidth() * 0.7);
        String content, heading;

        if (GamePanelController._language.equals("ENG")) {
            heading = "GAME TUTORIAL";
            content = """
                    - USE [UP] [DOWN] [LEFT] [RIGHT] to move player
                    - USE [SPACE] to put bombs
                    - You have to kill all monsters to win
                    - After win a part you will move to next level""";
        } else {
            heading = "Hướng dẫn Game";
            content = """
                    - Sử dụng các phím [UP] [DOWN] [LEFT] [RIGHT] để di chuyển nhân vật
                    - Sử dụng phím [SPACE] để đặt bom
                    - Bạn phải tiêu diệt tất cả mối nguy hại để chiến thắng
                    - Sau khi chiến thắng 1 màn bạn sẽ được tiếp tục level tiếp theo""";
        }

        BufferedImage menuImage = GuiImage.menuHorizontal;
        BufferedImage backgroundGradient = GuiImage.backgroundGradient;

        g2d.setColor(new Color(60, 60, 60));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.drawImage(backgroundGradient, 0, 0, getWidth(), getHeight(), this);
        g2d.drawImage(menuImage, (getWidth() - menuWidth) / 2, (getHeight() - menuHeight) / 2, menuWidth, menuHeight, this);
        GString.drawCenteredString(g2d, heading, new Rectangle((getWidth() - menuWidth) / 2, (int) (110 + (scale * 1.0 / 3) * 20), menuWidth, 50), new Font("Roboto", Font.BOLD, 50));
        GString.drawMultipleLine(g2d, content, (int) ((getWidth() - menuWidth) / 2 + 70 * (scale * 1.0 / 3)), getHeight() / 2 - 100, new Font("Roboto", Font.PLAIN, 20));
    }

    public static void main(String[] args) {
        int w = 1000;
        int h = 600;

        JPanel startGameScreen = new TutorialScreen(w, h, null);
        JFrame frame = new JFrame();

        frame.setLayout(null);
        frame.setBounds(0, 0, w, h);
        frame.add(startGameScreen);

        EventQueue.invokeLater(() -> {
            frame.setVisible(true);
            startGameScreen.repaint();
        });

        System.out.println(startGameScreen.getWidth() + " H");
    }
}
