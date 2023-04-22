package views;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JMenuBar {
    JMenu start, tutorial, pause;
    JMenuItem startItem, tutorialItem, pauseItem;

    public GameMenu() {
        super();
        start = new JMenu("Start");
        tutorial = new JMenu("Tutorial");
        pause = new JMenu("Pause");

        startItem = new JMenuItem("Start screen");
        tutorialItem = new JMenuItem("Show Tutorial");
        pauseItem = new JMenuItem("Pause screen");

        start.add(startItem);
        tutorial.add(tutorialItem);
        pause.add(pauseItem);

        add(start);
        add(tutorial);
        add(pause);

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

}
