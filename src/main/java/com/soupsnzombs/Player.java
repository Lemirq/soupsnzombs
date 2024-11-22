package com.soupsnzombs;

import java.awt.*;

public class Player extends Entity implements GameObject {
    boolean isDead = false;
    int money;

    public Player() {
        super((GamePanel.screenWidth / 2 - 10 / 2) - 10, (GamePanel.screenHeight / 2 - 40 / 2) - 10, 40, 70, 100,
                GamePanel.MOVE_SPEED);
        this.money = 0;
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
        int centerX = GamePanel.screenWidth / 2 - width / 2;
        int centerY = GamePanel.screenHeight / 2 - height / 2;

        // yellow rectangle
        g2d.setColor(Color.YELLOW);
        // fill a rectangle in the middle of the screen
        g2d.fillRect(centerX, centerY, width, height);
        // draw map coordinates next to player for debugging
        g2d.setColor(Color.RED);
        g2d.drawString("CX: " + centerX + " CY: " + centerY, 20,
                10);
        g2d.drawString("OX: " + GamePanel.offsetX + " OY: " + GamePanel.offsetY, 20,
                20);

        // draw rectangle x,y,w,h
        g2d.setColor(Color.RED);
        g2d.drawString("X: " + x + " Y: " + y + " W: " + width + " H: " + height, GamePanel.screenWidth - 300, 40);
    }
}
