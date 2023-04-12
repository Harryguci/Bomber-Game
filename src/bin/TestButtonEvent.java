package bin;

import models.gui.GString;
import util.ImageReader;
import util.MyFunction;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.awt.event.MouseEvent;

public class TestButtonEvent extends JButton {

    public static BufferedImage DEFAULT_ICON = ImageReader.Read(Path.of("GameUI", "button01.png").toString());
    private BufferedImage backgroundImage;

    private MyFunction handleMouseCliked;

    static {
        DEFAULT_ICON = DEFAULT_ICON.getSubimage(0, 0, DEFAULT_ICON.getWidth(), DEFAULT_ICON.getHeight() / 2);
    }

    public TestButtonEvent(String content, int x, int y, int width, int height, BufferedImage image) {
        setBounds(x, y, width, height * 2);
        setText(content);
        setFont(new Font("Roboto", Font.BOLD, 20));
        setBackground(null);
        setBorder(null);
        backgroundImage = image;
    }

    public TestButtonEvent(String content, int x, int y, int width, BufferedImage image, MyFunction handleMouseCliked) {
        int height = (int) ((159 * 1.0 / 255) * width);

        setBounds(x, y, width, height);
        setText(content);
        setFont(new Font("Roboto", Font.BOLD, 20));
        setBackground(null);
        setBorder(null);
        backgroundImage = image;
        this.handleMouseCliked = handleMouseCliked;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawImage(backgroundImage, getX(), getY(), getWidth() - 20, getHeight() / 2, null);
        g2d.setColor(Color.WHITE);

        GString.drawCenteredString(g2d, getText(), new Rectangle(getX(), getY(), getWidth() - 20, getHeight() / 2), getFont());
        g2d.dispose();
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            handleMouseCliked.apply();
        }

        super.processMouseEvent(e);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setBounds(100, 50, 500, 500);

        JPanel panel = new JPanel(null);
        panel.add(new TestButtonEvent("CLICKED", 0, 0, 200, 50, TestButtonEvent.DEFAULT_ICON));
        panel.setBounds(0, 0, 500, 500);

        frame.add(panel);
        frame.setBackground(Color.CYAN);

        frame.setVisible(true);
        panel.repaint();
    }
}