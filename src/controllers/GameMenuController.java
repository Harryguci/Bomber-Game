package controllers;

import views.GameMenu;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenuController {
    private GameMenu menuBar;
    private GamePanel gamePanel;

    public GameMenuController(GameMenu menuBar, GamePanel gamePanel) {
        this.menuBar = menuBar;
        this.gamePanel = gamePanel;

        ActionListener startAction = e -> {
            if (JOptionPane.showConfirmDialog(gamePanel, "Do you want to back to START?", "Bomber game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                gamePanel.statusGame = GamePanel.StatusGame.START;
        };
        ActionListener tutorialAction = e -> {
            if (JOptionPane.showConfirmDialog(gamePanel, "Do you want to show TUTORIAL?", "Bomber game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                gamePanel.statusGame = GamePanel.StatusGame.GAME_TUTORIAL;
        };

        ActionListener pauseAction = e -> {
            if (gamePanel.statusGame == GamePanel.StatusGame.PLAYING)
                gamePanel.statusGame = GamePanel.StatusGame.PAUSE;
        };

        ActionListener helpAction = e -> {
            JDialog helpDialog = new JDialog();
            helpDialog.setLayout(new BorderLayout());
            helpDialog.setTitle("HELP DIALOG");
            helpDialog.setModal(true);

            String html = "<html><head><style>"
                    + "body { font-family: Arial; font-size: 14px; background: white; border: none; text-align: center;}"
                    + "h1 { color: red; font-size: 30pt; font-weight: bold; text-align: left; }"
                    + "p { text-indent: 14px; }"
                    + "a { color: blue; text-decoration: none; }"
                    + "li {margin: 10px 0px; text-align: left;}"
                    + "</style></head><body>"
                    + "<h1>TUTORIAL</h1>"
                    + "<ul>"
                    + "<li>Use <b>[UP]</b>, <b>[DOWN]</b>, <b>[LEFT]</b>, <b>[RIGHT]</b> to move</li>"
                    + "<li>Use [SPACE] to put a bomb</li>"
                    + "<li><b><a href=\"https://github.com/Harryguci/Bomber-Game\">ABOUT ME</a><b></li>"
                    + "<ul>"
                    + "</body></html>";

            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setBackground(new Color(240, 240, 240));
            editorPane.setContentType("text/html");
            editorPane.setFont(new Font("Arial", Font.PLAIN, 15));
            editorPane.setAlignmentX(SwingConstants.CENTER);
            editorPane.setAlignmentY(SwingConstants.CENTER);
            editorPane.setText(html);
            editorPane.setBorder(null);
            editorPane.addHyperlinkListener(e1 -> {
                if (e1.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        Desktop.getDesktop().browse(e1.getURL().toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


            helpDialog.getContentPane().add(editorPane, BorderLayout.CENTER);
            helpDialog.getContentPane().add(new JLabel("    "), BorderLayout.WEST);
            helpDialog.getContentPane().add(new JLabel("    "), BorderLayout.EAST);
            helpDialog.getContentPane().add(new JLabel("    "), BorderLayout.NORTH);
            helpDialog.getContentPane().add(new JLabel("    "), BorderLayout.SOUTH);

            helpDialog.getContentPane().setBackground(Color.WHITE);
            helpDialog.setBackground(Color.WHITE);
            helpDialog.setSize(500, 400);
            helpDialog.setLocationRelativeTo(null); // Center the dialog on the screen
            helpDialog.setVisible(true);
        };

        ActionListener newGameAction = e -> {
            if (JOptionPane.showConfirmDialog(gamePanel, "Do you want a new game ?", "NEW GAME", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                gamePanel.statusGame = GamePanel.StatusGame.SELECT_MAP;
        };

        menuBar.getStartItem().addActionListener(startAction);
        menuBar.getTutorialItem().addActionListener(tutorialAction);
        menuBar.getPauseItem().addActionListener(pauseAction);
        menuBar.getDialogHelpItem().addActionListener(helpAction);
        menuBar.getNewGameỈtem().addActionListener(newGameAction);
    }
}
