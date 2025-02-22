package com.soupsnzombs.entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import com.soupsnzombs.GamePanel;
import com.soupsnzombs.UI.HealthBar;
import com.soupsnzombs.utils.Images;
import com.soupsnzombs.utils.KeyHandler;
import com.soupsnzombs.utils.SoundManager;
import com.soupsnzombs.utils.SoundManager.Sound;

public class Player extends Entity implements GameObject {
    public static int money;
    public static int score = 0;
    int health;
    BufferedImage spritePistol;
    BufferedImage spriteNoGun;
    BufferedImage spriteSniper;
    BufferedImage spriteMachine;
    public HealthBar bar = new HealthBar(100);
    public static int shotCoolDownTime = 100; // always 100, see keyhandler for how the time is subtracted in relation
                                              // to the firerate of the gun
    public static boolean showFireRateBar = false;
    private Gun gun;

    public Player(Gun gun) {
        super(0, 0, 0, 0, 100,
                GamePanel.MOVE_SPEED);

        spritePistol = Images.spriteImages.get("manBrown_gun.png");
        spriteNoGun = Images.spriteImages.get("manBrown_stand.png");
        spriteMachine = Images.spriteImages.get("manBrown_machine.png");
        spriteSniper = Images.spriteImages.get("manBrown_silencer.png");

        this.width = spriteNoGun.getWidth();
        this.height = spriteNoGun.getHeight();
        this.x = GamePanel.screenWidth / 2 - width / 2 - 1000;
        this.y = GamePanel.screenHeight / 2 - height / 2 - 1000;
        money = 90;
        this.health = 100;
        this.gun = gun;
    }

    public Gun getGun() {
        return this.gun;
    }

    public void setGun(Gun g) {
        // prevent timers from overlapping but unsure if really needed
        KeyHandler.shootCooldown.stop();
        KeyHandler.automaticGunTimer.stop();
        KeyHandler.canShoot = true;
        showFireRateBar = false;
        //

        this.gun = g;
    }

    /**
     * @return the health
     * @param healthAmount the health to set
     */
    public void decreaseHealth(int healthAmount) {
        SoundManager.playSound(Sound.DAMAGE, false);
        GamePanel.damageEffectStartTime = System.currentTimeMillis();
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
        if (this.health > 100) {
            this.health = 100;
        }
        bar.setHealthValue(this.health);
    }

    public int getHealth() {
        return this.health;
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
            g2d.fillRoundRect(x - 20, y - 15, fireRateBarWidth, barHeight, 10, 10);
        }
    }

    public void draw(Graphics2D g2d) {
        // bar.draw(g2d);
        drawFireRateBar(g2d, x + GamePanel.offsetX + 8, y + GamePanel.offsetY - 13);
        int centerXPlayer = (GamePanel.screenWidth / 2 - width / 2);
        int centerYPlayer = (GamePanel.screenHeight / 2 - height / 2);

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
        switch (getGun().getAutomaticState()) {
            case -1:
                if (getGun().getMaxAmmo() == 1)
                    g2d.drawImage(spriteSniper, centerXPlayer, centerYPlayer, null);
                else
                    g2d.drawImage(spritePistol, centerXPlayer, centerYPlayer, null);
                break;
            case 0:
                g2d.drawImage(spriteSniper, centerXPlayer, centerYPlayer, null);
                break;
            case 1:
                g2d.drawImage(spriteMachine, centerXPlayer, centerYPlayer, null);
                break;
            default:
                g2d.drawImage(spriteNoGun, centerXPlayer, centerYPlayer, null);
                break;
        }
        g2d.setTransform(GamePanel.oldTransformation);

        // draw score bottom left corner

        if (GamePanel.debugging) {
            // draw map coordinates next to player for debugging
            g2d.setColor(Color.RED);
            g2d.drawString("CX: " + centerXPlayer + " CY: " + centerYPlayer, 20,
                    80);
            g2d.drawString("OX: " + GamePanel.offsetX + " OY: " + GamePanel.offsetY, 20,
                    100);

            // draw Rectangle x,y,w,h
            g2d.drawString("X: " + x + " Y: " + y + " W: " + width + " H: " + height, 20, 300);

            g2d.setColor(Color.green);
            g2d.drawRect(x, y, width, height);
        }

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void dropGun() {
        this.gun.getDrop().x = this.x;
        this.gun.getDrop().y = this.y;
        GamePanel.gunDrops.add(this.gun.getDrop());
    }

    public void dropGun(int x, int y) {
        this.gun.getDrop().x = x;
        this.gun.getDrop().y = y;
        GamePanel.gunDrops.add(this.gun.getDrop());
    }
}
