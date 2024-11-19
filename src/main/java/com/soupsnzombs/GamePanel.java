package com.soupsnzombs;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.*;

public class GamePanel extends JPanel {

    public static int offsetX = 0; // Offset for the grid's X position
    public static int offsetY = 0; // Offset for the grid's Y position
    public static boolean gameRunning = false;
    public static int MOVE_SPEED = 5; // Speed of movement
    private final int GRID_SIZE = 50; // Size of each grid cell
    // private final int[] X_Bounds = { -2000, 2000 };
    // private final int[] Y_Bounds = { -700, 700 };
    public static AffineTransform oldTransformation;
    public static int screenWidth = 1600;
    public static int screenHeight = 1200;

    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean shootPressed = false;
    public static int direction = 0;
    public Player player = new Player();
    public MenuGUI menu = new MenuGUI();

    public boolean idle = true;

    GamePanel() {
        // setup timer
        setBackground(Theme.BG);
        Images.loadImages();
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
            System.out.println("up");
            offsetY += MOVE_SPEED;
        }
        if (downPressed) {
            System.out.println("down");
            offsetY -= MOVE_SPEED;
        }
        if (leftPressed) {
            System.out.println("left");
            offsetX += MOVE_SPEED;
        }
        if (rightPressed) {
            System.out.println("right");
            offsetX -= MOVE_SPEED;
        }

        System.out.println(offsetX);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Theme.GRID);
        for (int x = offsetX % GRID_SIZE; x < getWidth(); x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = offsetY % GRID_SIZE; y < getHeight(); y += GRID_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }
        player.draw(g2d);

        // Reset the transform

        // get Graphics2D object for more advanced graphics
        // turn on antialiasing

        // d make solid lines 3 pixels wide
        g2d.setStroke(new BasicStroke(3));
        // Update the color and draw some rectangles
        // g2d.setPaint(Color.RED);
        // g2d.drawRect(20, 40, 250, 40);
        // g2d.fillRect(offsetX, offsetY, 20, 10);
        // g2d.setPaint(Color.BLACK);
        // g2d.drawRect(220, 140, 50, 40);
        // g2d.fillRect(120, 240, 50, 40);
        player.draw(g2d);
    }

}
