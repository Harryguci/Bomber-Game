package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ImageReader {

    // get external image files
    public static BufferedImage Read(String filePath) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

        // filePath = path + "\\src\\resources\\" + filePath;
        filePath = Path.of(path.toString(), "src", "resources", filePath).toString();

        System.out.println(filePath);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.out.println("ERROR Read image file");
        }

        return img;
    }
}
