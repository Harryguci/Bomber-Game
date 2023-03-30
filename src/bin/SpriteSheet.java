package bin;

import util.ImageReader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
// import javax.imageio.ImageReader;

public class SpriteSheet {

    private String _path;
    public final int SIZE;
    public int[] _pixels;

    public static SpriteSheet tiles = new SpriteSheet("tiles\\tilemap_packed.png", 256);

    public SpriteSheet(String path, int size) {
        _path = path;
        SIZE = size;
        _pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        BufferedImage image = ImageReader.Read(_path);
        int w = image.getWidth();
        int h = image.getHeight();
        image.getRGB(0, 0, w, h, _pixels, 0, w);
    }


    public static void main(String[] args) {

// Load the sprite sheet image
        BufferedImage spriteSheet = ImageReader.Read("tiles\\tilemap_packed.png");

// Get the sprite sheet dimensions
        int spriteWidth = spriteSheet.getWidth();
        int spriteHeight = spriteSheet.getHeight();

// Create a new BufferedImage to hold the enlarged sprite sheet
        int scale = 3; // Change this value to adjust the zoom level
        BufferedImage enlargedSpriteSheet = new BufferedImage(spriteWidth * scale, spriteHeight * scale, BufferedImage.TYPE_INT_ARGB);

// Get the pixels from the sprite sheet image
        int[] spritePixels = spriteSheet.getRGB(0, 0, spriteWidth, spriteHeight, null, 0, spriteWidth);

// Loop through each pixel in the sprite sheet and draw it at a larger size in the new BufferedImage
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

// Create a JPanel to display the enlarged sprite sheet
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                int cl, r;
                cl = 0;
                r = 6;

                BufferedImage subImage = enlargedSpriteSheet.getSubimage(cl * 16 * scale, r * 16 * scale, 16 * scale, 16 * scale);
                // Draw the enlarged sprite sheet on the panel
                g2d.drawImage(subImage, 0, 0, null);

                g2d.dispose();
            }
        };

// Display the panel
        JFrame frame = new JFrame("Scaled Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
