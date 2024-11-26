package com.soupsnzombs.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.soupsnzombs.GamePanel;

import com.soupsnzombs.GamePanel.PlayerDir;
import com.soupsnzombs.utils.Images;


public class Player extends Entity implements GameObject {
    boolean isDead = false;
    int money;
  int health;
BufferedImage playerImg;

    public Player() {
        super(0, 0, 0, 0, 100,
                GamePanel.MOVE_SPEED);

        playerImg = Images.spriteImages.get("manBrown_gun.png");

        this.width = playerImg.getWidth();
        this.height = playerImg.getHeight();
        this.x = GamePanel.screenWidth / 2 - width / 2;
        this.y = GamePanel.screenHeight / 2 - height / 2;
        this.health = 100;
        this.money = 0;
        this.health = 100;
    }

    // Method to decrease health
    public void decreaseHealth(int healthAmount) {
        this.health -= healthAmount;
    }

    // Method to increase health
    public void increaseHealth(int healthAmount) {
        this.health += healthAmount;
    }

    // Method to add cash
    public void addMoney(int moneyAmount) {
        money += moneyAmount;
    }

    // Method to remove cash
    public void removeMoney(int moneyAmount) {
        money -= moneyAmount;
    }

    // Method to check if player is dead
    public boolean isDead() {
        if (health <= 0) {
            isDead = true;
        }
        return isDead;
    }

    public void draw(Graphics2D g2d) {
        int centerXPlayer = GamePanel.screenWidth / 2 - width / 2;
        int centerYPlayer = GamePanel.screenHeight / 2 - height / 2;

        // without image: testing
        // yellow Rectangle
        // g2d.setColor(Color.YELLOW);
        // fill a Rectangle in the middle of the screen
        // g2d.fillRect(centerX, centerY, width, height);

        // transform accordingly to direction, affine transform
        switch (GamePanel.direction) {
            case UP:
                g2d.rotate(Math.toRadians(270), GamePanel.screenWidth / 2, GamePanel.screenHeight / 2);
                break;

            case DOWN:
                g2d.rotate(Math.toRadians(90), GamePanel.screenWidth / 2, GamePanel.screenHeight / 2);
                break;

            case LEFT:
                g2d.rotate(Math.toRadians(180), GamePanel.screenWidth / 2, GamePanel.screenHeight / 2);
                break;
            case RIGHT:
                g2d.rotate(Math.toRadians(0), GamePanel.screenWidth / 2, GamePanel.screenHeight / 2);
                break;
            default:
                break;
        }
        g2d.drawImage(playerImg, centerXPlayer, centerYPlayer, null);

        g2d.setTransform(GamePanel.oldTransformation);

        // draw map coordinates next to player for debugging
        g2d.setColor(Color.RED);
        g2d.drawString("CX: " + centerXPlayer + " CY: " + centerYPlayer, 20,
                10);
        g2d.drawString("OX: " + GamePanel.offsetX + " OY: " + GamePanel.offsetY, 20,
                20);

        // draw Rectangle x,y,w,h
        g2d.setColor(Color.RED);
        g2d.drawString("X: " + x + " Y: " + y + " W: " + width + " H: " + height, GamePanel.screenWidth - 300, 40);

        g2d.drawRect(x, y, width, height);

    }

    public Rectangle getBounds() {
        return new Rectangle(GamePanel.offsetX + (Images.player_idle.getWidth() / 2),
        GamePanel.offsetY + (Images.player_idle.getHeight() / 2), Images.player_idle.getWidth(),
        Images.player_idle.getHeight());
    }
}
