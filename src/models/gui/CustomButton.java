package models.gui;

import controllers.GamePanelController;
import models.Entity;
import util.ImageReader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class CustomButton extends JPanel {
    private JPanel parentPanel;
    private ImageIcon imageIcon;
    private boolean isPressed = false;
    private String content;
    private Font font = new Font("Roboto", Font.BOLD, 30);
    private Color textColor = Color.WHITE;
    static private final ImageIcon imageIconDefault;
    static private final ImageIcon imageIconClicked;

    static {
        BufferedImage temp = ImageReader.Read(Path.of("GameUI", "button01.png").toString());
        imageIconDefault = new ImageIcon(temp.getSubimage(0, 0, temp.getWidth(), temp.getHeight() / 2));
        imageIconClicked = new ImageIcon(temp.getSubimage(0, temp.getHeight() / 2, temp.getWidth(), temp.getHeight() / 2));
    }

    public CustomButton(String content, GamePanelController gamePanelController) {
        parentPanel = gamePanelController;

        imageIcon = imageIconDefault;
        this.content = content;
        setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        repaint();
        setLocation(100, 100);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                imageIcon = imageIconClicked;
                textColor = Color.BLACK;
                isPressed = true;
                repaint();
                System.out.println("CLICK");
                Timer timer = new Timer(300, e1 -> {
                    imageIcon = imageIconDefault;
                    textColor = Color.WHITE;
                    repaint();
                    isPressed = false;
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);

        g2d.setColor(textColor);
        g2d.setFont(font);

        FontMetrics metrics = g2d.getFontMetrics(font);

        int x = (this.getWidth() - metrics.stringWidth(content)) / 2;
        int y = (this.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();

        g2d.drawString(content, x, y);
        g2d.dispose();
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);
        font = font.deriveFont(height * 0.5f);
    }
    public boolean isPressed() {
        return isPressed;
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Frame");
        frame.setLayout(null);
        frame.setSize(500, 500);

        CustomButton btn = new CustomButton("CLICK", null);

        btn.setSize(300, 100);
        frame.add(btn);
        frame.setVisible(true);
    }
}
