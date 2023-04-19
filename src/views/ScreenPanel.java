package views;

import javax.swing.*;
import java.awt.*;

public abstract class ScreenPanel extends JPanel {
    protected JComponent[] components;

    protected ScreenPanel(int w, int h) {
        setPreferredSize(new Dimension(w, h));
        setSize(w, h);
        setLayout(null);
    }

    protected ScreenPanel(int w, int h, JComponent[] components) {
        setPreferredSize(new Dimension(w, h));
        setSize(w, h);
        setLayout(null);
        try {
            for (var x : components)
                if (x != null) add(x);

            this.components = components;
        } catch (NullPointerException e) {
            System.out.println("NULL");
        }
    }

    public void setLang(String lang) {
        if (components == null) return;
        if (lang.equals("VN")) {
            for (JComponent component : components) {
                if (component instanceof JButton) {
                    String str = ((JButton) component).getText();
                    switch (str) {
                        case "PLAY" -> ((JButton) component).setText("CHƠI");
                        case "TUTORIAL" -> ((JButton) component).setText("HƯỚNG DẪN");
                        case "BACK" -> ((JButton) component).setText("QUAY LẠI");
                        case "VN" -> ((JButton) component).setText("ENG");
                    }
                }
            }
        } else {
            for (JComponent component : components) {
                if (component instanceof JButton) {
                    String str = ((JButton) component).getText();
                    switch (str) {
                        case "CHƠI" -> ((JButton) component).setText("PLAY");
                        case "HƯỚNG DẪN" -> ((JButton) component).setText("TUTORIAL");
                        case "QUAY LẠI" -> ((JButton) component).setText("BACK");
                        case "ENG" -> ((JButton) component).setText("VN");
                    }
                }
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
