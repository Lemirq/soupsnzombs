package com.soupsnzombs.entities.zombies;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Entity;
import com.soupsnzombs.entities.GameObject;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.utils.CollisionManager;
import com.soupsnzombs.utils.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Zombie extends Entity implements GameObject {
    // private static int direction;
    private int health = 100;
    private BufferedImage sprite;
    public int moneyDropped = 10;
    public int pointsDropped = 10;
    public boolean alive = true;

    public Zombie(int startX, int startY) {
        super(startX, startY, 0, 0, 100, 1);
        this.x = startX + GamePanel.offsetX;
        this.y = startY + GamePanel.offsetY;

        this.sprite = Images.spriteImages.get("zoimbie1_stand.png");
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // System.out.println("Zombie is dead");
            alive = false;
        }
    }

    public void draw(Graphics2D g2d) {
        // Calculate the screen position based on the world position and camera offsets
        int screenX = x + GamePanel.offsetX;
        int screenY = y + GamePanel.offsetY;

        // Draw the zombie at the calculated screen position
        g2d.drawImage(sprite, screenX, screenY, null);

        // Draw health bar above the zombie
        drawHealthBar(g2d, screenX, screenY - 10);
        // For debugging: draw the zombie's rectangle
        if (GamePanel.debugging) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(x, y, width, height);
        }
    }

    private void drawHealthBar(Graphics2D g2d, int x, int y) {
        int barWidth = 50;
        int barHeight = 10;
        int healthBarWidth = (int) ((health / 100.0) * barWidth);
        g2d.setColor(Color.RED);
        g2d.fillRoundRect(x, y, barWidth, barHeight, 10, 10);

        g2d.setColor(Color.GREEN);
        g2d.fillRoundRect(x, y, healthBarWidth, barHeight, 10, 10);
    }

    public boolean isColliding(Rectangle rect) {
        return intersects(rect);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }

    public void chasePlayer(Player p) {
        int vy = 0;
        int vx = 0;

        // make zombie walk on hypotenuse towards player
        double movementSpeed = 1;

        if (x < p.x) {
            vx += movementSpeed;
        } else if (x > p.x) {
            vx -= movementSpeed;
        }

        if (y < p.y) {
            vy += movementSpeed;
        } else if (y > p.y) {
            vy -= movementSpeed;
        }

        Rectangle rect = new Rectangle(x + vx, y + vy, width, height);
        ArrayList<Rectangle> newCollisions = CollisionManager.collidables;
        // find the current zombie in list of collisions
        newCollisions.remove(this);

        if (!CollisionManager.isColliding(rect, newCollisions)) {
            x += vx;
            y += vy;
        }

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Getters for the zombie's health
    public int getHealth() {
        return health;
    }

}
