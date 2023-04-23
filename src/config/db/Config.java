package config.db;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Config {
    // set up game properties..
    public static final Dimension deviceScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public final static byte FPS = 60;
    public static String _language = "ENG";
}
