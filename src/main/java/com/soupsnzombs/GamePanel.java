package com.soupsnzombs;

import javax.swing.*;
import com.soupsnzombs.UI.*;
import com.soupsnzombs.UI.MainMenu.*;
import com.soupsnzombs.UI.Shop.Shop;
import com.soupsnzombs.buildings.*;
import com.soupsnzombs.entities.*;
import com.soupsnzombs.entities.zombies.*;
import com.soupsnzombs.utils.*;
import com.soupsnzombs.utils.SoundManager.Sound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import static com.soupsnzombs.utils.Images.*;

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

    Timer deathScreenTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            seconds++;
        }
    });
    int seconds = 0;

    Timer nameSelectTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            timeLeft--;
        }
    });
    public static int timeLeft = 15;

    // private long time1 = 0;
    // private long time2 = 0;
    // private boolean gameOverHandled = false;

    // Movement variables
    public static int offsetX = 0; // Offset for the grid's X position
    public static int offsetY = 0; // Offset for the grid's Y position
    public static int MOVE_SPEED = 1; // Speed of movement
    public static AffineTransform oldTransformation;
    GameMap map;
    public static int screenWidth = 1920;
    public static int screenHeight = 1080;

    // Grid variables
    public static final int[] X_Bounds = { -5000, 5000 };
    public static final int[] Y_Bounds = { -2000, 2000 };
    AllBuildings buildings = new AllBuildings();
    AllCoins coins = new AllCoins();
    AllZombies zombies;
    WaveIndicator waveIndicator = new WaveIndicator();
    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean dropPressed = false;
    public static boolean shootPressed = false;
    public static PlayerDir direction = PlayerDir.UP;
    public static Player player;
    public MenuGUI menu = new MenuGUI();
    public NameSelect name = new NameSelect();
    public Shop shop = new Shop();
    public Scores scores = new Scores();
    public Instructions instruct = new Instructions();
    public Credits credits = new Credits();
    public static ArrayList<GunDrop> gunDrops = new ArrayList<>();
    public EntranceBuilding soupWarehouse = new EntranceBuilding(3000, -1200, 1200, 2200, 800, 4, 60);
    public EntranceBuilding warehouseEntrance = new EntranceBuilding(1940, -1200, 1350, 800, 450, 1, 60);
    public EntranceBuilding hut1 = new EntranceBuilding(-3500, 1020, 450, 400, 200, 2, 30);
    public EntranceBuilding hut2 = new EntranceBuilding(-2000, -800, 450, 400, 200, 4, 30);
    public EntranceBuilding hut3 = new EntranceBuilding(-600, 1600, 450, 400, 200, 3, 30);
    public ShopBuilding shopEntity = new ShopBuilding(250, 1400, 400, 200);
    public ArrayList<EntranceBuilding> entranceBuildings = new ArrayList<>();
    public Couch couch1 = new Couch(-310, 1725, 200, 200);
    public ArrayList<HealthDrop> healthDrops = new ArrayList<>();
    public Inventory inventory;

    public static long damageEffectStartTime = 0;
    private static final int DAMAGE_EFFECT_DURATION = 500; // Duration in milliseconds

    public ShopBuilding getShop() {
        return shopEntity;
    }

    public Player getPlayer() {
        // TODO: fix this
        return GamePanel.player;
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
            // TODO: make money not 90
            Player.score = 0; // Reset player score
            player.x = -3120;
            player.y = 1540;
            offsetX = -(player.x - (GamePanel.screenWidth / 2 - player.width / 2));
            offsetY = -(player.y - (GamePanel.screenHeight / 2 - player.height / 2)); // Reset player position
            zombies.clear(); // Removes all zombies
            coins.clear(); // Removes all coins
            NameSelect.name = new StringBuilder(""); // Clears leaderboard name input stream
            AllZombies.waveNumber = 0; // Set zombie wave to 0
        }

        if (gameState == GameState.GAMEOVER) {
            SoundManager.stopAllSounds();
            deathScreenTimer.start();
            if (seconds == 2) {
                gameState = GameState.NAME_SELECT;
                deathScreenTimer.stop();
                seconds = 0;
                return;
            }
        }

        if (gameState == GameState.NAME_SELECT) {
            nameSelectTimer.start();
            if (timeLeft == 0) {
                gameState = GameState.MAIN_MENU;
                nameSelectTimer.stop();
                timeLeft = 15;
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
            // play sound
            SoundManager.playSound(Sound.GUNFIRE, false);
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
                player.setGun(new Gun(0, 0, 0, 0, 0, -5, null));
            }
        }

        Iterator<HealthDrop> healthDropIterator = healthDrops.iterator();
        while (healthDropIterator.hasNext()) {
            HealthDrop hd = healthDropIterator.next();
            if (hd.getBounds().intersects(player.getBounds()) && hd.isVisible()) {
                // hd.setNewLocation();
                // hd.changeHealType();
                hd.setVisible(false);
                hd.setAnimation(true);
                player.increaseHealth(hd.getHealthDropVal());
                SoundManager.playSound(Sound.COIN_PICKUP, false);
                hd.startRespawnTimer();
            }
        }
    }

    GamePanel() {
        setBackground(Theme.BG);
        setFocusable(true);
        requestFocusInWindow();

        Images.loadImages();
        FontLoader.loadFont();

        map = new GameMap();

        player = new Player(new Gun(0, 0, 0, 0, 0, -5, null));
        // gunDrops.add(new GunDrop(75, 500, new Gun(10, 100, 600, 0, 0, 0, 5, 1),
        // Color.YELLOW));
        // gunDrops.add(new GunDrop(50, 400, new Gun(50, 500, 600, 0, 0, 0, 5, -1),
        // Color.RED));
        gunDrops.add(new GunDrop(-3450, 1025, new Gun(15, 200, 200, 5, 5, -1, pistolImage)));
        gunDrops.add(new GunDrop(-1800, -760, new Gun(25, 200, 300, 5, 5, -1, pistolImage)));

        gunDrops.add(new GunDrop(4935, 765, new Gun(15, 200, 300, 5, 5, 0, semiAutoImage)));
        warehouseEntrance.removeWall(2);

        soupWarehouse.removeWallLeft(-1200, -400);
        entranceBuildings.add(soupWarehouse);
        entranceBuildings.add(warehouseEntrance);
        entranceBuildings.add(hut1);
        entranceBuildings.add(hut2);
        entranceBuildings.add(hut3);

        for (EntranceBuilding eb : entranceBuildings) {
            buildings.addBuilding(eb);
            AllBuildings.buildings.addAll(eb.surroundingWalls);
        }

        CollisionManager.addCollidable(player);
        SoundManager.init();
        zombies = new AllZombies();
        inventory = new Inventory();

        healthDrops.add(new HealthDrop(-550, 1660, 30000, 50)); // health drop in the hut near the shop

        // health drops in the warehouse
        healthDrops.add(new HealthDrop(3850, 20, 10000));
        healthDrops.add(new HealthDrop(3600, 920, 5000));
        healthDrops.add(new HealthDrop(3880, -400, 10000, 20));
        healthDrops.add(new HealthDrop(3120, 600, 10000, 20));

        healthDrops.add(new HealthDrop(2200, -580, 10000));

        // row of soup 1
        healthDrops.add(new HealthDrop(2800, -1020, 60000, 50));
        healthDrops.add(new HealthDrop(2900, -1020, 60000, 50));
        healthDrops.add(new HealthDrop(3000, -1020, 60000, 50));
        healthDrops.add(new HealthDrop(3100, -1020, 60000, 50));

        // row of soup 2
        healthDrops.add(new HealthDrop(2800, -620, 60000, 50));
        healthDrops.add(new HealthDrop(2900, -620, 60000, 50));
        healthDrops.add(new HealthDrop(3000, -620, 60000, 50));
        healthDrops.add(new HealthDrop(3100, -620, 60000, 50));
        start();
    }

    private void moveMap() {
        int playerWidth = (int) player.getWidth();
        int playerHeight = (int) player.getHeight();
        int vx = 0;
        int vy = 0;

        if (upPressed || downPressed || leftPressed || rightPressed) {
            // Calculate desired velocities
            if (upPressed) {
                if (offsetY + MOVE_SPEED <= Y_Bounds[1] - playerHeight) {
                    vy = MOVE_SPEED;
                }
            }
            if (downPressed) {
                if (offsetY - MOVE_SPEED >= Y_Bounds[0]) {
                    vy = -MOVE_SPEED;
                }
            }
            if (leftPressed) {
                if (offsetX + MOVE_SPEED <= X_Bounds[1] - playerWidth) {
                    vx = MOVE_SPEED;
                }
            }
            if (rightPressed) {
                if (offsetX - MOVE_SPEED >= X_Bounds[0]) {
                    vx = -MOVE_SPEED;
                }
            }

            // Normalize diagonal movement
            if ((upPressed || downPressed) && (leftPressed || rightPressed)) {
                vx /= 1.25;
                vy /= 1.25;
            }

            // Check collisions
            Rectangle newPositionX = new Rectangle(player.x - vx, player.y, playerWidth, playerHeight);
            Rectangle newPositionY = new Rectangle(player.x, player.y - vy, playerWidth, playerHeight);
            ArrayList<Rectangle> n = CollisionManager.collidables;
            n.remove(player);

            // Apply movement if no collision
            if (!CollisionManager.isColliding(newPositionX, n)) {
                offsetX += vx;
                player.x -= vx;
            }
            if (!CollisionManager.isColliding(newPositionY, n)) {
                offsetY += vy;
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
            SoundManager.stopAllSounds();
            g2d.setColor(Color.RED);
            g2d.setFont(font100);

            // Center "YOU DIED!" text
            String youDiedText = "YOU DIED!";
            FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
            int textWidth = metrics.stringWidth(youDiedText);
            int xPosition = (GamePanel.screenWidth - textWidth) / 2;
            g2d.drawString(youDiedText, xPosition, 300);

            g2d.setFont(font50);

            // Center "Game Over" text
            String gameOverText = "Game Over";
            metrics = g2d.getFontMetrics(g2d.getFont());
            textWidth = metrics.stringWidth(gameOverText);
            xPosition = (GamePanel.screenWidth - textWidth) / 2;
            g2d.drawString(gameOverText, xPosition, 500);
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
            shop.getCost();
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

        // draw mud puddles
        g2d.drawImage(mudPuddle, -4194 + offsetX, -1192 + offsetY, 250, 250, null);
        g2d.drawImage(mudPuddle, -3212 + offsetX, 1595 + offsetY, 300, 300, null);
        g2d.drawImage(mudPuddle, -4194 + offsetX, -1192 + offsetY, 400, 400, null);
        g2d.drawImage(mudPuddle, -4194 + offsetX, -1192 + offsetY, 200, 200, null);
        g2d.drawImage(mudPuddle, -4000 + offsetX, -1400 + offsetY, 500, 500, null);
        g2d.drawImage(mudPuddle, -3500 + offsetX, 500 + offsetY, 250, 250, null);
        g2d.drawImage(mudPuddle, -3000 + offsetX, -800 + offsetY, 450, 450, null);
        g2d.drawImage(mudPuddle, -2500 + offsetX, 1200 + offsetY, 300, 300, null);
        g2d.drawImage(mudPuddle, -2000 + offsetX, -300 + offsetY, 350, 350, null);
        g2d.drawImage(mudPuddle, -1500 + offsetX, 900 + offsetY, 400, 400, null);
        g2d.drawImage(mudPuddle, -1000 + offsetX, -1000 + offsetY, 450, 450, null);
        // g2d.drawImage(mudPuddle, -500 + offsetX, 1500 + offsetY, 500, 500, null);
        g2d.drawImage(mudPuddle, 0 + offsetX, -500 + offsetY, 300, 300, null);
        g2d.drawImage(mudPuddle, 500 + offsetX, 2000 + offsetY, 200, 200, null);
        g2d.drawImage(mudPuddle, 1000 + offsetX, -200 + offsetY, 350, 350, null);
        g2d.drawImage(mudPuddle, 1500 + offsetX, 1700 + offsetY, 400, 400, null);
        // g2d.drawImage(mudPuddle, 2000 + offsetX, -700 + offsetY, 450, 450, null);
        g2d.drawImage(mudPuddle, 2500 + offsetX, 1300 + offsetY, 500, 500, null);
        g2d.drawImage(mudPuddle, 3000 + offsetX, -1200 + offsetY, 300, 300, null);
        g2d.drawImage(mudPuddle, 3500 + offsetX, 1100 + offsetY, 250, 250, null);
        // g2d.drawImage(mudPuddle, 4000 + offsetX, -400 + offsetY, 400, 400, null);
        g2d.drawImage(mudPuddle, 4500 + offsetX, 1800 + offsetY, 450, 450, null);
        g2d.drawImage(mudPuddle, 5000 + offsetX, -900 + offsetY, 500, 500, null);
        g2d.drawImage(mudPuddle, 5500 + offsetX, 1600 + offsetY, 300, 300, null);
        buildings.draw(g2d);

        // draw zombies
        zombies.draw(g2d, player);

        // draw trash piles
        g2d.drawImage(largeTrashPile, 1974 + offsetX, -214 + offsetY, 500, 500, null);
        g2d.drawImage(largeTrashPile, 2300 + offsetX, -214 + offsetY, 500, 500, null);
        g2d.drawImage(largeTrashPile, 2300 + offsetX, 0 + offsetY, 500, 500, null);

        g2d.drawImage(largeTrashPile, 4600 + offsetX, -1367 + offsetY, 500, 500, null);
        g2d.drawImage(largeTrashPile, 4600 + offsetX, -362 + offsetY, 500, 500, null);

        g2d.drawImage(largeTrashPile, 3800 + offsetX, 1480 + offsetY, 500, 500, null);
        g2d.drawImage(largeTrashPile, 3800 + offsetX, 2000 + offsetY, 500, 500, null);
        g2d.drawImage(largeTrashPile, 4080 + offsetX, 1820 + offsetY, 500, 500, null);

        g2d.drawImage(smallTrashPile, -3500 + offsetX, 1300 + offsetY, 150, 150, null);
        // g2d.drawImage(smallTrashPile, -3940 + offsetX, 1000 + offsetY, 150, 150,
        // null);
        // g2d.drawImage(smallTrashPile, -4145 + offsetX, 1114 + offsetY, 150, 150,
        // null);

        g2d.drawImage(smallTrashPile, 3487 + offsetX, -900 + offsetY, 150, 150, null);
        g2d.drawImage(smallTrashPile, 3800 + offsetX, -900 + offsetY, 150, 150, null);
        g2d.drawImage(smallTrashPile, 3287 + offsetX, 120 + offsetY, 150, 150, null);
        g2d.drawImage(smallTrashPile, 3700 + offsetX, 580 + offsetY, 150, 150, null);

        // g2d.drawImage(smallTrashPile, -326 + offsetX, 1845 + offsetY, 150, 150,
        // null);

        // draw couch
        couch1.draw(g2d);

        // draw beda
        g2d.drawImage(bed, -3500 + offsetX, 1100 + offsetY, 250, 250, null);
        g2d.drawImage(bed, -2000 + offsetX, -700 + offsetY, 250, 250, null);
        g2d.drawImage(bed, -600 + offsetX, 1700 + offsetY, 250, 250, null);

        // draw coins
        coins.draw(g2d, player);

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

        buildings.drawTrees(g2d);
        // g2d.setFont(font30);

        // UI stuff
        g2d.setColor(Color.WHITE);
        g2d.setFont(font30);
        g2d.drawString(String.format("Time Survived: %.2f", elapsedTime), 80, screenHeight - 100);
        if (getPlayer().getBounds().intersects(KeyHandler.proximity)) {
            g2d.drawString("[B] Shop", player.x + GamePanel.offsetX - 46,
                    player.y + GamePanel.offsetY - 20);
        }
        // rectangle in the bottom middle of the screen that houses both the coin and
        // the score
        int rectX = (GamePanel.screenWidth - 150) / 2;
        int rectY = GamePanel.screenHeight - 150;
        g2d.drawRect(rectX, rectY, 150, 50);
        g2d.drawImage(Images.coin, rectX + 10, rectY, 50, 50, null);
        g2d.drawString("" + Player.money, rectX + 80, rectY + 30);

        // draw escape key instructions
        g2d.setColor(Color.orange);
        g2d.setFont(font30);
        g2d.drawString("Press [Z] to return to main menu", 80, screenHeight - 50);

        player.bar.draw(g2d);
        inventory.draw(g2d, player.getGun());
        waveIndicator.draw(g2d, player);

        long currentTime = System.currentTimeMillis();
        if (currentTime - damageEffectStartTime < DAMAGE_EFFECT_DURATION) {
            g2d.setColor(new Color(255, 0, 0, 40)); // Red color with transparency
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        // if the player is close to the bounds draw big text saying: THERE IS NO ESCAPE
        if (offsetX < -4900 || offsetX > 4900 || offsetY < -1980 || offsetY > 1980) {
            g2d.setColor(Color.RED);
            g2d.setFont(font60);
            g2d.drawString("THERE IS NO ESCAPE", screenWidth / 2 - 300, screenHeight / 2);
        }
    }
}
