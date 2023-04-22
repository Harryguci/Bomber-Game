package models.gui;


import controllers.GamePanelController;
import util.ImageReader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.awt.event.MouseEvent;

public class MainButton extends JButton {

    public static BufferedImage DEFAULT_ICON = ImageReader.Read(Path.of("GameUI", "button01.png").toString());
    private BufferedImage backgroundImage, defaultImage, clickedImage;
    private Color overlay = null;

    public MainButton(String content, int x, int y, int width) {
        int height = (int) (width * 0.4);

        setBounds(x, y, width, height);
        setSize(width, height);
        // setPreferredSize(width, height);

        setText(content);
        setFont(new Font("Roboto", Font.BOLD, 20));
        setBackground(null);
        setBorder(null);

        backgroundImage = DEFAULT_ICON;
        clickedImage = backgroundImage.getSubimage(0, backgroundImage.getHeight() / 2, backgroundImage.getWidth(), backgroundImage.getHeight() / 2);
        backgroundImage = backgroundImage.getSubimage(0, 0, backgroundImage.getWidth(), backgroundImage.getHeight() / 2);
        defaultImage = backgroundImage;

        setIcon(new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                g2d.dispose();
            }

            @Override
            public int getIconWidth() {
                return 0;
            }

            @Override
            public int getIconHeight() {
                return 0;
            }
        });

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
//        setOpaque(false);

        setVisible(true);
    }

    public MainButton(String content, int x, int y, int width, int height, BufferedImage defaultImage, BufferedImage clickedImage) {

        setBounds(x, y, width, height);
        setSize(width, height);

        setText(content);
        setFont(new Font("Roboto", Font.BOLD, 20));
        setBackground(null);
        setBorder(null);

        backgroundImage = defaultImage;
        this.defaultImage = defaultImage;
        this.clickedImage = clickedImage;
        setIcon(new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();

                RenderingHints hints = new RenderingHints(
                        RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
                );
                g2d.setRenderingHints(hints);

                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                g2d.dispose();
            }

            @Override
            public int getIconWidth() {
                return 0;
            }

            @Override
            public int getIconHeight() {
                return 0;
            }
        });

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setVisible(true);

        setForeground(Color.WHITE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    protected void processMouseEvent(MouseEvent event) {
        if (event.getID() == MouseEvent.MOUSE_CLICKED) {
            backgroundImage = clickedImage;
            GamePanelController.setTimeout(300, () -> backgroundImage = defaultImage);
        } else if (event.getID() == MouseEvent.MOUSE_ENTERED) {
            setForeground(Color.ORANGE);
        } else if (event.getID() == MouseEvent.MOUSE_EXITED) {
            setForeground(Color.WHITE);
        }
        super.processMouseEvent(event);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setBounds(100, 50, 500, 500);

        JPanel panel = new JPanel(null);
        panel.add(new bin.TestButtonEvent("CLICKED", 0, 0, 200, 50, bin.TestButtonEvent.DEFAULT_ICON));
        panel.setBounds(0, 0, 500, 500);

        frame.add(panel);
        frame.setBackground(Color.CYAN);

        frame.setVisible(true);
        panel.repaint();
    }

    public static MainButton clone(MainButton other) {
        MainButton cloneButton = new MainButton(other.getText(), other.getX(), other.getY(),
                other.getWidth());
        cloneButton.addActionListener(other.actionListener);
        cloneButton.setForeground(other.getForeground());
        return cloneButton;
    }


    // GETTER & SETTER
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public MainButton setBackgroundImage(BufferedImage image) {
        this.backgroundImage = image;
        return this;
    }

}