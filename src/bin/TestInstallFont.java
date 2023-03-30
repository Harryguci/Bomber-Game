package bin;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TestInstallFont {
    public static void main(String[] args) throws IOException, FontFormatException {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String fontPath = "LilitaOne-Regular.ttf";
        fontPath = path + "\\src\\resources\\font\\" + fontPath;
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(24f);

        // Register the custom font with the GraphicsEnvironment
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        ge.registerFont(customFont);

        // Create a new JFrame and JLabel
        JFrame frame = new JFrame("Custom Font Example");
        JLabel label = new JLabel("Hello World");

        // Set the custom font to the JLabel
        label.setFont(customFont);
        frame.setFont(customFont);

        // Add the JLabel to the JFrame and show the JFrame
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
}
