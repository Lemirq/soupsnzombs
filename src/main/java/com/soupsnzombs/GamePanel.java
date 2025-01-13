package com.soupsnzombs;

import javax.swing.*;
import com.soupsnzombs.UI.*;
import com.soupsnzombs.UI.MainMenu.*;
import com.soupsnzombs.UI.Shop.Shop;
import com.soupsnzombs.buildings.*;
import com.soupsnzombs.entities.*;
import com.soupsnzombs.entities.zombies.*;
import com.soupsnzombs.utils.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static com.soupsnzombs.utils.FontLoader.*;

public class GamePanel extends JPanel implements Runnable, ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /*
     * flow:
     * main menu -> instructions -> game -> name select (option to not record score)
     * -> game over -> main menu
     */
    public enum GameState {
        MAIN_MENU, OPTIONS, GAME, PAUSE, GAMEOVER, SHOP, SCORES, INSTRUCTIONS, CREDITS, NAME_SELECT
    }

    public enum PlayerDir {
        UP, DOWN, LEFT, RIGHT
    }

    public static boolean debugging = false;

    public static GameState gameState = GameState.MAIN_MENU;

    // Game loop variables
    private boolean running = false;
    public static double elapsedTime = 0;
    private long lastDamageTime = 0; // for player invincibility
    private Thread gameThread;
    private long lastTime;
    private final int FPS = 120;
    private final double TIME_PER_TICK = 1000000000 / FPS;

    Timer timer;
    int seconds = 0;
    // private long time1 = 0;
    // private long time2 = 0;
    // private boolean gameOverHandled = false;

    // Movement variables
    public static int offsetX = 0; // Offset for the grid's X position
    public static int offsetY = 0; // Offset for the grid's Y position
    public static int MOVE_SPEED = 1; // Speed of movement
    public static AffineTransform oldTransformation;
    GameMap map;
    public static int screenWidth = 1200;
    public static int screenHeight = 900;

    // Grid variables
    public static final int[] X_Bounds = { -5000, 5000 };
    public static final int[] Y_Bounds = { -2000, 2000 };
    AllBuildings buildings = new AllBuildings();
    AllCoins coins = new AllCoins();
    AllZombies zombies;
    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean dropPressed = false;
    public static boolean shootPressed = false;
    public static PlayerDir direction = PlayerDir.UP;
    public Player player;
    public MenuGUI menu = new MenuGUI();
    public NameSelect name = new NameSelect();
    public Shop shop = new Shop();
    public Scores scores = new Scores();
    public Instructions instruct = new Instructions();
    public Credits credits = new Credits();
    public static ArrayList<GunDrop> gunDrops = new ArrayList<>();
    public EntranceBuilding prototypeBuilding1 = new EntranceBuilding(1000, 900, 300, 500, 160, 1, 40);
    public EntranceBuilding prototypeBuilding3 = new EntranceBuilding(1000, 500, 700, 500, 0, 0, 40);
    public EntranceBuilding prototypeBuilding2 = new EntranceBuilding(2000, 1000, 1000, 300, 200, 4, 65);
    public EntranceBuilding prototypeBuilding4 = new EntranceBuilding(2000 + 1000 - 65 - 65, 1000, 800, 1000, 0, 0, 65);
    public ShopBuilding shopEntity = new ShopBuilding(500, -200, 400, 200);
    public ArrayList<HealthDrop> healthDrops = new ArrayList<>();
    public Inventory inventory;

    public ShopBuilding getShop() {
        return shopEntity;
    }

    public Player getPlayer() {
        return this.player;
    }

    public synchronized void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        lastTime = System.nanoTime();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / TIME_PER_TICK;
            if (gameState == GameState.GAME)
                elapsedTime += (now - lastTime) / 1_000_000_000.0; // Update elapsed time, THIS IS WHERE SCORE IS
                                                                   // CALCULATED
            lastTime = now;

            // game over testing
            // if (elapsedTime >= 2) {
            // gameState = GameState.GAMEOVER;

            // }

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

        }
    }

    public enum CollisionDirection {
        NONE, TOP, BOTTOM, LEFT, RIGHT
    }

    private void update() {
        if (!player.alive && gameState != GameState.GAMEOVER && gameState != GameState.NAME_SELECT
                && gameState != GameState.MAIN_MENU) {
            gameState = GameState.GAMEOVER;
            return;
        }

        if (gameState == GameState.MAIN_MENU) {
            elapsedTime = 0; // Reset elapsed time
            player.alive = true; // Reset alive status
            player.setHealth(100); // Reset player health
            Player.money = 0; // Reset player coins
            Player.score = 0; // Reset player score
            player.x = GamePanel.screenWidth / 2 - player.width / 2;
            player.y = GamePanel.screenHeight / 2 - player.height / 2;
            offsetX = 0;
            offsetY = 0; // Reset player position
            zombies.clear(); // Removes all zombies
            NameSelect.name = new StringBuilder(""); // Clears leaderboard name input stream
            AllZombies.waveNumber = 1; // Set zombie wave to 1
        }

        if (gameState == GameState.GAMEOVER) {
            timer.start();
            if (seconds == 2) {
                gameState = GameState.NAME_SELECT;
                seconds = 0;
                timer.stop();
                return;
            }
        }

        /*
         * int delay = 5000; // number of milliseconds to sleep
         * long start = System.currentTimeMillis();
         * while(start >= System.currentTimeMillis() - delay);
         * gameState = GameState.NAME_SELECT;
         * return;
         */

        if (shootPressed) {
            player.getGun().shootBullet(player);
            shootPressed = false;
        }
        player.getGun().updateBullets();
        checkCollisions();
        moveMap();
    }

    private void checkCollisions() {
        Iterator<Zombie> zombieIterator = AllZombies.zombies.iterator();
        while (zombieIterator.hasNext()) {
            Zombie z = zombieIterator.next();
            Rectangle zBounds = z.getBounds();

            for (Bullet b : Gun.bullets) {
                Rectangle bBounds = b.getBounds();
                if (bBounds.intersects(zBounds)) {
                    Gun.bullets.remove(b);
                    z.takeDamage(player.getGun().getDamage());
                    break;
                }
            }

            // check against player, if yes, decrease player health
            if (zBounds.intersects(player.getBounds())) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastDamageTime >= z.damageTime) { // Check if 500 ms have passed
                    player.decreaseHealth(z.getDamage()); // FIXME: Change to zombie damage
                    lastDamageTime = currentTime; // Update the last damage time
                }
            }
        }

        // if bullets collide with buildings, remove bullet
        Iterator<Bullet> bulletIterator = Gun.bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet b = bulletIterator.next();
            Rectangle bBounds = b.getBounds();
            for (Rectangle r : CollisionManager.collidables) {
                if (bBounds.intersects(r)) {
                    bulletIterator.remove();
                    break;
                }
            }
        }

        Iterator<GunDrop> gunDropIterator = gunDrops.iterator();
        while (gunDropIterator.hasNext()) {
            GunDrop gd = gunDropIterator.next();
            Rectangle gdBounds = gd.getBounds();
            if (gdBounds.intersects(player.getBounds()) && dropPressed) {
                gunDropIterator.remove();
                dropPressed = false;

                if (player.getGun().getDamage() != 0)
                    player.dropGun(gd.x, gd.y);
                player.setGun(gd.getGun());

                break;
                // else if (gd.isInteractable()) {
                // gd.startSwapTimer();
                // break;
                // }
            } else if (gdBounds.intersects(player.getBounds()))
                gd.setInteractable(true);
            else
                gd.setInteractable(false);
            // else if (gd.isSwapTimerRunning()) gd.stopSwapTimer();
        }

        if (dropPressed) {
            if (player.getGun().getDamage() != 0) {
                dropPressed = false;
                player.dropGun();
                player.setGun(new Gun(0, 0, 0, 0, 0, 0, 0, 0));
            }
        }

        Iterator<HealthDrop> healthDropIterator = healthDrops.iterator();
        while (healthDropIterator.hasNext()) {
            HealthDrop hd = healthDropIterator.next();
            if (hd.getBounds().intersects(player.getBounds()) && hd.isVisible()) {
                hd.setVisible(false);
                hd.setAnimation(true);
                player.increaseHealth(hd.getHealthDropVal());
                hd.setNewLocation();
                hd.changeHealType();
                hd.startRespawnTimer();
            }
        }
    }

    GamePanel() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seconds++;
            }
        });

        setBackground(Theme.BG);
        setFocusable(true);
        requestFocusInWindow();

        Images.loadImages();
        FontLoader.loadFont();

        map = new GameMap();

        player = new Player(new Gun(15, 200, 600, 5, 5, 5, 5, -1));
        // gunDrops.add(new GunDrop(75, 500, new Gun(10, 100, 600, 0, 0, 0, 5, 1),
        // Color.YELLOW));
        // gunDrops.add(new GunDrop(50, 400, new Gun(50, 500, 600, 0, 0, 0, 5, -1),
        // Color.RED));

        prototypeBuilding1.removeWall(3);
        prototypeBuilding3.removeWallBottom(1000, 1300);
        prototypeBuilding2.removeWallRight(1000, 1300);
        prototypeBuilding4.removeWallLeft(1000, 1300);

        buildings.addBuilding(prototypeBuilding1);
        buildings.addBuilding(prototypeBuilding2);
        buildings.addBuilding(prototypeBuilding3);
        buildings.addBuilding(prototypeBuilding4);
        CollisionManager.addCollidable(player);
        AllBuildings.buildings.addAll(prototypeBuilding2.surroundingWalls);
        AllBuildings.buildings.addAll(prototypeBuilding1.surroundingWalls);
        AllBuildings.buildings.addAll(prototypeBuilding3.surroundingWalls);
        AllBuildings.buildings.addAll(prototypeBuilding4.surroundingWalls);
        zombies = new AllZombies();
        inventory = new Inventory();

        healthDrops.add(new HealthDrop(1100, 1000, 3000));
        healthDrops.add(new HealthDrop(1500, 900, 3000));
        healthDrops.add(new HealthDrop(-100, -200, 3000));
        start();
    }

    private void moveMap() {
        int playerWidth = (int) player.getWidth();
        int playerHeight = (int) player.getHeight();
        int vx = 0;
        int vy = 0;
        // Check vertical movement
        if (upPressed || downPressed || leftPressed || rightPressed) {

            if (upPressed && offsetY + MOVE_SPEED + playerHeight < Y_Bounds[1]) {
                vy = MOVE_SPEED;
            } else if (upPressed) {
                offsetY = Y_Bounds[1] - playerHeight;
            }

            if (downPressed && offsetY - MOVE_SPEED - playerHeight > Y_Bounds[0]) {
                vy = -MOVE_SPEED;
            } else if (downPressed) {
                offsetY = Y_Bounds[0] + playerHeight;
            }

            // Check horizontal movement
            if (leftPressed && offsetX + MOVE_SPEED + playerWidth < X_Bounds[1]) {
                vx = MOVE_SPEED;
            } else if (leftPressed) {
                offsetX = X_Bounds[1] - playerWidth;
            }

            if (rightPressed && offsetX - MOVE_SPEED - playerWidth > X_Bounds[0]) {
                vx = -MOVE_SPEED;
            } else if (rightPressed) {
                offsetX = X_Bounds[0] + playerWidth;
            }

            // Normalize diagonal movement
            if ((upPressed || downPressed) && (leftPressed || rightPressed)) {
                vx /= 1.25;
                vy /= 1.25;
            }

            // System.out.println("Vx: " + vx + " Vy: " + vy);

            // decide if collision happens
            Rectangle newPosition = new Rectangle(player.x - vx, player.y - vy, playerWidth, playerHeight);
            ArrayList<Rectangle> n = CollisionManager.collidables;
            n.remove(player);

            // System.out.println("New position: X: " + newPosition.x + " Y: " +
            // newPosition.y + " W: " + newPosition.width
            // + " H: " + newPosition.height);
            if (!CollisionManager.isColliding(newPosition, n)) {
                offsetX += vx;
                offsetY += vy;
                player.x -= vx;
                player.y -= vy;
            }
        }
    }

    /**
     * Paints the game panel
     * 
     * @param g Graphics objects
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        oldTransformation = g2d.getTransform();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // menu screen
        if (gameState == GameState.MAIN_MENU) {
            menu.drawMenu(g2d);
            menu.checkPlay();
            menu.checkScores();
            menu.checkCredits();
            return;
        }

        // Instructions
        if (gameState == GameState.INSTRUCTIONS) {
            instruct.drawInstructions(g2d);
            return;
        }

        if (gameState == GameState.GAMEOVER) {
            g2d.setColor(Color.RED);
            g2d.setFont(font100);
            g2d.drawString("YOU DIED!", 360, 300);
            g2d.setFont(font50);
            g2d.drawString("Game Over", 475, 500);
            return;
        }

        // prompt name
        if (gameState == GameState.NAME_SELECT) {
            name.drawKeyboard(g2d);
            name.drawName(g2d);
            name.drawInstructions(g2d);
            name.drawRejected(g2d);
            return;
        }

        // Credits
        if (gameState == GameState.CREDITS) {
            credits.drawCredits(g2d);
            return;
        }

        // shop
        if (gameState == GameState.SHOP) {
            shop.drawShop(g2d);
            shop.drawOptions(g2d);
            shop.checkShop();
            return;
        }

        // scores
        if (gameState == GameState.SCORES) {
            scores.drawScores(g2d);
            return;
        }

        // Otherwise, draw the game

        map.draw(g2d, getWidth(), getHeight());

        g2d.setTransform(oldTransformation);
        // macbook
        // g2d.setColor(Color.BLACK);
        // g2d.fillRect(KeyHandler.proximity.x+GamePanel.offsetX, KeyHandler.proximity.y
        // + GamePanel.offsetY, KeyHandler.proximity.width,
        // KeyHandler.proximity.height);

        coins.draw(g2d, player);
        buildings.draw(g2d);

        if (player.intersects(shopEntity)) {
            g2d.setColor(Color.WHITE);
            g2d.drawString("Open Shop", shopEntity.x, shopEntity.y);
        }

        zombies.draw(g2d, player);

        for (GunDrop gd : gunDrops) {
            gd.draw(g2d, player);
        }

        player.getGun().draw(g2d, player);

        for (HealthDrop drop : healthDrops) {
            drop.draw(g2d);
        }

        // bottom right corner, bullet positions
        player.draw(g2d);

        // Score Displayed Top-Right
        // g2d.drawString(String.format("Time Survived: %.2f", elapsedTime), screenWidth
        // - 300, screenHeight - 850);

        // Score Displayed Bottom-Left
        g2d.drawString(String.format("Time Survived: %.2f", elapsedTime), 200, screenHeight - 100);

        // DEBUG drawings
        // g2d.setColor(Color.RED);
        // make solid lines 3 pixels wide40,
        // g2d.setStroke(new BasicStroke(3));
        // draw horizontal line in middle of screen
        // g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        // // draw vertical line in middle of screen
        // g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        // draw dot at offset x,y
        // g2d.fillOval(offsetX, offsetY, 10, 10);

        g2d.setFont(font30);
        if (getPlayer().getBounds().intersects(KeyHandler.proximity)) {
            g2d.drawString("PRESS [P] to Open Shop", player.x + GamePanel.offsetX - 150,
                    player.y + GamePanel.offsetY - 20);
        }

        player.bar.draw(g2d);
        inventory.draw(g2d, this, player.getGun());
    }
}
