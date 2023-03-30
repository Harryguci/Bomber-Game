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

    public void login(String username, String password) {
        User user = UserController.findOne(username, password);
        if (user != null) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            System.out.println("Login Successfully: Welcome " + user.getUsername());

            JOptionPane.showMessageDialog(loginFrame, "Login Successfully: Welcome " + user.getUsername());
            isLogin = true;
        } else
            JOptionPane.showMessageDialog(loginFrame, "Login Failed");

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