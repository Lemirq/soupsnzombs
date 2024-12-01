package com.soupsnzombs.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.soupsnzombs.GamePanel.PlayerDir;

public class Bullet extends Entity {
    private int startX, startY;
    private PlayerDir direction;
    private int maxDistance; // Maximum distance the bullet can travel
    private int cx, cy;

    public Bullet(int x, int y, int cx, int cy, PlayerDir direction, int range) {
        super(x, y, 10, 10, 0, 2);
        this.startX = x;
        this.startY = y;
        this.cx = cx;
        this.cy = cy;
        this.maxDistance = range;
        this.direction = direction;
    }

    public void update() {
        // Update bullet position based on direction
        if (direction == PlayerDir.RIGHT) {
            x += speed;
            cx -= speed;
        } else if (direction == PlayerDir.LEFT) {
            x -= speed;
            cx += speed;
        } else if (direction == PlayerDir.UP) {
            y -= speed;
            cy += speed;
        } else if (direction == PlayerDir.DOWN) {
            y += speed;
            cy -= speed;
        }
    }

    // public void showGunfire(Graphics2D g2d, int gunMouthX, int gunMouthY) {
    // // Show gunfire animation
    // // images are in Images.gunfire as an arraylist, there are 5 images
    // // f1.png to f5.png
    // // Use a timer to cycle through the images to create an animation
    // Timer timer = new Timer(100, new ActionListener() {
    // int index = 0;

    // @Override
    // public void actionPerformed(ActionEvent e) {
    // // Draw the image at the gunMouthX and gunMouthY position
    // g2d.drawImage(Images.gunfire.get(index), gunMouthX, gunMouthY, null);
    // index++;
    // if (index >= Images.gunfire.size()) {
    // ((Timer) e.getSource()).stop();
    // }
    // }
    // });
    // timer.start();
    // }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void draw(Graphics2D g2d) {
        // Update bullet position
        // Set bullet color to yellow
        g2d.setColor(Color.YELLOW);
        // Draw the bullet
        g2d.fillOval(x, y, width, height);
    }

    public Rectangle getBounds() {
        // System.out.println("Bullet x:" + cx + " y: " + cy);
        // translate the x and y so that intersection is correct
        return new Rectangle(cx, cy, width, height);
    }
}