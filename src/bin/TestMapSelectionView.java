package bin;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class TestMapSelectionView extends JPanel {
    int screenWidth = 700;
    int screenHeight = 500;

    public TestMapSelectionView() {
        repaint();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2d = (Graphics2D) g.create();

        Color _underbutton1 = new Color(54, 49, 49, 255);
        Color _underbutton2 = new Color(93, 148, 185, 224);
        Color _textColor = new Color(255, 255, 255);

        String map1 = "1";
        String map2 = "2";
        String map3 = "3";
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.setColor(new Color(92, 61, 164, 236));
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        AffineTransform tx1 = new AffineTransform();
        g2d.setColor(new Color(158, 80, 175, 236));
//        tx1.translate(-getScreenWidth()/2,-getScreenHeight()/2);
        tx1.scale(2.2, 2.2);
        tx1.rotate(Math.toRadians(-45));
        g2d.setTransform(tx1);
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        g2d.setColor(new Color(92, 61, 164, 236));
        g2d.fillRect(180, 150, screenWidth, screenHeight);

        AffineTransform tx2 = new AffineTransform();
        tx2.translate(0, 0);
        tx2.rotate(0);
        tx2.scale(1, 1);
        g2d.setTransform(tx2);
        //
        //paint big circle white
        g2d.setColor(_textColor);
        g2d.fillOval(100, 250, 80, 80);
        g2d.fillOval((screenWidth / 2) - 50, 50, 80, 80);
        g2d.fillOval(screenWidth - 200, 250, 80, 80);

        //paint smaller circle with color background2
        g2d.setColor(new Color(0, 0, 0));
        g2d.fillOval(105, 255, 70, 70);
        g2d.fillOval((screenWidth / 2) - 45, 55, 70, 70);
        g2d.fillOval(screenWidth - 195, 255, 70, 70);

        // paint stt map
        g2d.setColor(_textColor);
        g2d.drawString(map1, 133, 300);
        g2d.drawString(map2, (screenWidth / 2) - 17, 100);
        g2d.drawString(map3, screenWidth - 166, 300);

        // paint under button
        g2d.setColor(_underbutton1);
        g2d.fillOval(88, 330, 100, 25);
        g2d.fillOval((screenWidth / 2) - 60, 130, 100, 25);
        g2d.fillOval(screenWidth - 212, 330, 100, 25);
        g2d.dispose();
        g.dispose();
    }

    public void paintSelectLevelMap(Graphics2D g2d) {

        Color _underbutton1 = new Color(54, 49, 49, 255);
        Color _underbutton2 = new Color(93, 148, 185, 224);
        Color _textColor = new Color(255, 255, 255);

        String map1 = "1";
        String map2 = "2";
        String map3 = "3";
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.setColor(new Color(92, 61, 164, 236));
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        AffineTransform tx1 = new AffineTransform();
        g2d.setColor(new Color(158, 80, 175, 236));
//        tx1.translate(-getScreenWidth()/2,-getScreenHeight()/2);
        tx1.scale(2.2, 2.2);
        tx1.rotate(Math.toRadians(-45));
        g2d.setTransform(tx1);
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        g2d.setColor(new Color(92, 61, 164, 236));
        g2d.fillRect(180, 150, screenWidth, screenHeight);

        AffineTransform tx2 = new AffineTransform();
        tx2.translate(0, 0);
        tx2.rotate(0);
        tx2.scale(1, 1);
        g2d.setTransform(tx2);
        //
        //paint big circle white
        g2d.setColor(_textColor);
        g2d.fillOval(100, 250, 80, 80);
        g2d.fillOval((screenWidth / 2) - 50, 50, 80, 80);
        g2d.fillOval(screenWidth - 200, 250, 80, 80);

        //paint smaller circle with color background2
        g2d.setColor(new Color(0, 0, 0));
        g2d.fillOval(105, 255, 70, 70);
        g2d.fillOval((screenWidth / 2) - 45, 55, 70, 70);
        g2d.fillOval(screenWidth - 195, 255, 70, 70);

        // paint stt map
        g2d.setColor(_textColor);
        g2d.drawString(map1, 133, 300);
        g2d.drawString(map2, (screenWidth / 2) - 17, 100);
        g2d.drawString(map3, screenWidth - 166, 300);

        // paint under button
        g2d.setColor(_underbutton1);
        g2d.fillOval(88, 330, 100, 25);
        g2d.fillOval((screenWidth / 2) - 60, 130, 100, 25);
        g2d.fillOval(screenWidth - 212, 330, 100, 25);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        TestMapSelectionView panel = new TestMapSelectionView();
        panel.setSize(700, 500);
        panel.setPreferredSize(new Dimension(700, 500));
        frame.setLayout(null);

        frame.setPreferredSize(new Dimension(700, 500));
        frame.setSize(new Dimension(700, 500));

        frame.add(panel);
        frame.setVisible(true);
    }
}
