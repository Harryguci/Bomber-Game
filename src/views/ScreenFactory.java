package views;

import com.sun.tools.javac.Main;
import controllers.GamePanelController;
import models.gui.GString;
import models.gui.MainButton;
import models.gui.MainButtonFactory;

import javax.swing.JComponent;
import java.awt.*;

final public class ScreenFactory {
    private ScreenPanel startScreen, tutorialScreen, gameOverScreen, transitionScreen;

    public ScreenFactory(GamePanelController gamePanelController) {
        final MainButtonFactory btnFactory = new MainButtonFactory(gamePanelController);
        final int screenWidth = gamePanelController.screenWidth;
        final int screenHeight = gamePanelController.screenHeight;

        MainButton playBtn = btnFactory.getPlayBtn();
        MainButton tutorialBtn = btnFactory.getTutorialBtn();
        MainButton langBtn = btnFactory.getLangBtn();
        MainButton backBtn = btnFactory.getBackStartBtn();
        MainButton nextGameBtn = btnFactory.getnextGameBtn();

        playBtn.setForeground(Color.WHITE);
        tutorialBtn.setForeground(Color.WHITE);
        langBtn.setForeground(Color.WHITE);
        backBtn.setForeground(Color.WHITE);
        nextGameBtn.setForeground(Color.WHITE);

        playBtn.addActionListener(e -> GamePanelController.setTimeout(300, () -> {
                    gamePanelController.restartAtLevel(0);
                    gamePanelController.statusGame = GamePanelController.StatusGame.PLAYING;
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

        tutorialScreen = new TutorialScreen(screenWidth, screenHeight,
                new JComponent[]{MainButton.clone(langBtn), MainButton.clone(backBtn)}
        );

        startScreen = new StartGameScreen(screenWidth, screenHeight,
                new JComponent[]{playBtn, tutorialBtn, langBtn}
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
}
