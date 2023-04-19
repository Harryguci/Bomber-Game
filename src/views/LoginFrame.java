package views;

import config.db.Config;
import controllers.LoginController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class LoginFrame extends JFrame implements KeyListener {
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;
    private LoginController loginController;

    private int width = 550, height = 420;

    private ActionListener loginHandle;

    public LoginFrame(ActionListener loginHandle) {
        // Set frame properties
        Color backgroundColor = new Color(70, 70, 70);
        Color textColor = Color.WHITE;

        setTitle("BOMBER GAME | Login");
        setSize(width, height);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // hide the title bar and window decorations

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.setSize(new Dimension(500, 300));
        formPanel.setPreferredSize(new Dimension(500, 300));

        // Create components
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        usernameLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        passwordLabel.setFont(new Font("Roboto", Font.PLAIN, 16));

        usernameLabel.setForeground(textColor);
        passwordLabel.setForeground(textColor);

        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        usernameField.setFont(new Font("Roboto", Font.PLAIN, 16));
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 16));

        usernameField.setBackground(new Color(70, 70, 120));
        passwordField.setBackground(new Color(70, 70, 120));

        usernameField.setForeground(textColor);
        passwordField.setForeground(textColor);

        Border border = BorderFactory.createLineBorder(new Color(100, 100, 200)); // create a LineBorder
        usernameField.setBorder(border); // set the border of the JTextField
        passwordField.setBorder(border); // set the border of the JTextField
        usernameField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);

        loginButton = new JButton("Login");
        loginButton.setBorder(null);
        loginButton.setForeground(new Color(70, 70, 70));
        loginButton.setBackground(Color.WHITE);

        cancelButton = new JButton("Cancel");
        cancelButton.setBorder(null);
        cancelButton.setForeground(new Color(100, 100, 100));
        cancelButton.setBackground(new Color(200, 200, 200));


        JLabel heading = new JLabel("BOMBER GAME");
        heading.setFont(new Font("Roboto", Font.BOLD, 33));
        heading.setHorizontalAlignment(SwingConstants.LEFT);
        heading.setForeground(new Color(150, 150, 255));
        heading.setSize(550, 50);
        heading.setLocation(width / 2 - 110, 30);

        // Add components to frame
        add(heading);

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        formPanel.add(cancelButton);
        formPanel.add(loginButton);

        formPanel.setLocation(25, 110);
        // Add action listener to login button
        loginButton.addActionListener(loginHandle);
        cancelButton.addActionListener(e -> dispose());
        formPanel.setBackground(backgroundColor);
        add(formPanel);

        Dimension screenSize = Config.deviceScreenSize;
        setLocation(new Point((screenSize.width - width) / 2, (screenSize.height - height) / 2));
        setResizable(false);
        getContentPane().setBackground(backgroundColor); // set background color here
        // Display the frame
        setVisible(true);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("ENTER");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
