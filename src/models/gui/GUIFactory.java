package models.gui;

import util.ImageReader;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class GUIFactory {
    public enum NameGUI {
        HEART, MENU_HORIZONTAL, MENU_VERTICAL, BG_GRADIENT
    }
    private final static BufferedImage[] guiImages = {ImageReader.Read("background\\heart1.png"),
            ImageReader.Read(Path.of("GameUI", "menu_horizontal.png").toString()),
            ImageReader.Read(Path.of("GameUI", "menu.png").toString()),
            ImageReader.Read(Path.of("background", "backgroundGradient.png").toString())
    };

    public static BufferedImage getHeart() {
        return guiImages[0];
    }

    public static BufferedImage getMenuHorizontal() {
        return guiImages[1];
    }

    public static BufferedImage getMenuVertical() {
        return guiImages[2];
    }

    public static BufferedImage getBackgroundGradient() {
        return guiImages[3];
    }

    public static BufferedImage[] getGuiImages() {
        return guiImages;
    }
}
