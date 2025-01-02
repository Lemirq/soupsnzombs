package com.soupsnzombs.entities;

import com.soupsnzombs.GamePanel;

import java.awt.*;

public class Coin extends Entity {
    int startX;
    int startY;
    int moneyValue;

    public Coin(int x, int y, int w, int h, int health, double speed) {
        super(x, y, 10, 10, 1, 0);
        this.startX = x;
        this.startY = y;
        moneyValue = health;
    }

    public void draw(Graphics2D g2d) {
        // Update bullet position
        // Set bullet color to yellow
        g2d.setColor(Color.YELLOW);
        // Draw the bullet
        g2d.fillOval(x + GamePanel.offsetX, y + GamePanel.offsetY, width, height);

        if (GamePanel.debugging) {
            g2d.setColor(Color.GREEN);
            g2d.drawRect(x, y, width, height);
        }
    }


}
