package com.soupsnzombs;

import java.awt.*;

public class Player extends Entity implements GameObject {
    boolean isDead = false;
    int money;
    int pw = 20;
    int ph = 50;

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

    public void draw(Graphics2D g2d) {
        // yellow rectangle
        g2d.setColor(Color.YELLOW);
        int centerX = GamePanel.screenWidth / 2 - pw / 2;
        int centerY = GamePanel.screenHeight / 2 - ph / 2;
        // fill a rectangle in the middle of the screen
        g2d.fillRect(centerX, centerY, pw, ph);
        // draw map coordinates next to player for debugging
        g2d.setColor(Color.BLACK);
        g2d.drawString("X: " + GamePanel.offsetX + " Y: " + GamePanel.offsetY, GamePanel.screenWidth / 2 - pw / 2,
                GamePanel.screenHeight / 2 - ph / 2);

    }
}
