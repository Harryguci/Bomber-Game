import controllers.LoginController;
import models.GameFrame;

public class Main implements Runnable {
    private Thread loginThread;
    private LoginController loginController;
    private GameFrame gameFrame = null;

    public Main() {
        loginController = new LoginController();
        loginThread = new Thread(this);
        loginThread.start();
    }

    public static void main(String[] args) {
        new Main();
    }

    public void update() {
        System.out.print("");
        if (loginController.isLogin()) {
            gameFrame = new GameFrame("BOMB GAME");
            loginController.disposeFrame();
            loginThread = null;
        }
    }

    @Override
    public void run() {
        while (loginThread != null) {
            update();
        }
    }
}