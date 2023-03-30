package bin;

import util.ImageReader;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class Render {
    public static void main(String[] args) throws IOException {
        // Load the sprite sheet image file into a BufferedImage object
        BufferedImage spriteSheet = ImageReader.Read("mainObject\\some_sonic_sprites_i_edited__by_terencebird1_d6em2fa.png");

        // Define the width and height of each sprite in pixels
        int spriteWidth = 30;
        int spriteHeight = 30;
        final double scale = 1.5;

        // Define the number of rows and columns in the sprite sheet
        int rows = 1;
        int cols = 10;

        // Create a JFrame window and set its title, size, and close operation
        JFrame window = new JFrame("Sprite Sheet Example");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel component and add it to the window
        JPanel panel = new JPanel() {
            // Override the paintComponent method of the JPanel to draw the sprites
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Cast the Graphics object to Graphics2D for better rendering
                Graphics2D g2d = (Graphics2D) g;
                int i, j;
                i = 0; j = 1;
                int x = j * spriteWidth;
                int y = i * spriteHeight;

                BufferedImage sprite = spriteSheet.getSubimage(x, y, spriteWidth, spriteHeight);
                g2d.drawImage(sprite, (int) (x * scale), (int) (y * scale), (int) (spriteWidth * scale),
                        (int) (spriteHeight * scale), null);

//                // Loop through each row and column of the sprite sheet
//                for (int i = 0; i < rows; i++) {
//                    for (int j = 0; j < cols; j++) {
//                        // Calculate the x and y coordinates of the current sprite in the sprite sheet
//                        int x = j * spriteWidth;
//                        int y = i * spriteHeight;
//                        // Get a subimage of the sprite sheet that corresponds to the current sprite
//                        BufferedImage sprite = spriteSheet.getSubimage(x, y, spriteWidth, spriteHeight);
//                        // Draw the sprite on the panel at a scaled position
//                        g2d.drawImage(sprite, (int) (x * scale), (int) (y * scale), (int) (spriteWidth * scale),
//                                (int) (spriteHeight * scale), null);
//                    }
//                }
            }
        };
        window.add(panel);

        // Make the window visible
        window.setVisible(true);
    }
}
