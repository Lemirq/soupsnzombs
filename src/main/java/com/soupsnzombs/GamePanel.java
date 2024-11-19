package com.soupsnzombs;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.*;

public class GamePanel extends JPanel {

    public static int offsetX = 0; // Offset for the grid's X position
    public static int offsetY = 0; // Offset for the grid's Y position
    public static boolean gameRunning = false;
    private int MOVE_SPEED = 50000; // Speed of movement
    private final int GRID_SIZE = 50; // Size of each grid cell
    private final int[] X_Bounds = { -2000, 2000 };
    private final int[] Y_Bounds = { -700, 700 };
    public static AffineTransform oldTransformation;
    public static int screenWidth = 1920;
    public static int screenHeight = 1080;

    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean shootPressed = false;
    public static int direction = 0;
    public Player player = new Player();
    public MenuGUI menu = new MenuGUI();

    public boolean idle = true;

    // public static void main(String[] args) {
    // javax.swing.SwingUtilities.invokeLater(new Runnable() {
    // public void run() {
    // new GamePanel();
    // }
    // });
    // }

    GamePanel() {
        // setup timer
        Images.loadImages();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) {
                    upPressed = true;
                    System.out.println("up");
                    direction = 0;
                } else if (key == KeyEvent.VK_S) {
                    downPressed = true;
                    direction = 2;
                } else if (key == KeyEvent.VK_A) {
                    leftPressed = true;
                    direction = -1;
                } else if (key == KeyEvent.VK_D) {
                    rightPressed = true;
                    direction = 1;
                } else if (key == KeyEvent.VK_SPACE) {
                    shootPressed = true;
                } else if (key == KeyEvent.VK_SHIFT) {
                    MOVE_SPEED = 30;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) {
                    upPressed = false;
                } else if (key == KeyEvent.VK_S) {
                    downPressed = false;
                } else if (key == KeyEvent.VK_A) {
                    leftPressed = false;
                } else if (key == KeyEvent.VK_D) {
                    rightPressed = false;
                } else if (key == KeyEvent.VK_E) {
                    shootPressed = false;
                } else if (key == KeyEvent.VK_SHIFT) {
                    MOVE_SPEED = 6;
                }
            }
        });

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
        if (upPressed && offsetY < Y_Bounds[1]) {
            offsetY += MOVE_SPEED;
        }
        if (downPressed && offsetY > Y_Bounds[0]) {
            offsetY -= MOVE_SPEED;
        }
        if (leftPressed && offsetX < X_Bounds[1]) {
            offsetX += MOVE_SPEED;
        }
        if (rightPressed && offsetX > X_Bounds[0]) {
            offsetX -= MOVE_SPEED;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (!gameRunning) {
            menu.drawMenu(g2d);
            return;
        }
        // draw underlying grid
        // Set the stroke width for the grid lines
        g2d.setStroke(new BasicStroke(4)); // Change the value to make the lines thicker or thinner

        // draw bg
        g2d.setColor(Theme.BG);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        g2d.setColor(Theme.GRID);
        for (int x = offsetX % GRID_SIZE; x < getWidth(); x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = offsetY % GRID_SIZE; y < getHeight(); y += GRID_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        // get Graphics2D object for more advanced graphics
        // turn on antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // d make solid lines 3 pixels wide
        g2d.setStroke(new BasicStroke(3));
        // Update the color and draw some rectangles
        g2d.setPaint(Color.RED);
        g2d.drawRect(20, 40, 250, 40);
        g2d.fillRect(offsetX, offsetY, 20, 10);
        g2d.setPaint(Color.BLACK);
        g2d.drawRect(220, 140, 50, 40);
        g2d.fillRect(120, 240, 50, 40);
        player.draw(g2d);
    }

}
