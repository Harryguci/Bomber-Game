package controllers;

import models.AbleMoveEntity;
import models.BackgroundType;
import models.Entity;
import models.bomb.Bomb;
import models.bomb.Explosion;
import models.gui.GButton;
import models.gui.GString;
import models.mainObject.MainObject;
import models.tiles.LowTile16bit;
import models.threat.Zombie;
import util.ImageReader;
import models.tiles.TileKind;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GamePanelController extends JPanel implements Runnable {
    enum StatusGame {
        START, PLAYING, PAUSE, GAME_OVER, TRANSITION, GAME_TUTORIAL, WIN_GAME,
    }

    private final byte FPS = 60;
    private final int originalTitleSize = 16;
    private int scale = 3;
    public int tileSize = scale * originalTitleSize;
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 10;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;
    private String _language = "ENG";

    private int viewportWidth = tileSize * 104; // viewport width default set 104 columns
    private int viewportHeight = screenHeight; // viewport height default set equals the window's height.

    private int xOffset = 0;    // x offset
    private int yOffset = 0;    // At 1.0 version don't use yet.

    private Thread gameThread;  // main thread

    private Color _objectColor = new Color(50, 50, 50),
            _backgroundColor = new Color(214, 160, 84),
            _backgroundColor2 = new Color(250, 160, 84);

    private boolean[][] _tileMap = new boolean[maxScreenRow][30];   // save the location matrix of tiles (walls) as a boolean matrix
    private final String[] _titleMapStr = new String[500];    // save the location matrix of tiles (walls) as a string matrix
    private StatusGame statusGame = StatusGame.START; // current status of game
    private final KeyInputController keyInputController; // manages keyboard event
    private final MouseInputController mouseInputController; // manages mouse event
    public MainObject player1; // main player
    private final List<Entity> _entities = new ArrayList<>();

    private final List<BufferedImage> tiles = new ArrayList<>();
    private final List<BufferedImage> backgroundImage = new ArrayList<>();
    private final Map<String, GButton> buttonMap = new HashMap<>();
    private final BufferedImage _heartImage = ImageReader.Read("background\\heart1.png");
    private final BufferedImage menuImage = ImageReader.Read(Path.of("GameUI", "menu_horizontal.png").toString());
    private final BufferedImage menuImageVertical = ImageReader.Read(Path.of("GameUI", "menu.png").toString());
    private Font customFont;
    private int _animate = 0, delay = 0, numberScores = 0, gameLevel = 0;

    public GamePanelController() {
        initScreen();
        installFont("font\\LilitaOne-Regular.ttf");
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        // Dependent game level
        switch (gameLevel) {
            case 0 -> {
                setupMap("map3.txt");
                getTileImage();
                initBackgroundImage();
                initZombie("map\\zombieLocation3.txt");
            }
            case 1 -> {
                // This level is not creation
            }
        }

        keyInputController = new KeyInputController(this);
        mouseInputController = new MouseInputController(this);

        addKeyListener(keyInputController);
        addMouseListener(mouseInputController);

        player1 = new MainObject(this, keyInputController);
        player1.setLocation(tileSize, tileSize);

        initButton();

        startThread();
        setFocusable(true);
    }

    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void restart() {
        player1.setDefault();
        statusGame = StatusGame.PLAYING;
        _entities.clear();
        xOffset = yOffset = 0;
        player1.setHeart(3);
        numberScores = 0;
        gameLevel = 0;
        setupMap("map3.txt");
        initZombie("map\\zombieLocation3.txt");
    }

    public void installFont(String fontName) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        fontName = path + "\\src\\resources\\" + fontName;

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontName)).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            customFont = new Font("Arial", Font.BOLD, 20);
            System.out.println("Can not get font");
        }

    }

    public void initButton() {

        int w = 120, h = 50;
        GButton playBtn = new GButton("PLAY", (screenWidth - w) / 2, screenHeight / 3, w, h, this, mouseInputController);

        w = 150;
        GButton tutorialBtn = new GButton("TUTORIAL", (screenWidth - w) / 2, screenHeight / 3 + (int) (h * 1.3), w, h, this, mouseInputController);

        // add some buttons in here
        playBtn.setFont(customFont);
        tutorialBtn.setFont(customFont);
        buttonMap.put(playBtn.getName(), playBtn);
        buttonMap.put(tutorialBtn.getName(), tutorialBtn);
    }

    public void initScreen() {
        tileSize = scale * originalTitleSize;
        screenWidth = tileSize * maxScreenCol;
        screenHeight = tileSize * maxScreenRow;
        setPreferredSize(new Dimension(screenWidth, screenHeight));
    }

    public void createOneZombie(int cl, int r) {
        while (_titleMapStr[r].charAt(cl) != '0') cl++;
        int zombieX = cl * tileSize;
        int zombieY = r * tileSize;
        _entities.add(new Zombie(zombieX, zombieY, this, keyInputController));
    }

    public void initZombie(String filePath) {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            filePath = path + "\\src\\resources\\" + filePath;
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            int n = Integer.parseInt(myReader.nextLine());

            for (int i = 0; i < n; i++) {
                if (!myReader.hasNext()) break;
                String temp = myReader.nextLine();
                int r = Integer.parseInt(temp.substring(0, temp.lastIndexOf(' ')).trim());
                int cl = Integer.parseInt(temp.substring(temp.lastIndexOf(' ')).trim());

                createOneZombie(cl, r);
            }

            myReader.close();

        } catch (Exception e) {
            System.out.println("Error: Read zombies map file");
        }
    }

    public void initBackgroundImage() {
        switch (gameLevel) {
            case 0 -> {
                backgroundImage.add(LowTile16bit.GROUND_01.getImage());
                backgroundImage.add(LowTile16bit.GROUND_02.getImage());
                backgroundImage.add(LowTile16bit.GROUND_03.getImage());
            }
        }
    }

    public void getTileImage() {
        switch (gameLevel) {
            case 0 -> {
                tiles.add(LowTile16bit.WALL_01.getImage());
                tiles.add(LowTile16bit.WALL_02.getImage());
                tiles.add(LowTile16bit.WALL_03.getImage());
            }
        }
    }

    public boolean getTileImage(String fileName) {
        BufferedImage temp = ImageReader.Read("tiles\\" + fileName); // Tile_11.png

        tiles.add(temp);

        return temp != null;
    }

    public Entity addLowTile(int cl, int r, String type) {
        LowTile16bit e = null;
        switch (type) {
            case "WOOD_01" -> e = new LowTile16bit(LowTile16bit.WOOD_01);
        }
        if (e == null) return null;
        e.setGamePanel(this);
        e.setSize(tileSize, tileSize);
        e.setLocation(cl * tileSize, r * tileSize);
        _entities.add(e);

        return e;
    }

    public boolean[][] setupMap(String mapName) {

        _tileMap = new boolean[viewportHeight / tileSize][viewportWidth / tileSize];
        try {
            int r = 0;

            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            mapName = path + "\\src\\resources\\map\\" + mapName;

            File myObj = new File(mapName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                _titleMapStr[r] = data;

                for (int i = 0; i < viewportWidth / tileSize; i++) {
                    char t = data.charAt(i);

                    if (t == '1')
                        try {
                            _tileMap[r][i] = true;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Out of range: tilMap");
                            break;
                        }
                    else if (t == '2') {
                        if (addLowTile(i, r, "WOOD_01") == null)
                            System.out.println("can not add a low tile");
                    }
                }
                r++;
            }

            myReader.close();
            return _tileMap;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    @Override
    public void run() {
        // Run game
        double drawInterval = (1e9) / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {

                // 1. Update: information such as character information
                update();

                // 2. Draw: the screen with updated information
                repaint();
                delta--;
            }
        }
    }

    // [Update method]
    public void update() {
        switch (statusGame) {
            case START -> updateStartGame();
            case PLAYING -> updatePlayingGame();
            case PAUSE -> System.out.println("PAUSE...");
            case GAME_OVER -> {
                if (keyInputController.isPressed(KeyEvent.VK_S)) {
                    restart();
                }
            }
            case TRANSITION -> System.out.println("TRANSITION...");
            case GAME_TUTORIAL -> {
                //   System.out.println("GAME TUTORIAL...");
                --delay;
                if (buttonMap.containsKey("BACK TO START")) {
                    GButton btn = buttonMap.get("BACK TO START");
                    if (delay < -7500)
                        delay = -1;

                    btn.update();
                    if (btn.isPressed()) {
                        if (delay < 0) delay = 5;
                        if (delay == 0) {
                            statusGame = StatusGame.START;
                            buttonMap.remove("BACK TO START");
                        }
                    }
                }
            }
            case WIN_GAME -> System.out.println("WIN GAME...");
            default -> System.out.println("ERROR with status game: " + statusGame);
        }
    }

    public void updateStartGame() {
        --_animate;
        --delay;

        // don't let _animate and delay too small
        if (_animate < -7500)
            _animate = 7501;
        if (delay < -7500)
            delay = -1;

        // press 'S' to start game
        if (keyInputController.isPressed(KeyEvent.VK_S)) {
            if (delay < 0) delay = 10;
            if (delay == 0)
                statusGame = StatusGame.PLAYING;
        }

        // click on the play button
        if (buttonMap.containsKey("TUTORIAL")) {
            buttonMap.get("TUTORIAL").update();

            if (buttonMap.get("TUTORIAL").isPressed()) {
                if (delay < 0)
                    delay = 5;
                else if (delay == 0) {
                    int w = 200;
                    int h = 70;
                    GButton backBtn = new GButton("BACK TO START", (screenWidth - w) / 2, screenHeight - 150, w, h, this, mouseInputController);
                    backBtn.setFont(customFont.deriveFont(Font.BOLD, 20f));

                    buttonMap.put(backBtn.getName(), backBtn);
                    statusGame = StatusGame.GAME_TUTORIAL;
                    buttonMap.get("TUTORIAL").setPressed(false);
                }
            }
        }

        if (buttonMap.containsKey("PLAY")) {
            buttonMap.get("PLAY").update();

            if (buttonMap.get("PLAY").isPressed()) {
                if (delay < 0)
                    delay = 5;
                else if (delay == 0) {
                    statusGame = StatusGame.PLAYING;
                    buttonMap.get("PLAY").setPressed(false);
                }
            }
        }

    }

    public void updatePlayingGame() {
        player1.update();

        if (player1.isDied()) {
            delay--;
            if (delay <= 0)
                statusGame = StatusGame.GAME_OVER;
            return;
        }

        for (int i = 0; i < _entities.size(); i++) {
            Entity e = _entities.get(i);
            _entities.get(i).update();

            if (e instanceof Bomb && !((Bomb) e).isAlive()) {
                _entities.remove(i);
                i--;
            } else if (e instanceof AbleMoveEntity && !((AbleMoveEntity) e).isAlive()) {
                if (e instanceof Zombie) numberScores += 10;
                _entities.remove(i);
                i--;
            } else if (e instanceof LowTile16bit && !((LowTile16bit) e).getAlive()) {
                _entities.remove(i);
                i--;
            } else if (e instanceof Explosion && !(((Explosion) e).getAlive())) {
                _entities.remove(i);
                i--;
            }
        }
        updateOffset();
    }

    public void updateOffset() {
        if (player1.isMove()) {
            if (keyInputController.isPressed("left") && player1.getX() + player1.getSpeedX() < viewportWidth && player1.getX() - xOffset < screenWidth / 3) {
                xOffset -= player1.getSpeedX();
            } else if (keyInputController.isPressed("right") && player1.getX() - xOffset - player1.getSpeedX() > 0 && player1.getX() - xOffset > 2 * screenWidth / 3) {
                xOffset += player1.getSpeedX();
            }
        }

        if (xOffset < 0) xOffset = 0;
        else if (xOffset > viewportWidth - screenWidth) xOffset = viewportWidth - screenWidth;
    }
    // END OF [Update method]


    // [PAINT Screen method]
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        switch (statusGame) {
            case START -> paintStartScreen(g2d);
            case PLAYING -> paintGamePlaying(g2d);
            case PAUSE -> System.out.println("PAUSE...");
            case GAME_OVER -> paintOverScreen(g2d);
            case TRANSITION -> System.out.println("TRANSITION...");
            case GAME_TUTORIAL -> paintGameTutorial(g2d);
            case WIN_GAME -> System.out.println("WIN GAME...");
            default -> System.out.println("ERROR with status game: " + statusGame);
        }

        g2d.dispose();
    }

    public void paintGrid(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 50));
        for (int r = 0; r < maxScreenRow; r++) {
            for (int cl = 0; cl < maxScreenCol; cl++) {
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.drawRect(cl * tileSize, r * tileSize, tileSize, tileSize);
            }
        }
    }

    public void paintInformation(Graphics2D g2d) {
        if (_heartImage == null) {
            g2d.setFont(customFont.deriveFont(Font.PLAIN, 25f));
            g2d.setColor(Color.WHITE);
            g2d.drawString("Heart: " + player1.getHeart(), 30, 30);
        } else
            for (int i = 0; i < player1.getHeart(); i++) {
                g2d.drawImage(_heartImage, 40 * i + 20, 20, 35, 35, this);
            }

        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.fillRoundRect(40 * player1.getHeart() + 30, 20, 230, 40, 20, 20);

        g2d.setFont(customFont.deriveFont(Font.PLAIN, 20f));
        g2d.setColor(Color.WHITE);

        String scoreString = "" + numberScores;
        while (scoreString.length() < 3) scoreString = "0" + scoreString;

        g2d.drawString("Score:  " + scoreString + "   | Bomb:  " + player1.getNumberOfBomb(), 40 * player1.getHeart() + 40, 45);
    }

    public void paintBackground(Graphics2D g2d) {

        int maxRow = viewportHeight / tileSize;
        int maxCol = viewportWidth / tileSize;

        g2d.setColor(_backgroundColor);

        for (int r = 0; r < maxRow; r++) {
            for (int cl = xOffset / tileSize - 1; cl <= Math.min(xOffset / tileSize + maxScreenCol + 1, maxCol - 1); cl++) {
                int d = (r + cl);
                if (d % 5 == 0)
                    g2d.drawImage(backgroundImage.get(BackgroundType.GROUND_03.ordinal()), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                else if (d % 7 > 4)
                    g2d.drawImage(backgroundImage.get(BackgroundType.GROUND_02.ordinal()), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                else
                    g2d.drawImage(backgroundImage.get(BackgroundType.GROUND_01.ordinal()), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
            }
        }
//        paintGrid(g2d);
    }


    public void paintMap(Graphics2D g2d) {
        int maxCol = viewportWidth / tileSize;
        int maxRow = viewportHeight / tileSize;

        for (int r = 0; r < maxRow; r++) {
            for (int cl = 0; cl < maxCol; cl++) {
                if (cl > 1 && (cl + 1) * tileSize < xOffset || (cl - 1) * tileSize - xOffset > screenWidth) continue;

                try {
                    if (_titleMapStr[r].charAt(cl) == '1') {
                        g2d.setColor(_objectColor);
                        if (tiles.size() == 0 || tiles.get(TileKind.WALL_01.ordinal()) == null)
                            g2d.fillRect(cl * tileSize - xOffset, r * tileSize, tileSize, tileSize);
                        else {
                            if ((cl + r) % 5 == 0)
                                g2d.drawImage(LowTile16bit.WALL_02.getImage(), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                            else if ((cl + r) % 3 == 0)
                                g2d.drawImage(LowTile16bit.WALL_03.getImage(), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                            else
                                g2d.drawImage(LowTile16bit.WALL_01.getImage(), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("String out of range : " + cl);
                }
            }
        }


    }

    public void paintDarkMap(Graphics2D g2d) {
        int maxCol = viewportWidth / tileSize;
        int maxRow = viewportHeight / tileSize;

        // Map tat den
        int d = 4;
        for (int r = 0; r < maxRow * d; r++) {
            for (int cl = 0; cl < maxCol * d; cl++) {

                if (cl > 1 && (cl + 1) * tileSize / 4 < xOffset || (cl - 1) * tileSize / 4 - xOffset > screenWidth)
                    continue;

                if (Math.abs(cl * tileSize / d - player1.getX()) <= tileSize * 2
                        && Math.abs(r * tileSize / d - player1.getY()) <= tileSize * 2)
                    continue;

                g2d.setColor(Color.BLACK);
                g2d.fillRect(cl * tileSize / d - xOffset, r * tileSize / d, tileSize / d, tileSize / d);
            }
        }
    }

    public void paintStartScreen(Graphics2D g2d) {
        g2d.setColor(new Color(64, 64, 64));
        g2d.fillRect(0, 0, screenWidth, screenHeight);
        g2d.setColor(Color.WHITE);

        GString.drawCenteredString(g2d, "BOMB GAME !", new Rectangle(0, 0, screenWidth, 100), customFont.deriveFont(Font.BOLD, 30f));

        int menuHeight = screenHeight - 50;
        int menuWidth = (int) ((menuHeight * 1.0 / menuImageVertical.getHeight()) * menuImageVertical.getWidth());

        g2d.drawImage(menuImageVertical, (screenWidth - menuWidth) / 2, (screenHeight - menuHeight) / 2, menuWidth, menuHeight, this);

        if (buttonMap.containsKey("PLAY")) {
            buttonMap.get("PLAY").draw(g2d);
        }
        if (buttonMap.containsKey("TUTORIAL")) {
            buttonMap.get("TUTORIAL").draw(g2d);
        }
    }

    public void paintGameTutorial(Graphics2D g2d) {
        int menuHeight = screenHeight - 50;
        int menuWidth = (int) (screenWidth * 0.7);
        String content = """
                - USE [UP] [DOWN] [LEFT] [RIGHT] to move player
                - USE [SPACE] to put bombs
                - You have to kill all monsters to win
                - After win a part you will move to next level""";

        g2d.setColor(new Color(60, 60, 60));
        g2d.fillRect(0, 0, screenWidth, screenHeight);
        g2d.drawImage(menuImage, (screenWidth - menuWidth) / 2, (screenHeight - menuHeight) / 2, menuWidth, menuHeight, this);
        GString.drawCenteredString(g2d, "GAME TUTORIAL", new Rectangle((screenWidth - menuWidth) / 2, 70, menuWidth, 50), customFont.deriveFont(Font.PLAIN, 30f));
        GString.drawMultipleLine(g2d, content, (screenWidth - menuWidth) / 2 + 50, 150, customFont.deriveFont(Font.PLAIN, 20f));

        if (buttonMap.containsKey("BACK TO START")) {
            buttonMap.get("BACK TO START").draw(g2d);
        }
    }

    public void paintGamePlaying(Graphics2D g2d) {
        paintBackground(g2d);
        for (Entity x : _entities) {
            if (x != null && x.getX() - xOffset > 0)
                x.draw(g2d);
        }

        paintMap(g2d);

        player1.draw(g2d);
        paintInformation(g2d);
    }

    public void paintOverScreen(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        g2d.setColor(Color.RED);
        GString.drawCenteredString(g2d, "Game Over", new Rectangle(0, 0, screenWidth, screenHeight),
                customFont.deriveFont(Font.PLAIN, 40f));

        g2d.setColor(Color.BLACK);
        GString.drawCenteredString(g2d, "Press 'S' to Play Again", new Rectangle(0, screenHeight / 2, screenWidth, 100),
                customFont.deriveFont(Font.PLAIN, 20f));
    }

    // End of [Paint screen method]

    // [GETTER & SETTER]

    public boolean detectHardTile(int cl, int r) {
        try {
            if (this._tileMap[r][cl])
                return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
    public boolean detectTitle(int cl, int r) {
        try {
            if (this._tileMap[r][cl])
                return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        for (Entity e : _entities)
            if (e instanceof LowTile16bit
                    && isCollision(new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight()),
                    new Rectangle(cl * tileSize, r * tileSize, tileSize, tileSize)))
                return true;

        return false;
    }

    public static boolean isCollision(Rectangle r1, Rectangle r2) {
        // check if the boundaries of the two rectangles intersect
        return r1.x < r2.x + r2.width &&
                r1.x + r1.width > r2.x &&
                r1.y < r2.y + r2.height &&
                r1.y + r1.height > r2.y;
    }

    public Entity detectEntity(Rectangle rect1) {
        for (Entity e : _entities) {
            Rectangle rect2 = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());

            if (GamePanelController.isCollision(rect1, rect2))
                return e;
        }
        return null;
    }

    public Entity detectEntity(Rectangle rect1, Entity currentEntity) {
        for (Entity e : _entities) {
            if (e == currentEntity) continue;

            Rectangle rect2 = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());

            if (GamePanelController.isCollision(rect1, rect2))
                return e;
        }
        return null;
    }


    // [GETTER & SETTER]
    public void setDelay(int num) {
        delay = num;
    }

    // Add an entity
    public void addEntity(Entity entity) {
        _entities.add(entity);
    }
    public boolean addExplosion(Explosion e) {
        int cl = e.getX() / tileSize;
        int r = e.getY() / tileSize;

        if (cl < 0 || r < 0 || cl >= viewportWidth / tileSize || r >= viewportHeight / tileSize) return false;
        if (detectHardTile(cl, r)) return false;
        System.out.println("ADD Explosion");
        _entities.add(e);
        return true;
    }

    // Get entity at (x, y)
    public Entity getEntityAtXY(int x, int y) {
        for (Entity e : _entities) {
            if (e.getX() <= x && e.getX() + e.getWidth() >= x &&
                    e.getY() <= y && e.getY() + e.getWidth() >= y) {
                return e;
            }
        }

        return null;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getViewportWidth() {
        return viewportWidth;
    }

    public int getViewportHeight() {
        return viewportHeight;
    }

    public boolean[][] getTileMap() {
        return _tileMap;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public String[] getTileMapAsString() {
        return this._titleMapStr;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int sc) {
        scale = sc;
    }

    public void setOffset(int xOffset) {
        if (viewportWidth - xOffset >= screenWidth) this.xOffset = xOffset;
        else
            this.xOffset = viewportWidth - screenWidth;
    }

    public void setOffset(int xOffset, int yOffset) {
        setOffset(xOffset);
        if (viewportHeight - yOffset >= screenHeight) this.yOffset = yOffset;
        else
            this.yOffset = viewportHeight - screenHeight;
    }

    // [END OF GETTER & SETTER]
}
