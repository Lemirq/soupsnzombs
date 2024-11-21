package com.soupsnzombs;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.*;

public class GamePanel extends JPanel {
    enum GameState {
        MAIN, OPTIONS, GAME, PAUSE, GAMEOVER
    }

    public static GamePanel.GameState gameState;
    public static int offsetX = 0; // Offset for the grid's X position
    public static int offsetY = 0; // Offset for the grid's Y position
    public static boolean gameRunning = true;
    public static int MOVE_SPEED = 5; // Speed of movement
    private final int GRID_SIZE = 50; // Size of each grid cell
    // private final int[] X_Bounds = { -2000, 2000 };
    // private final int[] Y_Bounds = { -700, 700 };
    public static AffineTransform oldTransformation;
    public static int screenWidth = 1200;
    public static int screenHeight = 900;

    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean shootPressed = false;
    public static int direction = 0;
    public Player player = new Player();
    public MenuGUI menu = new MenuGUI();
    Boundary boundary = new Boundary();

    public boolean idle = true;

    GamePanel() {
        // setup timer
        setBackground(Theme.BG);
        Images.loadImages();
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setFocusable(true);
        requestFocusInWindow();

        Timer timer = new Timer(17, new ActionListener() { // roughly 60 frames per second as 1000ms / 60fps =
            // 16.6666666667ms
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMap();

                // if (shootPressed) {
                // gun.shootBullet(player);
                // shootPressed = false; // Prevent continuous shooting
                // }
                // updateGame();
                repaint();
            }
        });

        timer.start();
    }

    private void moveMap() {
        if (upPressed) {
            offsetY += MOVE_SPEED;
        }
        if (downPressed) {
            offsetY -= MOVE_SPEED;
        }
        if (leftPressed) {
            offsetX += MOVE_SPEED;
        }
        if (rightPressed) {
            offsetX -= MOVE_SPEED;
        }

        System.out.println(offsetX);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        oldTransformation = g2d.getTransform();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // menu screen
        menu.drawMenu(g2d);
        menu.checkPlay();
        if (gameState == GameState.MAIN) {
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
        int leftBoundary = offsetX - centerX - centerX / 2;
        int rightBoundary = -(offsetX + centerX) - centerX / 2;
        int topBoundary = offsetY - centerY + 200;
        int bottomBoundary = -(offsetY + centerY) + 200;

        boundary.draw(g2d, leftBoundary, rightBoundary, topBoundary, bottomBoundary);
        player.draw(g2d);

        // d make solid lines 3 pixels wide
        g2d.setStroke(new BasicStroke(3));

        // draw horizontal line in middle of screen
        g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        // draw vertical line in middle of screen
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
    }

}
