package controllers;

import views.LoginFrame;
import controllers.db.UserController;
import models.db.User;

import javax.swing.JOptionPane;

public class LoginController {
    private LoginFrame loginFrame;
    private String username = "", password = "";
    private boolean isLogin = false;
    private User user;

    public LoginController() {
        loginFrame = new LoginFrame(this);
    }

    public boolean validate(String username, String password) {
        // password and username must
        // - Have length greater or equal than 3
        // - Have character and least 1 number but dont start with a number.

        boolean checkPassword = password.length() >= 6 && password.matches("^[a-zA-Z0-9]+$");
        boolean checkUsername = username.length() >= 3 && username.matches("^(?=.*[0-9])(?!\\d).*[a-zA-Z0-9]+$");

        return checkUsername && checkPassword;
    }

    public User login(String username, String password) {
        if (!validate(username, password)) {
            JOptionPane.showMessageDialog(loginFrame, "Your information is wrong format", "Login Failed", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        User user = UserController.findOne(username, password);
        if (user != null) {
            this.username = user.getUsername();
            this.password = user.getPassword();

            JOptionPane.showMessageDialog(loginFrame, "<html><h2>WELCOME BACK</h2><p>USERNAME: " + user.getUsername() + "</p><p>PASSWORD: " + user.getPassword() + "</p><p>LEVEL: " + user.getLevel() + "</p><br><html>", "Login Successfully", JOptionPane.INFORMATION_MESSAGE);
            isLogin = true;
        } else
            JOptionPane.showMessageDialog(loginFrame, "Your account not found", "Login Failed", JOptionPane.WARNING_MESSAGE);

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