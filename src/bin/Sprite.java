package bin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

//import javax.media.jai.JAI;
//import javax.media.jai.RenderedOp;

public class Sprite {
    public int SIZE;
    private int _x, _y;
    public int[] _pixels;
    protected int _realWidth;
    protected int _realHeight;
    private SpriteSheet _sheet;

    public Sprite(int x, int y, int SIZE, SpriteSheet sheet, int rw, int rh) {
        _pixels = new int[SIZE * SIZE];
        _x = x;
        _y = y;
        _realWidth = rw;
        _realHeight = rh;
        _sheet = sheet;
        load();
    }

    public Sprite(int size, int color) {
        SIZE = size;
        _pixels = new int[SIZE * SIZE];
        setColor(color);
    }

    private void setColor(int color) {
        for (int i = 0; i < _pixels.length; i++) {
            _pixels[i] = color;
        }
    }

    private void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                _pixels[x + y * SIZE] = _sheet._pixels[(x + _x) + (y + _y) * _sheet.SIZE];
            }
        }
    }

    public int getSize() {
        return SIZE;
    }

    public int[] getPixels() {
        return _pixels;
    }

    public int getPixel(int i) {
        return _pixels[i];
    }

    public int getRealWidth() {
        return _realWidth;
    }

    public int getRealHeight() {
        return _realHeight;
    }

    public void draw(Graphics2D g2d) {

    }

    public static void main(String[] args) {

        Sprite sprite = new Sprite(0, 0, 16 * 5, SpriteSheet.tiles, 100, 100);
        BufferedImage image = new BufferedImage(192, 176, BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();


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

                for (int i = 0; i < sprite._pixels.length; i++) { //create the image to be rendered
                    pixels[i] = sprite._pixels[i];
                }

                g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

            }
        };
        window.add(panel);

        // Make the window visible
        window.setVisible(true);
    }
}
