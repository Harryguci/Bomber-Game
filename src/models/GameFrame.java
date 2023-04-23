package models;

import config.db.Config;

import controllers.GameMenuController;
import controllers.GamePanel;
import models.db.User;
import views.GameMenu;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameFrame extends JFrame implements WindowListener {
    private User user = null;
    private final GamePanel gamePanel;
    private final GameMenu gameMenu = new GameMenu();
    private GameMenuController gameMenuController;

    public GameFrame(String title) {
        super(title);

        this.gamePanel = new GamePanel(user);
        this.add(gamePanel, BorderLayout.CENTER);
        gameMenuController = new GameMenuController(gameMenu, gamePanel);
        this.add(gameMenu, BorderLayout.NORTH);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
        this.pack();

        Dimension screenSize = Config.deviceScreenSize;

        this.setLocation((screenSize.width - gamePanel.getScreenWidth()) / 2,
                (screenSize.height - gamePanel.getScreenHeight()) / 2);
        this.setVisible(true);
    }

    public GameFrame(String title, User user) {
        super(title);

        this.user = user;
        this.gamePanel = new GamePanel(user);
        this.add(gamePanel, BorderLayout.CENTER);
        gameMenuController = new GameMenuController(gameMenu, gamePanel);
        this.add(gameMenu, BorderLayout.NORTH);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
        this.pack();

        Dimension screenSize = Config.deviceScreenSize;

        this.setLocation((screenSize.width - gamePanel.getScreenWidth()) / 2,
                (screenSize.height - gamePanel.getScreenHeight()) / 2);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame("BOMBER MAN");
    }

    private void confirmExit() {
        int confirmed = JOptionPane.showConfirmDialog(this,
                "<html><h2>Do you sure you want to exit?</h2></html>",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
        this.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        confirmExit();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
