package com.soupsnzombs.entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import com.soupsnzombs.GamePanel;
import com.soupsnzombs.UI.HealthBar;
import com.soupsnzombs.utils.Images;
import com.soupsnzombs.utils.KeyHandler;
public class Player extends Entity implements GameObject {
    int money;
    public static int score;
    int health;
    BufferedImage sprite;
    public HealthBar bar = new HealthBar(100);
    public static int shotCoolDownTime = 100; //always 100, see keyhandler for how the time is subtracted in relation to the firerate of the gun
    public static boolean showFireRateBar = false;
    private Gun gun;
    public Player(Gun gun) {
        super(0, 0, 0, 0, 100,
                GamePanel.MOVE_SPEED);

        sprite = Images.spriteImages.get("manBrown_gun.png");

        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.x = GamePanel.screenWidth / 2 - width / 2;
        this.y = GamePanel.screenHeight / 2 - height / 2;
        this.health = 100;
        this.money = 0;
        this.health = 100;
        this.gun = gun;
    }

    public Gun getGun() {
        return this.gun;
    }

    public void setGun(Gun g) {
        KeyHandler.t.stop();
        KeyHandler.canShoot = true;
        this.gun = g;
    }

    /**
     * @return the health
     * @param healthAmount the health to set
     */
    public void decreaseHealth(int healthAmount) {
        this.health = Math.max(0, this.health - healthAmount);
        bar.setHealthValue(this.health);

        if (this.health <= 0) {
            // Game over
            this.alive = false;
        }
    }

    /**
     * @return the health
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.bar = new HealthBar(health);
        this.health = health;
        if (this.health <= 0) {
            // Game over
            this.alive = false;
        }
    }

    /**
     * @return the health
     * @param healthAmount the health to set
     */
    public void increaseHealth(int healthAmount) {
        this.health += healthAmount;
    }

    /**
     * @return the money
     * @param moneyAmount the money to set
     */
    public void addMoney(int moneyAmount) {
        money += moneyAmount;
    }

    /**
     * @return the money
     * @param moneyAmount the money to set
     */
    public void removeMoney(int moneyAmount) {
        money -= moneyAmount;
    }

    public void drawFireRateBar(Graphics2D g2d, int x, int y) {
        int barWidth = 50;
        int barHeight = 10;
        int fireRateBarWidth = (int) ((shotCoolDownTime / 100.0) * barWidth); 
        if (showFireRateBar) {
           // g2d.setColor(Color.BLACK);
           // g2d.fillRoundRect(x-10, y-2, barWidth, barHeight, 10, 10); 

            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(x-10, y-2, fireRateBarWidth, barHeight, 10, 10); 
        }
    }

    public void draw(Graphics2D g2d) {
        bar.draw(g2d);
        drawFireRateBar(g2d, x+GamePanel.offsetX+8, y+GamePanel.offsetY-13);
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
        g2d.drawImage(sprite, centerXPlayer, centerYPlayer, null);

        g2d.setTransform(GamePanel.oldTransformation);

        // draw score bottom left corner
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + score, 20, GamePanel.screenHeight - 20);

        if (GamePanel.debugging) {
            // draw map coordinates next to player for debugging
            g2d.setColor(Color.RED);
            g2d.drawString("CX: " + centerXPlayer + " CY: " + centerYPlayer, 20,
                    10);
            g2d.drawString("OX: " + GamePanel.offsetX + " OY: " + GamePanel.offsetY, 20,
                    20);

            // draw Rectangle x,y,w,h
            g2d.drawString("X: " + x + " Y: " + y + " W: " + width + " H: " + height, GamePanel.screenWidth - 300, 40);

            g2d.setColor(Color.green);
            g2d.drawRect(x, y, width, height);
        }

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
