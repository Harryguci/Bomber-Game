package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ImageReader {
    public static BufferedImage Read(String filePath) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

        filePath = path + "\\src\\resources\\" + filePath;
        System.out.println(filePath);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.out.println("ERROR Read image file");
        } finally {
            return img;
        }
    }

    public static void main(String[] args) {
        BufferedImage img = ImageReader.Read("demo.png");
    }
}
