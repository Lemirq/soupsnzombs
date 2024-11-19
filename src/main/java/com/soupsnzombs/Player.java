package com.soupsnzombs;

import java.awt.*;

public class Player extends Entity implements GameObject {
    boolean isDead = false;
    int money;

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
        g2d.fillRect(GamePanel.screenWidth - 200, 0, 200, 50);
    }
}
