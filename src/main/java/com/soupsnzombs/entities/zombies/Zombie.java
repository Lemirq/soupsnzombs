package com.soupsnzombs.entities.zombies;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Entity;
import com.soupsnzombs.entities.Player;

import java.awt.*;

public class Zombie extends Entity {
    private int movementSpeed = 10;
    private int health = 100;
    private Image zombieSprite;
    private int width = Images.player_idle.getWidth();
    private int height = Images.player_idle.getHeight();
    public int moneyDropped = 10;
    public int pointsDropped = 10;
    public boolean alive = true;

    public Zombie(int startX, int startY) {
        super(startX, startY, Images.player_idle.getWidth(), Images.player_idle.getHeight());
        this.x = startX;
        this.y = startY;
        this.movementSpeed = 10;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            //System.out.println("Zombie is dead");
            alive = false;
        }
    }

    public void ZombieMovement() {
        while (alive) {
            x += movementSpeed;
            y += movementSpeed;
        }
    }

    public void draw(Graphics2D g2d) {
        int leftEdge = GamePanel.offsetX + (GamePanel.screenWidth / 2);
        int topEdge = GamePanel.offsetY + (GamePanel.screenHeight / 2);
        if (leftEdge > x - width) {
            g2d.drawImage(zombieSprite, leftEdge - x, topEdge - y, null);
            //drawHealthBar(g2d, leftEdge - x, topEdge - y - 10); // Draw health bar above the zombie

        }
    }

    public void ChasePlayer(Player player) {
        int playerX = player.getBounds().x;
        int playerY = player.getBounds().y;

        if (x < playerX) {
            x += movementSpeed;
        } else if (x > playerX) {
            x -= movementSpeed;
        }

        if (y < playerY) {
            y += movementSpeed;
        } else if (y > playerY) {
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
