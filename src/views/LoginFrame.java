package views;

import config.db.Config;
import controllers.LoginController;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;
    private LoginController loginController;

    private int width = 500, height = 350;
    public LoginFrame(LoginController loginController) {
        // Set frame properties
        Color backgroundColor = new Color(70, 70, 70);
        Color textColor = Color.WHITE;

        this.loginController = loginController;

        setTitle("BOMBER GAME | Login");
        setSize(500, 350);
        setLayout(new GridLayout(7, 2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // hide the title bar and window decorations

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

        List<JLabel> listSpace = new ArrayList<>();

        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));

        JLabel heading = new JLabel("BOMBER GAME");
        heading.setFont(new Font("Roboto", Font.BOLD, 33));
        heading.setHorizontalAlignment(SwingConstants.LEFT);
        heading.setForeground(new Color(150, 150, 255));

        // Add components to frame
        add(heading);
        add(new JLabel(""));
        add(new JLabel(""));

        add(usernameLabel);
        add(usernameField);

        add(passwordLabel);
        add(passwordField);

        add(new JLabel(""));
        add(new JLabel(""));

        add(cancelButton);
        add(loginButton);

        // Add action listener to login button
        loginButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());

        Dimension screenSize = Config.deviceScreenSize;
        setLocation(new Point((screenSize.width - width) / 2, (screenSize.height - height) / 2));
        setResizable(false);
        getContentPane().setBackground(backgroundColor); // set background color here
        // Display the frame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Do login validation here
            // For example, you could compare the entered username and password with a database of valid credentials
            // If the login is successful, you could open a new window or do other actions
            loginController.login(username, password);
        }
    }
}
