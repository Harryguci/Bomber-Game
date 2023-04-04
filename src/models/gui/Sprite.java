package models.gui;

import views.Renderer;
import java.nio.file.Path;

public class Sprite {

    //    final public static Renderer PLAYER_01 = new Renderer(120, 120, "\\mainObject\\sonic_sripte_sheet.png");
    final public static Renderer PLAYER_01 = new Renderer(120, 120, Path.of("mainObject", "sonic_sripte_sheet.png").toString());
    final public static Renderer ZOMBIE_01 = new Renderer(334, 334, Path.of("zombies", "ghost_sprite_sheet.png").toString());
    final public static Renderer BOMB_01 = new Renderer(40, 40, Path.of("bomb", "bomb1.png").toString());

    final public static Renderer LOWTILE_01 = new Renderer(16, 16, Path.of("tiles", "tilemap_packed.png").toString());
    final public static Renderer PAUSE_BTN = new Renderer(112, 112, Path.of("background", "pauseButton.png").toString());

}
