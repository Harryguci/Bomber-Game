package controllers;

import config.db.Config;
import models.AbleMoveEntity;
import models.BackgroundType;
import models.Entity;
import models.Sprite;
import models.bomb.Bomb;
import models.bomb.Explosion;
import models.db.User;
import models.gui.*;
import models.mainObject.MainObject;
import models.tiles.Item;
import models.tiles.LowTile16bit;
import models.threat.Zombie;

import models.tiles.TileKind;
import org.w3c.dom.css.Rect;
import util.MyFunction;
import views.GuiImage;
import views.ScreenFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.util.*;
import java.util.List;

public class GamePanelController extends JPanel implements Runnable {
    private final User user;

    public enum StatusGame {
        START, PLAYING, PAUSE, GAME_OVER, TRANSITION, GAME_TUTORIAL, WIN_GAME,
    }

    public final static byte FPS = 60;
    private final int originalTitleSize = 16;
    private int scale = 4;
    public int tileSize = scale * originalTitleSize;
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 10;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;
    public static String _language = "ENG";
    private int viewportWidth = tileSize * 104; // viewport width default set 104 columns
    private int viewportHeight = screenHeight; // viewport height default set equals the window's height.
    private int xOffset = 0;    // x offset
    private int yOffset = 0;    // At 1.0 version don't use yet.
    private final Color _objectColor = new Color(50, 50, 50);       // for case can not get images
    private final Color _backgroundColor = new Color(214, 160, 84); // for case can not get images
    private boolean[][] _tileMap = new boolean[maxScreenRow][30];   // save the location matrix of tiles (walls) as a boolean matrix
    private final String[] _titleMapStr = new String[500];    // save the location matrix of tiles (walls) as a string matrix
    private final KeyInputController keyInputController; // manages keyboard event
    private final MouseInputController mouseInputController; // manages mouse event
    private final ArrayList<BufferedImage> backgroundImage = new ArrayList<>();  // store background images
    private final ArrayList<BufferedImage> tiles = new ArrayList<>();
    private Vector<Entity> _entities = new Vector<>();
    private Thread gameThread;  // main thread
    public StatusGame statusGame = StatusGame.START; // current status of game
    public MainObject player1; // main player
    private Font customFont;
    private int numberScores = 0, gameLevel = 0, numberOfThreat = 100;

    // TEST
    private ScreenFactory screenFactory;

    public GamePanelController(User user) {
        setLayout(null);
        setLocation(0, 0);

        this.user = user;

        initScreen();   // init information for game screen
        installFont(Path.of("font", "LilitaOne-Regular.ttf").toString());   // Get Font
        setBackground(Color.BLACK); // background default
        setDoubleBuffered(true);    // double buffered


        // KEYBOARD & MOUSE
        keyInputController = new KeyInputController(this);
        mouseInputController = new MouseInputController(this);
        addKeyListener(keyInputController);
        addMouseListener(mouseInputController);

        // MAIN PLAYER
        player1 = new MainObject(this, keyInputController);
        player1.setLocation(tileSize, tileSize);

        // Init buttons
        initButton();

        // Set up game objects dependent game level
        restartAtLevel(gameLevel);
        statusGame = StatusGame.START;

        // Start main thread
        startThread();
        setFocusable(true);
    }

    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Restart game
    public void restart() {
        player1.setDefault();
        statusGame = StatusGame.PLAYING;
        _entities.clear();
        xOffset = yOffset = 0;
        player1.setHeart(3);
        numberScores = 0;
        gameLevel = 0;
        setupMap("map3.txt");
        initThreat(Path.of("map", "zombieLocation3.txt").toString());
    }

    public void restartAtLevel(int level) {
        gameLevel = level;

        player1.setDefault();
//        statusGame = StatusGame.PLAYING;

        _entities.clear();
        xOffset = yOffset = 0;
        player1.setHeart(3);
        numberScores = 90;

        setupMap();
        initThreat();
        getTileImage();
        initBackgroundImage();

        System.out.println(gameLevel);
    }


    // Get font from external files.
    public void installFont(String fontName) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        fontName = Path.of(path.toString(), "src", "resources", fontName).toString();
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontName)).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            customFont = new Font("Arial", Font.BOLD, 20);
            System.out.println("Can not get font");
        }
    }

    // Init buttons
    public void initButton() {
//        mainButtonFactory = new MainButtonFactory(this);
        screenFactory = new ScreenFactory(this);
    }

    public void setLang(String lang) {
        _language = lang;
        screenFactory.setLang(lang);
    }


    public void initScreen() {
        int deviceScreenHeight = Config.deviceScreenSize.height;

        scale = Math.min(scale * (int) (deviceScreenHeight / screenHeight), 4);
        System.out.println(scale);

        tileSize = scale * originalTitleSize;
        screenWidth = tileSize * maxScreenCol;
        screenHeight = scale * originalTitleSize * maxScreenRow;

        // reset size of the game world
        viewportWidth = tileSize * 104; // viewport width default set 104 columns
        viewportHeight = screenHeight; // viewport height default set equals the window's height.

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setSize(new Dimension(screenWidth, screenHeight));
    }

    public void createOneThreat(int cl, int r) {
        while (_titleMapStr[r].charAt(cl) != '0') cl++;
        int zombieX = cl * tileSize;
        int zombieY = r * tileSize;
        _entities.add(new Zombie(zombieX, zombieY, this, keyInputController));
    }

    public void initThreat(String filePath) {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            filePath = Path.of(path.toString(), "src", "resources", filePath).toString();
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            numberOfThreat = Integer.parseInt(myReader.nextLine().trim());
            System.out.println(numberOfThreat);
            for (int i = 0; i < numberOfThreat && myReader.hasNext(); i++) {
                String temp = myReader.nextLine();
                int r = Integer.parseInt(temp.substring(0, temp.lastIndexOf(' ')).trim());
                int cl = Integer.parseInt(temp.substring(temp.lastIndexOf(' ')).trim());
                System.out.println(r + " " + cl);
                createOneThreat(cl, r);
            }

            myReader.close();

        } catch (Exception e) {
            System.out.println("Error: Read zombies map file");
        }
    }

    public void initThreat() {
        switch (gameLevel) {
            case 0 -> initThreat(Path.of("map", "zombieLocation3.txt").toString());
            case 1 -> initThreat(Path.of("map", "zombieLocation4.txt").toString());
            case 2 -> initThreat(Path.of("map", "zombieLocation5.txt").toString());
            default -> System.out.println("ERROR in GamePanel.initZombie()");
        }
    }

    public void initBackgroundImage() {
        backgroundImage.clear();
        switch (gameLevel) {
            case 0 -> {
                backgroundImage.add(LowTile16bit.GROUND_01.getImage());
                backgroundImage.add(LowTile16bit.GROUND_02.getImage());
                backgroundImage.add(LowTile16bit.GROUND_03.getImage());
            }
            case 1, 2 -> {
                backgroundImage.add(LowTile16bit.GROUND_04.getImage());
                backgroundImage.add(LowTile16bit.GROUND_05.getImage());
                backgroundImage.add(LowTile16bit.GROUND_04.getImage());
            }
        }
    }

    public void getTileImage() {
        tiles.clear();
        switch (gameLevel) {
            case 0 -> {
                System.out.println("added tile images for game level 1");
                tiles.add(LowTile16bit.WALL_01.getImage());
                tiles.add(LowTile16bit.WALL_02.getImage());
                tiles.add(LowTile16bit.WALL_03.getImage());
            }
            case 1, 2 -> {
                System.out.println("added tile images for game level 2");
                tiles.add(Sprite.TILE_SET02.getSubimage(2, 2));
                tiles.add(Sprite.TILE_SET02.getSubimage(2, 2));
                tiles.add(Sprite.TILE_SET02.getSubimage(2, 2));
            }
        }
    }

    public Entity addLowTile(int cl, int r, String type) {
        LowTile16bit e = null;
        switch (type) {
            case "WOOD_01" -> e = new LowTile16bit(LowTile16bit.WOOD_01);
            case "WOOD_02" -> e = new LowTile16bit(LowTile16bit.WOOD_02);
        }
        if (e == null) return null;
        e.setGamePanel(this);
        e.setSize(tileSize, tileSize);
        e.setLocation(cl * tileSize, r * tileSize);
        _entities.add(e);

        return e;
    }

    public boolean[][] setupMap() {
        switch (gameLevel) {
            case 0 -> {
                return setupMap("map3.txt");
            }
            case 1 -> {
                return setupMap("map4.txt");
            }
            case 2 -> {
                return setupMap("map5.txt");
            }
            default -> System.out.println("ERROR in GamePanelController.setupMap()");
        }
        return null;
    }

    public boolean[][] setupMap(String mapName) {
        _tileMap = new boolean[viewportHeight / tileSize][viewportWidth / tileSize];
        try {
            int r = 0;

            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

            mapName = Path.of(path.toString(), "src", "resources", "map", mapName).toString();

            File myObj = new File(mapName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                _titleMapStr[r] = data;

                FLAG:
                for (int i = 0; i < viewportWidth / tileSize; i++) {
                    char t = data.charAt(i);
                    switch (t) {
                        case '1' -> {
                            try {
                                _tileMap[r][i] = true;
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Out of range: tilMap");
                                break FLAG;
                            }
                        }
                        case '2' -> {
                            if (gameLevel == 0 && addLowTile(i, r, "WOOD_01") == null)
                                System.out.println("can not add a low tile");
                            if (gameLevel >= 1 && addLowTile(i, r, "WOOD_02") == null)
                                System.out.println("can not add a low tile");
                        }
                        case '3' -> {
                            if (addLowTile(i, r, "WOOD_02") == null)
                                System.out.println("can not add a low tile");
                        }
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
        clearPanel();
        switch (statusGame) {
            case START -> updateStartGame();
            case PLAYING -> updatePlayingGame();
            case PAUSE -> updatePauseGame();
            case TRANSITION -> updateTransitionGame();
            case GAME_TUTORIAL -> updateTutorialGame();
            case WIN_GAME -> System.out.println("WIN GAME...");
            case GAME_OVER -> {
                if (keyInputController.isPressed(KeyEvent.VK_S)) {
                    if (user != null)
                        user.setMaxScore(Math.max(user.getMaxScore(), numberScores));

                    restartAtLevel(0);
                    statusGame = StatusGame.PLAYING;
                }
            }
            default -> System.out.println("ERROR with status game: " + statusGame);
        }
    }

    public static void setTimeout(int delayMilliseconds, MyFunction f) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMilliseconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            f.apply();
        }).start();
    }

    public void updateStartGame() {
        remove(screenFactory.getTutorialScreen());
        add(screenFactory.getStartScreen());
        screenFactory.getStartScreen().setVisible(true);

        // press 'S' to start game
        if (keyInputController.isPressed(KeyEvent.VK_S)) {
            setTimeout(300, () -> {
                statusGame = StatusGame.PLAYING;
            });
        }
    }

    public void updatePlayingGame() {
        // Next level
        if (keyInputController.isPressed(KeyEvent.VK_K) || numberOfThreat == 0) {
            statusGame = StatusGame.TRANSITION;
            gameLevel++;
            if (gameLevel > 2) gameLevel = 0;
            restartAtLevel(gameLevel);

            return;
        }

        player1.update();

        if (player1.isDied()) {
            setTimeout(1000, () -> {
                statusGame = StatusGame.GAME_OVER;
            });

            return;
        }


        for (int i = 0; i < _entities.size(); i++) {
            Entity e = _entities.get(i);
            e.update();

            if (e instanceof Bomb && !((Bomb) e).isAlive()) {
                _entities.remove(e);
                i--;

            } else if (e instanceof AbleMoveEntity && !((AbleMoveEntity) e).isAlive()) {
                if (e instanceof Zombie) {
                    numberScores += 10;
                    numberOfThreat--;
                }
                _entities.remove(e);
                i--;
            } else if (e instanceof LowTile16bit && !((LowTile16bit) e).getAlive()) {

                int d = (int) (Math.random() * 1000) % 6;
                BufferedImage img = null;
                switch (d) {
                    case 0 -> img = Sprite.ITEMS.getSubimage(2, 2);
                    case 1 -> img = Sprite.ITEMS.getSubimage(1, 1);
                    case 2 -> img = Sprite.ITEMS.getSubimage(0, 1);
                    case 3 -> img = Sprite.ITEMS.getSubimage(2, 0);
                }

                if (d <= 4)
                    _entities.add(new Item(e.getX(), e.getY(),
                            tileSize, tileSize, img, d));

                _entities.remove(e);
                i--;
            } else if (e instanceof Explosion && !(((Explosion) e).getAlive())) {
                _entities.remove(e);
                i--;
            } else if (e instanceof Item && !((Item) e).isAlive()) {
                _entities.remove(e);
                i--;
            }
        }

        updateOffset();
    }

    public void updatePauseGame() {

    }

    public void updateTutorialGame() {
        add(screenFactory.getTutorialScreen());
        screenFactory.getTutorialScreen().setVisible(true);
    }

    public void updateTransitionGame() {
        add(screenFactory.getTransitionScreen());
        screenFactory.getTransitionScreen().setVisible(true);
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

        for (int i = 0; i < _entities.size(); i++) {
            if (_entities.get(i) instanceof Item) {
                Item item = (Item) _entities.get(i);
                item.setxOffset(xOffset);
            }
        }
//
//        for (Entity item : _entities) {
//            if (item instanceof Item) ((Item) item).setxOffset(xOffset);
//        }
    }
    // END OF [Update method]


    // [PAINT Screen method]
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(hints);

        switch (statusGame) {
            case START -> paintStartScreen(g2d);
            case PLAYING -> paintGamePlaying(g2d);
            case PAUSE -> paintPauseGame(g2d);
            case GAME_OVER -> paintOverScreen(g2d);
            case TRANSITION -> paintTransitionScreen(g2d);
            case GAME_TUTORIAL -> paintGameTutorial(g2d);
            case WIN_GAME -> System.out.println("WIN GAME...");
            default -> System.out.println("ERROR with status game: " + statusGame);
        }

        g2d.dispose();
    }

    public void clearPanel() {
        try {
            remove(screenFactory.getStartScreen());
            remove(screenFactory.getTutorialScreen());
            remove(screenFactory.getGameOverScreen());
            remove(screenFactory.getTransitionScreen());
        } catch (Exception ex) {
            System.out.println("Can not clear Panels");
        }
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
        if (GuiImage.heart == null) {
            g2d.setFont(customFont.deriveFont(Font.PLAIN, 25f));
            g2d.setColor(Color.WHITE);
            g2d.drawString("Heart: " + player1.getHeart(), 30, 30);
        } else
            for (int i = 0; i < player1.getHeart(); i++) {
                g2d.drawImage(GuiImage.heart, 40 * i + 20, 20, 35, 35, this);
            }

        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.fillRoundRect(40 * player1.getHeart() + 30, 20, 230, 40, 20, 20);

        g2d.setFont(customFont.deriveFont(Font.PLAIN, 20f));
        g2d.setColor(Color.WHITE);

        String scoreString = "" + numberScores;
        while (scoreString.length() < 3) scoreString = "0" + scoreString;

        g2d.drawString("Score:  " + scoreString + "   | Bomb:  " + player1.getNumberOfBomb(), 40 * player1.getHeart() + 40, 45);

        g2d.setColor(new Color(0, 0, 0, 150));

        int w = 350;
        g2d.fillRect(screenWidth - w, screenHeight - 50, w, 50);
        g2d.setColor(Color.WHITE);

        try {
            g2d.drawString("USERNAME: " + user.getUsername() + " | MAX SCORES: " + user.getMaxScore(), screenWidth - w + 20, screenHeight - 20);
        } catch (Exception e) {
            // System.out.println("USER IS NULL");
        }

        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.fillRoundRect(screenWidth - 100, 0, 100, 70, 20, 20);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Roboto", Font.BOLD, 15));
        g2d.drawString("LEVEL: " + gameLevel, screenWidth - 70, 50);
    }

    public void paintBackground(Graphics2D g2d) {

        int maxRow = viewportHeight / tileSize;
        int maxCol = viewportWidth / tileSize;

        g2d.setColor(_backgroundColor);

        for (int r = 0; r < maxRow; r++) {
            for (int cl = xOffset / tileSize - 1; cl <= Math.min(xOffset / tileSize + maxScreenCol + 1, maxCol - 1); cl++) {
                int d = (r + cl);
                if (d % 5 == 0)
                    g2d.drawImage(backgroundImage.get(2), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                else if (d % 7 > 4)
                    g2d.drawImage(backgroundImage.get(1), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                else
                    g2d.drawImage(backgroundImage.get(0), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
            }
        }
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
                        if (tiles.size() == 0 || tiles.get(0) == null)
                            g2d.fillRect(cl * tileSize - xOffset, r * tileSize, tileSize, tileSize);
                        else {
                            if ((cl + r) % 5 == 0)
                                g2d.drawImage(tiles.get(0), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                            else if ((cl + r) % 3 == 0)
                                g2d.drawImage(tiles.get(2), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                            else
                                g2d.drawImage(tiles.get(1), cl * tileSize - xOffset, r * tileSize - yOffset, tileSize, tileSize, this);
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("String out of range : " + cl);
                }
            }
        }

        if (gameLevel == 2) paintDarkMap(g2d);
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

    }

    public void paintGameTutorial(Graphics2D g2d) {

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

    public void paintPauseGame(Graphics2D g2d) {
        g2d.drawImage(GuiImage.backgroundGradient, 0, 0, screenWidth, screenHeight, this);
        BufferedImage menuImageVertical = GuiImage.menuVertical;

        int temp = screenHeight - 150;
        Dimension menuSize = new Dimension((int) ((temp * 1.0 / menuImageVertical.getHeight()) * menuImageVertical.getWidth()), screenHeight - 150);
        g2d.drawImage(menuImageVertical, (screenWidth - menuSize.width) / 2, (screenHeight - menuSize.height) / 2, menuSize.width, menuSize.height, this);
    }

    public void paintTransitionScreen(Graphics2D g2d) {

    }

    public void paintOverScreen(Graphics2D g2d) {
        screenFactory.getGameOverScreen().setVisible(true);
        add(screenFactory.getGameOverScreen());
    }
    // End of [Paint screen method]

    // [GETTER & SETTER]

    public void removeButton(Component component) {
        this.remove(component);
    }

    public boolean detectHardTile(int cl, int r) {
        try {
            if (this._tileMap[r][cl])
                return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    public boolean detectLowTile(int cl, int r) {
        for (Entity e : _entities)
            if (e instanceof LowTile16bit
                    && isCollision(new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight()),
                    new Rectangle(cl * tileSize, r * tileSize, tileSize, tileSize)))
                return true;
        return false;
    }

    public boolean detectAllTitles(int cl, int r) {
        return detectHardTile(cl, r) || detectLowTile(cl, r);
    }

    public static boolean isCollision(Rectangle r1, Rectangle r2) {
        return r1.x < r2.x + r2.width &&
                r1.x + r1.width > r2.x &&
                r1.y < r2.y + r2.height &&
                r1.y + r1.height > r2.y;
    }

    public Entity detectEntity(Rectangle rect1) {
        ArrayList<Rectangle> rects = new ArrayList<>();
        Iterator<Entity> it = _entities.iterator();

        try {
            while (it.hasNext()) {
                Entity entity = it.next();
                Rectangle rect2 = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
                rects.add(rect2);
            }
            for (int i = 0; i < rects.size(); i++) {
                if (GamePanelController.isCollision(rect1, rects.get(i))) {
                    return _entities.get(i);
                }
            }
        } catch (Exception ignored) {

        }

        return null;
    }

    public Entity detectEntity(Rectangle rect1, Entity currentEntity) {

//        for (Entity e : _entities) {
//            if (e == currentEntity) continue;
//
//            Rectangle rect2 = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
//
//            if (GamePanelController.isCollision(rect1, rect2))
//                return e;
//        }

        Iterator<Entity> it = _entities.iterator();

        try {
            while (it.hasNext()) {
                Entity e = it.next();
                if (e == currentEntity) continue;

                Rectangle rect2 = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());

                if (GamePanelController.isCollision(rect1, rect2))
                    return e;
            }
        } catch (Exception ignored) {

        }

        return null;
    }
    // [GETTER & SETTER]

    // Add an entity
    public void addEntity(Entity entity) {
        _entities.add(entity);
    }

    public boolean addExplosion(Explosion e) {
        int cl = e.getX() / tileSize;
        int r = e.getY() / tileSize;

        // this explosion is outside the game screen.
        boolean isOutside = (cl < 0 || r < 0 || cl >= viewportWidth / tileSize || r >= viewportHeight / tileSize);
        boolean isInsideAHardTile = detectHardTile(cl, r); // this explosion is stopped by a hard tile.

        if (isOutside || isInsideAHardTile) return false; // can not add this

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

    public int getScores() {
        return numberScores;
    }

    public int getLevel() {
        return gameLevel;
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

    public void setScores(int scores) {
        this.numberScores = scores;
    }

    // [END OF GETTER & SETTER]
}
