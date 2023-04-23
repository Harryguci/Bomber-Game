package controllers;

import views.LoginFrame;
import controllers.db.UserController;
import models.db.User;

import javax.swing.JOptionPane;
import javax.swing.text.Keymap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class LoginController {
    private final LoginFrame loginFrame;
    private boolean isLogin = false;
    private User user;

    public LoginController() {
        loginFrame = new LoginFrame(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loginFrame.getLoginButton()) {
                    String username = loginFrame.getUsernameField().getText();
                    String password = new String(loginFrame.getPasswordField().getPassword());

                    login(username, password);
                }
            }
        });

    }

    public boolean validate(String username, String password) {
        // password and username must
        // - Have length greater or equal than 3
        // - Have character and least 1 number but dont start with a number.

        boolean checkPassword = (password.length() >= 6) && password.matches("^[a-zA-Z0-9]+$");
        boolean checkUsername = (username.length() >= 3) && username.matches("^(?=.*[0-9])(?!\\d).*[a-zA-Z0-9]+$");

        return checkUsername && checkPassword;
    }

    public User login(String username, String password) {
        if (!validate(username, password)) {
            JOptionPane.showMessageDialog(loginFrame, "<html><h2>Your information is wrong format</h2></html>", "Login Failed", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        User user = UserController.findOne(username, password);
        if (user != null) {
            String username1 = user.getUsername();
            String password1 = user.getPassword();

            int level = user.getLevel() + 1;
            String html = "<html><head><style>body { font-family: Arial; font-size: 12px; margin: 20px; background: none; border: none; text-align: center;}h1 { color: red; font-size: 20pt; font-weight: bold; text-align: left; }p { text-indent: 14px; }a { color: blue; text-decoration: none; }li {margin: 10px 0px; text-align: left; font-size: 12px;}</style></head><body><h1>WELCOME BACK</h1><ul><li>USERNAME: %s</li><li>PASSWORD: %s</li><li>LEVEL: %d</body></html>".formatted(user.getUsername(), user.getPassword(), level);

            JOptionPane.showMessageDialog(loginFrame, html, "Login Successfully", JOptionPane.INFORMATION_MESSAGE);

            isLogin = true;
        } else
            JOptionPane.showMessageDialog(loginFrame, "<html><h2>Your account not found</h2></html>", "Login Failed", JOptionPane.WARNING_MESSAGE);

        this.user = user;

        return user;
    }

    public void disposeFrame() {
        this.loginFrame.dispose();
    }

    public boolean isLogin() {
        return isLogin;
    }

    public User getUser() {
        return user;
    }

    public boolean isFrameVisible() {
        return loginFrame.isVisible();
    }

    public static void main(String[] args) {
        LoginController loginController = new LoginController();
    }
}