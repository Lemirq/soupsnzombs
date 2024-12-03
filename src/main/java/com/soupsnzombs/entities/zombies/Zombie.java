package com.soupsnzombs.entities.zombies;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Entity;
import com.soupsnzombs.entities.GameObject;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.utils.CollisionManager;
import com.soupsnzombs.utils.Images;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Zombie extends Entity implements GameObject {
    // private static int direction;
    private int health = 100;
    private BufferedImage sprite;
    public int moneyDropped = 10;
    public int pointsDropped = 10;
    public int damageTime = 500;

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

    public void draw(Graphics2D g2d, Player p) {
        // Calculate the screen position based on the world position and camera offsets
        int screenX = x + GamePanel.offsetX;
        int screenY = y + GamePanel.offsetY;

        // Calculate the angle of the velocity vector
        double angle = Math.atan2(p.y - y, p.x - x);

        // Save the original transform

        // Apply the rotation transformation
        g2d.rotate(angle, screenX + width / 2, screenY + height / 2);

        // Draw the zombie at the calculated screen position
        g2d.drawImage(sprite, screenX, screenY, null);

        // Reset the transformation
        g2d.setTransform(GamePanel.oldTransformation);

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
        // int vy = 0;
        // int vx = 0;
        double deltaX = p.x - this.x;
        double deltaY = p.y - this.y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Normalize the direction vector
        double directionX = deltaX / distance;
        double directionY = deltaY / distance;
        // Calculate the velocity components
        double vx = directionX * speed;
        double vy = directionY * speed;

        // make zombie walk on hypotenuse towards player

        if (x < p.x) {
            vx += speed;
        } else if (x > p.x) {
            vx -= speed;
        }

        if (y < p.y) {
            vy += speed;
        } else if (y > p.y) {
            vy -= speed;
        }

        Rectangle rect = new Rectangle(x + (int) vx, y + (int) vy, width, height);
        ArrayList<Rectangle> newCollisions = CollisionManager.collidables;
        // find the current zombie in list of collisions
        newCollisions.remove(this);

        if (!CollisionManager.isColliding(rect, newCollisions)) {
            x += vx;
            y += vy;
        }

    }

    public void draw(Graphics2D g2d) {
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
