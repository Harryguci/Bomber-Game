package controllers;

import views.LoginFrame;
import controllers.db.UserController;
import models.db.User;

import javax.swing.JOptionPane;

public class LoginController {
    private LoginFrame loginFrame;
    private String username = "", password = "";
    private boolean isLogin = false;

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

    public void login(String username, String password) {
        if (!validate(username, password)) {
            JOptionPane.showMessageDialog(loginFrame, "Your information is wrong format", "Login Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = UserController.findOne(username, password);
        if (user != null) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            System.out.println("Login Successfully: Welcome " + user.getUsername());

            JOptionPane.showMessageDialog(loginFrame, "WELCOME BACK " + user.getUsername(), "Login Successfully", JOptionPane.INFORMATION_MESSAGE);
            isLogin = true;
        } else
            JOptionPane.showMessageDialog(loginFrame, "Your account not found", "Login Failed", JOptionPane.WARNING_MESSAGE);

    }

    public void disposeFrame() {
        this.loginFrame.dispose();
    }

    public boolean isLogin() {
        return isLogin;
    }

    public static void main(String[] args) {
        LoginController loginController = new LoginController();
    }
}