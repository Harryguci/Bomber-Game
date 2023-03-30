package controllers;

import config.db.Config;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameFrame extends JFrame implements WindowListener {
    private final GamePanelController gamePanelController;

    public GameFrame(String title) {
        super(title);
        this.gamePanelController = new GamePanelController();
        this.add(gamePanelController, BorderLayout.CENTER);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
        this.pack();

        Dimension screenSize = Config.deviceScreenSize;

        this.setLocation((screenSize.width - gamePanelController.getScreenWidth()) / 2,
                (screenSize.height - gamePanelController.getScreenHeight()) / 2);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame("BOMBER MAN");
    }

    private void confirmExit() {
        int confirmed = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit the program?",
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
