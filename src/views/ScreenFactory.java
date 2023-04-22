package views;

import com.sun.tools.javac.Main;
import controllers.GamePanelController;
import models.gui.GString;
import models.gui.MainButton;
import models.gui.MainButtonFactory;
import util.ImageReader;
import util.OpenURL;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

final public class ScreenFactory {
    private ScreenPanel startScreen, tutorialScreen, gameOverScreen, transitionScreen, selectionMapScreen, winGameScreen;

    public ScreenFactory(GamePanelController gamePanelController) {
        final MainButtonFactory btnFactory = new MainButtonFactory(gamePanelController);
        final int screenWidth = gamePanelController.screenWidth;
        final int screenHeight = gamePanelController.screenHeight;

        MainButton playBtn = btnFactory.getPlayBtn();
        MainButton tutorialBtn = btnFactory.getTutorialBtn();
        MainButton langBtn = btnFactory.getLangBtn();
        MainButton backBtn = btnFactory.getBackStartBtn();
        MainButton nextGameBtn = btnFactory.getnextGameBtn();
        MainButton aboutBtn = new MainButton("ABOUT", tutorialBtn.getX(), tutorialBtn.getY() + 100, tutorialBtn.getWidth());

        BufferedImage map1Image = ImageReader.Read(Path.of("GameUI", "map1btn.png").toString());
        BufferedImage map2Image = ImageReader.Read(Path.of("GameUI", "map2btn.png").toString());
        BufferedImage map3Image = ImageReader.Read(Path.of("GameUI", "map3btn.png").toString());
        int mapButtonWidth = 200;
        int mapButtonHeight = 400;

        MainButton map1 = new MainButton("Map 1", gamePanelController.screenWidth / 3 - mapButtonWidth / 2, screenHeight / 2 - mapButtonHeight / 2,
                mapButtonWidth, mapButtonHeight, map1Image, map1Image);
        MainButton map2 = new MainButton("Map 2", gamePanelController.screenWidth / 2 - mapButtonWidth / 2, screenHeight / 2 - mapButtonHeight / 2,
                mapButtonWidth, mapButtonHeight, map2Image, map2Image);
        MainButton map3 = new MainButton("Map 3", 2 * gamePanelController.screenWidth / 3 - mapButtonWidth / 2, screenHeight / 2 - mapButtonHeight / 2,
                mapButtonWidth, mapButtonHeight, map3Image, map3Image);

        playBtn.setForeground(Color.WHITE);
        tutorialBtn.setForeground(Color.WHITE);
        aboutBtn.setForeground(Color.WHITE);
        langBtn.setForeground(Color.WHITE);
        backBtn.setForeground(Color.WHITE);
        nextGameBtn.setForeground(Color.WHITE);
        map1.setForeground(Color.WHITE);
        map2.setForeground(Color.WHITE);
        map3.setForeground(Color.WHITE);

        playBtn.addActionListener(e -> GamePanelController.setTimeout(300, () -> {
//                    gamePanelController.restartAtLevel(0);
                    gamePanelController.statusGame = GamePanelController.StatusGame.SELECT_MAP;
                }
        ));

        tutorialBtn.addActionListener(e -> GamePanelController.setTimeout(300, () ->
                gamePanelController.statusGame = GamePanelController.StatusGame.GAME_TUTORIAL));

        langBtn.addActionListener(e -> gamePanelController.setLang(GamePanelController._language.equals("ENG") ? "VN" : "ENG"));

        backBtn.addActionListener(e -> GamePanelController.setTimeout(300, ()
                -> gamePanelController.statusGame = GamePanelController.StatusGame.START));

        nextGameBtn.addActionListener(e -> GamePanelController.setTimeout(1000, ()
                -> {
            gamePanelController.statusGame = GamePanelController.StatusGame.PLAYING;
            gamePanelController.restartAtLevel(gamePanelController.getLevel());
        }));

        try {
            aboutBtn.addActionListener(new OpenURL(new URI("https://github.com/Harryguci/Bomber-Game")));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        map1.addActionListener(e -> {
            JOptionPane.showMessageDialog(gamePanelController, "Get start now..");
            GamePanelController.setTimeout(500, () -> {
                gamePanelController.restartAtLevel(0);
                gamePanelController.statusGame = GamePanelController.StatusGame.PLAYING;
            });
        });

        map2.addActionListener(e -> {
            JOptionPane.showMessageDialog(gamePanelController, "Get start now..");
            GamePanelController.setTimeout(500, () -> {
                gamePanelController.restartAtLevel(1);
                gamePanelController.statusGame = GamePanelController.StatusGame.PLAYING;
            });
        });
        map3.addActionListener(e -> {
            JOptionPane.showMessageDialog(gamePanelController, "Get start now..");

            GamePanelController.setTimeout(500, () -> {
                gamePanelController.restartAtLevel(2);
                gamePanelController.statusGame = GamePanelController.StatusGame.PLAYING;
            });
        });


        tutorialScreen = new TutorialScreen(screenWidth, screenHeight,
                new JComponent[]{MainButton.clone(langBtn), MainButton.clone(backBtn)}
        );

        startScreen = new StartGameScreen(screenWidth, screenHeight,
                new JComponent[]{playBtn, tutorialBtn, langBtn, aboutBtn}
        );

        gameOverScreen = new GameOverScreen(screenWidth, screenHeight, new JComponent[]{
                MainButton.clone(playBtn), MainButton.clone(tutorialBtn)
        });

        backBtn.setLocation(backBtn.getX(), nextGameBtn.getY() + 100);
        transitionScreen = new ScreenPanel(screenWidth, screenHeight, new JComponent[]{
                MainButton.clone(nextGameBtn), MainButton.clone(backBtn)
        }) {
            @Override
            public void setLang(String lang) {
                if (lang.equals("ENG")) {
                    nextGameBtn.setText("NEXT LEVEL");
                } else if (lang.equals("VN")) {
                    nextGameBtn.setText("MÀN TIẾP");
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                RenderingHints hints = new RenderingHints(
                        RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
                );
                g2d.setRenderingHints(hints);

                g2d.setColor(new Color(64, 64, 64));
                g2d.fillRect(0, 0, screenWidth, screenHeight);

                g2d.setColor(new Color(100, 100, 100));
                GString.drawCenteredString(g2d, "DESIGN BY HUY, NGHIA, TRUC - IT3 UTC - 2022",
                        new Rectangle(0, screenHeight - 50, screenWidth, 50),
                        gamePanelController.getFont().deriveFont(Font.PLAIN, 10));
            }
        };

        selectionMapScreen = new ScreenPanel(screenWidth, screenHeight, new JComponent[]{map1, map2, map3}) {
            @Override
            public void setLang(String lang) {
                super.setLang(lang);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setFont(new Font("Arial", Font.BOLD, 30));
                g2d.setColor(new Color(92, 61, 164, 236));
                g2d.fillRect(0, 0, screenWidth, screenHeight);

                AffineTransform tx1 = new AffineTransform();
                g2d.setColor(new Color(158, 80, 175, 236));

                tx1.scale(2.2, 2.2);
                tx1.rotate(Math.toRadians(-45));
                g2d.setTransform(tx1);
                g2d.fillRect(0, 0, screenWidth, screenHeight);

                g2d.setColor(new Color(92, 61, 164, 236));
                g2d.fillRect(180, 150, screenWidth, screenHeight);

                AffineTransform tx2 = new AffineTransform();
                tx2.translate(0, 0);
                tx2.rotate(0);
                tx2.scale(1, 1);
                g2d.setTransform(tx2);
            }
        };

    }

    public void setLang(String lang) {
        startScreen.setLang(lang);
        tutorialScreen.setLang(lang);
    }

    public ScreenPanel getStartScreen() {
        return startScreen;
    }

    public ScreenPanel getTutorialScreen() {
        return tutorialScreen;
    }

    public ScreenPanel getGameOverScreen() {
        return gameOverScreen;
    }

    public ScreenPanel getTransitionScreen() {
        return transitionScreen;
    }

    public ScreenPanel getSelectionMapScreen() {
        return selectionMapScreen;
    }

    public ScreenPanel[] getAll() {
        ScreenPanel[] panels = new ScreenPanel[]{
                startScreen, transitionScreen, gameOverScreen, transitionScreen, selectionMapScreen
        };

        return panels;
    }
}
