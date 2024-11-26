package com.soupsnzombs.entities.zombies;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Entity;
import com.soupsnzombs.utils.Images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends Entity {
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
        int leftEdge = GamePanel.offsetX + (GamePanel.screenWidth / 2);
        int topEdge = GamePanel.offsetY + (GamePanel.screenHeight / 2);
        if (leftEdge > x - width) {
            g2d.drawImage(sprite, leftEdge - x, topEdge - y, null);
            // drawHealthBar(g2d, leftEdge - x, topEdge - y - 10); // Draw health bar above
            // the zombie

        }
    }

    public void chasePlayer(Rectangle player) {
        int playerX = player.x - GamePanel.offsetX;
        int playerY = player.y - GamePanel.offsetY;

        // System.out.println("Player X: " + playerX + " Player Y: " + playerY);

        if (x < GamePanel.offsetX) {
            x += movementSpeed;
        } else if (x > GamePanel.offsetX) {
            x -= movementSpeed;
        }

        if (y < GamePanel.offsetY) {
            y += movementSpeed;
        } else if (y > GamePanel.offsetY) {
            y -= movementSpeed;
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

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
