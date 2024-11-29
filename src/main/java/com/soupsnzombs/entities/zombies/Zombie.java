package com.soupsnzombs.entities.zombies;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Entity;
import com.soupsnzombs.entities.GameObject;
import com.soupsnzombs.utils.CollisionManager;
import com.soupsnzombs.utils.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Zombie extends Entity implements GameObject {
    private static int direction;
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
            drawHealthBar(g2d, leftEdge - x - 10, topEdge - y - 10); // Draw health bar above the zombie
        }
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(leftEdge - x, topEdge - y, (int) getWidth(), (int) getHeight());
        
    }

    private void drawHealthBar(Graphics2D g2d, int x, int y) {
        int barWidth = 50;
        int barHeight = 10;
        int healthBarWidth = (int) ((health / 100.0) * barWidth);

        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, barWidth, barHeight);

        g2d.setColor(Color.GREEN);
        g2d.fillRect(x, y, healthBarWidth, barHeight);
    }

    public boolean isColliding(Rectangle rect) {
        return intersects(rect);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }



    public void chasePlayer(Rectangle player) {
        int vy = 0;
        int vx = 0;

        // System.out.println("Player X: " + playerX + " Player Y: " + playerY);

        if (x < GamePanel.offsetX) {
            vx += movementSpeed;
        } else if (x > GamePanel.offsetX) {
            vx -= movementSpeed;
        }

        if (y < GamePanel.offsetY) {
            vy += movementSpeed;
        } else if (y > GamePanel.offsetY) {
            vy -= movementSpeed;
        }

        Rectangle rect = new Rectangle(x + vx, y + vy, width, height);
        ArrayList<Rectangle> newCollisions = CollisionManager.collidables;
        newCollisions.remove(this);
        if(!CollisionManager.isColliding(rect, newCollisions)){
            x += vx;
            y+= vy;
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
