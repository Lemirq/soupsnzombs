package com.soupsnzombs;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.*;

public class GamePanel extends JPanel {

    public static int offsetX = 0; // Offset for the grid's X position
    public static int offsetY = 0; // Offset for the grid's Y position
    private int MOVE_SPEED = 6; // Speed of movement
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

        // get Graphics2D object for more advanced graphics
        Graphics2D g2 = (Graphics2D) g;
        // turn on antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // make solid lines 3 pixels wide
        g2.setStroke(new BasicStroke(3));
        // Update the color and draw some rectangles
        g2.setPaint(Color.BLUE);
        g2.drawRect(20, 40, 250, 40);
        g2.fillRect(0, 0, 20, 10);
        g2.setPaint(Color.YELLOW);
        g2.drawRect(220, 140, 50, 40);
        g2.fillRect(120, 240, 50, 40);
    }

}
