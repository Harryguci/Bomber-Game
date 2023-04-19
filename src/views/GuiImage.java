package views;

import models.Sprite;
import util.ImageReader;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class GuiImage {
    final public static BufferedImage menuVertical = ImageReader.Read(Path.of("GameUI", "menu.png").toString());
    final public static BufferedImage menuHorizontal = ImageReader.Read(Path.of("GameUI", "menu_horizontal.png").toString());
    public static BufferedImage button_icon_default = ImageReader.Read(Path.of("GameUI", "button01.png").toString());
    public static BufferedImage button_icon_clicked = ImageReader.Read(Path.of("GameUI", "button01.png").toString());
    final public static BufferedImage backgroundGradient = ImageReader.Read(Path.of("background", "backgroundGradient.png").toString());

    final public static BufferedImage heart = ImageReader.Read(Path.of("background", "heart1.png").toString());

    final public static BufferedImage pause_btn = Sprite.PAUSE_BTN.getSubimage(0,0);
    final public static BufferedImage pause_btn_clicked = Sprite.PAUSE_BTN.getSubimage(1,0);


    static {
        button_icon_clicked = button_icon_default.getSubimage(0, button_icon_default.getHeight() / 2, button_icon_default.getWidth(), button_icon_default.getHeight() / 2);
        button_icon_default = button_icon_default.getSubimage(0, 0, button_icon_default.getWidth(), button_icon_default.getHeight() / 2);

    }
}
