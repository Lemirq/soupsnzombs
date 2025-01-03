package com.soupsnzombs.entities;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.zombies.Zombie;

import java.awt.*;
import java.util.ArrayList;

public class Coin extends Rectangle {

    public Coin(int x, int y, int w, int h) {
        super(x, y, 10, 10);
    }

    public void draw(Graphics2D g2d) {
        // Update bullet position
        // Set bullet color to yellow
        g2d.setColor(Color.YELLOW);
        // Draw the bullet
        g2d.fillOval(this.x, this.y, this.width, this.height);

        if (GamePanel.debugging) {
            g2d.setColor(Color.GREEN);
            g2d.drawRect(x, y, width, height);
        }
    }


}
