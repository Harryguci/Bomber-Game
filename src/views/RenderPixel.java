package views;

import util.ImageReader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderPixel {
    private BufferedImage spriteSheet;
    private int spriteWidth, spriteHeight;
    private int scale;
    private BufferedImage enlargedSpriteSheet;
    private int[] spritePixels;

    private int sizeOne;

    public static RenderPixel TILES16 = new RenderPixel(ImageReader.Read("tiles\\tilemap_packed.png"), 4);
    public static RenderPixel TILES16_02 = new RenderPixel(ImageReader.Read("tiles\\Overworld.png"), 4);

    public RenderPixel(BufferedImage spriteSheet, int scale) {
        this.spriteSheet = spriteSheet;
        spriteWidth = spriteSheet.getWidth();
        spriteHeight = spriteSheet.getHeight();
        this.scale = scale;
        this.spritePixels = spriteSheet.getRGB(0, 0, spriteWidth, spriteHeight, null, 0, spriteWidth);
        this.sizeOne = 16;
        enlargedSpriteSheet = new BufferedImage(spriteWidth * scale, spriteHeight * scale, BufferedImage.TYPE_INT_ARGB);

        load();
    }

    public RenderPixel(BufferedImage spriteSheet, int scale, int sizeOne) {
        this.spriteSheet = spriteSheet;
        spriteWidth = spriteSheet.getWidth();
        spriteHeight = spriteSheet.getHeight();
        this.scale = scale;
        this.spritePixels = spriteSheet.getRGB(0, 0, spriteWidth, spriteHeight, null, 0, spriteWidth);
        this.sizeOne = sizeOne;
        enlargedSpriteSheet = new BufferedImage(spriteWidth * scale, spriteHeight * scale, BufferedImage.TYPE_INT_ARGB);

        load();
    }

    public void load() {
        for (int x = 0; x < spriteWidth; x++) {
            for (int y = 0; y < spriteHeight; y++) {
                // Get the pixel color at the current position
                int pixel = spritePixels[x + y * spriteWidth];

                // Scale the pixel color to the new size
                Color color = new Color(pixel);
                if (color == Color.BLACK)
                    continue;

                for (int i = 0; i < scale; i++) {
                    for (int j = 0; j < scale; j++) {
                        int px = x * scale + i;
                        int py = y * scale + j;
                        enlargedSpriteSheet.setRGB(px, py, color.getRGB());
                    }
                }
            }
        }
    }

    public BufferedImage getEnlargedSpriteSheet() {
        return enlargedSpriteSheet;
    }

    public BufferedImage getSubEnlargedSpriteSheet(int cl, int r) {
        int w = sizeOne * scale;
        int h = sizeOne * scale;
        return enlargedSpriteSheet.getSubimage(cl * w, r * h, w, h);
    }

    // test
    public static void main(String[] args) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                int cl, r;

                cl = 3;
                r = 5;
                BufferedImage img = RenderPixel.TILES16.getSubEnlargedSpriteSheet(cl, r);
                g2d.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);

                g2d.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(100, 100));

        // Display the panel
        JFrame frame = new JFrame("Scaled Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
