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
        if (loginController.isLogin()) {
            gameFrame = new GameFrame("BOMB GAME", loginController.getUser());
            loginController.disposeFrame();
            loginThread.interrupt();
        }
    }

    @Override
    public void run() {
        while (!loginThread.isInterrupted()) {
            update();
        }
    }
}