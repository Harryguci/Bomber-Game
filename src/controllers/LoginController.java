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
    private String username = "", password = "";
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
            this.username = user.getUsername();
            this.password = user.getPassword();

            JOptionPane.showMessageDialog(loginFrame, "<html><h1>WELCOME BACK</h1><ul><li>USERNAME: " + user.getUsername() + "</li><li>PASSWORD: " + user.getPassword() + "</li>" +
                    "<li>LEVEL: " + user.getLevel() + "</li></ul><html>", "Login Successfully", JOptionPane.INFORMATION_MESSAGE);
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

    public static void main(String[] args) {
        LoginController loginController = new LoginController();
    }
}