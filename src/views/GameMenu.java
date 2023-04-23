package views;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JMenuBar {
    JMenu start, tutorial, pause, help;
    JMenuItem startItem, tutorialItem, pauseItem, newGameỈtem, dialogHelpItem;

    public GameMenu() {
        super();
        start = new JMenu("Game");
        tutorial = new JMenu("Tutorial");
        pause = new JMenu("Pause");
        help = new JMenu("Help");

        startItem = new JMenuItem("Start screen");
        tutorialItem = new JMenuItem("Show Tutorial");
        pauseItem = new JMenuItem("Pause screen");
        newGameỈtem = new JMenuItem("New Game");
        dialogHelpItem = new JMenuItem("Dialog help");

        start.add(startItem);
        start.add(newGameỈtem);

        tutorial.add(tutorialItem);
        pause.add(pauseItem);

        help.add(dialogHelpItem);

        add(start);
        add(tutorial);
        add(pause);
        add(help);

        setBackground(Color.WHITE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setBounds(100, 100, 500, 500);
        frame.add(new GameMenu(), BorderLayout.NORTH);

        frame.setVisible(true);
    }

    public JMenu getStartMenu() {
        return this.start;
    }

    public JMenu getTutorialMenu() {
        return this.tutorial;
    }

    public JMenu getPauseMenu() {
        return this.pause;
    }

    public JMenuItem getStartItem() {
        return this.startItem;
    }

    public JMenuItem getTutorialItem() {
        return this.tutorialItem;
    }

    public JMenuItem getPauseItem() {
        return this.pauseItem;
    }

    public JMenuItem getNewGameỈtem() {
        return newGameỈtem;
    }

    public JMenuItem getDialogHelpItem() {
        return dialogHelpItem;
    }
}
