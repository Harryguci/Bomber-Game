package util;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

public class OpenURL implements ActionListener {
    private final URI uri;
    public OpenURL(URI uri) {
        this.uri = uri;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        open(uri);
    }

    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) { /* TODO: error handling */ }
        } else { /* TODO: error handling */ }
    }
}
