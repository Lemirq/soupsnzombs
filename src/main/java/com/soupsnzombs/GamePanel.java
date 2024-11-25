package com.soupsnzombs;

import javax.swing.*;

import com.soupsnzombs.UI.MenuGUI;
import com.soupsnzombs.buildings.AllBuildings;
import com.soupsnzombs.entities.Boundary;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.utils.CRectangle;
import com.soupsnzombs.utils.CollisionManager;
import com.soupsnzombs.utils.Images;
import com.soupsnzombs.utils.Theme;

import java.awt.geom.AffineTransform;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public enum GameState {
        MAIN_MENU, OPTIONS, GAME, PAUSE, GAMEOVER
    }

    public static GameState gameState = GameState.MAIN_MENU;

    // Game loop variables
    private boolean running = false;
    private Thread gameThread;
    private long lastTime;
    private final int FPS = 120;
    private final double TIME_PER_TICK = 1000000000 / FPS;

    // Movement variables
    public static int offsetX = 0; // Offset for the grid's X position
    public static int offsetY = 0; // Offset for the grid's Y position
    public static int MOVE_SPEED = 1; // Speed of movement
    public static AffineTransform oldTransformation;
    public static int screenWidth = 1200;
    public static int screenHeight = 900;

    // Grid variables
    private final int GRID_SIZE = 50; // Size of each grid cell
    private final int[] X_Bounds = { -5000, 5000 };
    private final int[] Y_Bounds = { -2000, 2000 };
    CollisionManager collisionManager = new CollisionManager();
    AllBuildings buildings = new AllBuildings(collisionManager);
    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean shootPressed = false;
    public static int direction = 0;
    public Player player = new Player();
    public MenuGUI menu = new MenuGUI();
    Boundary boundary = new Boundary();

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
            lastTime = now;

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

        moveMap();
    }

    GamePanel() {
        // setup timer
        setBackground(Theme.BG);
        setFocusable(true);
        requestFocusInWindow();

        // Load images in background thread
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Images.loadImages();
                return null;
            }

            @Override
            protected void done() {
                // Any code that needs to run after images are loaded can go here
                start();
            }
        }.execute();

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

            System.out.println("Vx: " + vx + " Vy: " + vy);

            // decide if collision happens
            CRectangle newPosition = new CRectangle(player.x - vx, player.y - vy, playerWidth, playerHeight);

            System.out.println("New position: X: " + newPosition.x + " Y: " + newPosition.y + " W: " + newPosition.width
                    + " H: " + newPosition.height);
            if (collisionManager.isColliding(newPosition)) {
                System.out.println("Collision detected");
            } else {
                offsetX += vx;
                offsetY += vy;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        oldTransformation = g2d.getTransform();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // menu screen
        if (gameState == GameState.MAIN_MENU) {
            menu.drawMenu(g2d);
            menu.checkPlay();
            return;
        }

        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        // draw grid
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Theme.GRID);
        for (int x = offsetX % GRID_SIZE; x < getWidth(); x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = offsetY % GRID_SIZE; y < getHeight(); y += GRID_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        g2d.setTransform(oldTransformation);
        // macbook
        int leftBoundary = offsetX - centerX - X_Bounds[1] + screenWidth;
        int rightBoundary = -(offsetX + centerX) + X_Bounds[0] + screenWidth;
        int topBoundary = offsetY - centerY - Y_Bounds[1] + screenHeight;
        int bottomBoundary = -(offsetY + centerY) + Y_Bounds[0] + screenHeight;

        boundary.draw(g2d, leftBoundary, rightBoundary, topBoundary, bottomBoundary);
        buildings.draw(g2d);
        player.draw(g2d);

        // DEBUG drawings

        // make solid lines 3 pixels wide
        // g2d.setStroke(new BasicStroke(3));
        // draw horizontal line in middle of screen
        // g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        // // draw vertical line in middle of screen
        // g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        // draw dot at offset x,y
        // g2d.fillOval(offsetX, offsetY, 10, 10);

        // bottom left corner, draw stringgs for left,right,top,bottom boundaries
        g2d.drawString("Left: " + leftBoundary, 10, getHeight() - 10);
        g2d.drawString("Right: " + rightBoundary, 10, getHeight() - 30);
        g2d.drawString("Top: " + topBoundary, 10, getHeight() - 50);
        g2d.drawString("Bottom: " + bottomBoundary, 10, getHeight() - 70);
    }

}
