package com.soupsnzombs;

import javax.swing.*;

import com.soupsnzombs.UI.MainMenu.Credits;
import com.soupsnzombs.UI.MainMenu.Instructions;
import com.soupsnzombs.UI.MainMenu.MenuGUI;
import com.soupsnzombs.UI.MainMenu.NameSelect;
import com.soupsnzombs.UI.Shop.MainShop;
import com.soupsnzombs.UI.MainMenu.Scores;
import com.soupsnzombs.buildings.AllBuildings;
import com.soupsnzombs.buildings.AllTrees;
import com.soupsnzombs.entities.Bullet;
import com.soupsnzombs.entities.Gun;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.entities.zombies.AllZombies;
import com.soupsnzombs.entities.zombies.Zombie;
import com.soupsnzombs.utils.CollisionManager;
import com.soupsnzombs.utils.FontLoader;
import com.soupsnzombs.utils.Images;
import com.soupsnzombs.utils.Theme;

import java.awt.geom.AffineTransform;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements Runnable {

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
    private double elapsedTime = 0;
    private long lastDamageTime = 0; // for player invincibility
    private Thread gameThread;
    private long lastTime;
    private final int FPS = 120;
    private final double TIME_PER_TICK = 1000000000 / FPS;

    // Movement variables
    public static int offsetX = 0; // Offset for the grid's X position
    public static int offsetY = 0; // Offset for the grid's Y position
    public static int MOVE_SPEED = 1; // Speed of movement
    public static AffineTransform oldTransformation;
    GameMap map = new GameMap();
    public static int screenWidth = 1200;
    public static int screenHeight = 900;

    // Grid variables
    public static final int[] X_Bounds = { -5000, 5000 };
    public static final int[] Y_Bounds = { -2000, 2000 };
    AllBuildings buildings = new AllBuildings();
    AllTrees trees = new AllTrees();
    AllZombies zombies;
    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean shootPressed = false;
    public static PlayerDir direction = PlayerDir.UP;
    public Player player;
    public MenuGUI menu = new MenuGUI();
    public NameSelect name = new NameSelect();
    public MainShop shop = new MainShop();
    public Scores scores = new Scores();
    public Instructions instruct = new Instructions();
    public Credits credits = new Credits();

    public Gun gun = new Gun(5, 5, 600, 5, 5, 5, 5);

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
                elapsedTime += (now - lastTime) / 1_000_000_000.0; // Update elapsed time
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
        if (!player.alive && gameState != GameState.GAMEOVER) {
            gameState = GameState.NAME_SELECT;
            return;
        }
        if (shootPressed) {
            gun.shootBullet(player);
            shootPressed = false;
        }
        gun.updateBullets();
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
                    z.takeDamage(10);
                    break;
                }
            }

            // check against player, if yes, decrease player health
            if (zBounds.intersects(player.getBounds())) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastDamageTime >= z.damageTime) { // Check if 500 ms have passed
                    player.decreaseHealth(10);
                    lastDamageTime = currentTime; // Update the last damage time
                }
            }
        }

        // if bullets collide with buildings, remove bullet
        Iterator<Bullet> bulletIterator = Gun.bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet b = bulletIterator.next();
            Rectangle bBounds = b.getBounds();
            for (Rectangle r : buildings.buildings) {
                if (bBounds.intersects(r)) {
                    bulletIterator.remove();
                    break;
                }
            }
        }

    }

    GamePanel() {
        // setup timer
        setBackground(Theme.BG);
        setFocusable(true);
        requestFocusInWindow();

        Images.loadImages();
        FontLoader.loadFont();
        player = new Player();
        CollisionManager.addCollidable(player);
        zombies = new AllZombies();
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
     * @param g Graphics objecta
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

        // Instrucitons
        if (gameState == GameState.INSTRUCTIONS) {
            instruct.drawInstructions(g2d);
            return;
        }

        if (gameState == GameState.GAMEOVER) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.PLAIN, 40));
            g2d.drawString("Game Over", 500, 500);
            return;
        }

        // prompt name
        if (gameState == GameState.NAME_SELECT) {
            name.drawKeyboard(g2d);
            name.drawName(g2d);
            name.drawInstructions(g2d);
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

        player.bar.draw(g2d);
        buildings.draw(g2d);
        trees.draw(g2d);
        zombies.draw(g2d, player);
        player.draw(g2d);

        gun.draw(g2d, player);

        // bottom right corner, bullet positions
        g2d.setColor(Color.RED);

        // DEBUG drawings

        // make solid lines 3 pixels wide
        // g2d.setStroke(new BasicStroke(3));
        // draw horizontal line in middle of screen
        // g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        // // draw vertical line in middle of screen
        // g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        // draw dot at offset x,y
        // g2d.fillOval(offsetX, offsetY, 10, 10);

    }
}
