package models.gui;


import controllers.GamePanelController;
import util.ImageReader;
import util.MouseClickedFunc;
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

public class MainButton extends JButton {

    public static BufferedImage DEFAULT_ICON = ImageReader.Read(Path.of("GameUI", "button01.png").toString());
    private BufferedImage backgroundImage;

    private MouseClickedFunc handleMouseCliked;

    private final GamePanelController gamePanelController;

    static {
        DEFAULT_ICON = DEFAULT_ICON.getSubimage(0, 0, DEFAULT_ICON.getWidth(), DEFAULT_ICON.getHeight() / 2);
    }

//    public MainButton(String content, int x, int y, int width, int height, BufferedImage image, GamePanelController gamePanelController) {
//        this.gamePanelController = gamePanelController;
//
//        setBounds(x, y, width, height * 2);
//        setText(content);
//        setFont(new Font("Roboto", Font.BOLD, 20));
//        setBackground(null);
//        setBorder(null);
//        backgroundImage = image;
//    }

    public MainButton(String content, int x, int y, int width, BufferedImage image, GamePanelController gamePanelController, MouseClickedFunc handleMouseCliked) {
        this.gamePanelController = gamePanelController;

        int height = (int)(width * 0.4);

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

        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        g2d.setColor(Color.WHITE);

        GString.drawCenteredString(g2d, getText(), new Rectangle(0, 0, getWidth(), getHeight()), getFont());
        g2d.dispose();
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            handleMouseCliked.apply(this.gamePanelController);

            backgroundImage = ImageReader.Read(Path.of("GameUI", "button01.png").toString());
            backgroundImage = backgroundImage.getSubimage(0, backgroundImage.getHeight() / 2, backgroundImage.getWidth(), backgroundImage.getHeight() / 2);
            GamePanelController.setTimeout(300, () -> {
                backgroundImage = DEFAULT_ICON;
            });
        }

        super.processMouseEvent(e);
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


    // GETTER & SETTER
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public MainButton setBackgroundImage(BufferedImage image) {
        this.backgroundImage = image;
        return this;
    }

    public MainButton setHandleCliked(MouseClickedFunc func) {
        this.handleMouseCliked = func;
        return this;
    }

}