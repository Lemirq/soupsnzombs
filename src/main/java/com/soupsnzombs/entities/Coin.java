package com.soupsnzombs.entities;

import com.soupsnzombs.GamePanel;

import java.awt.*;
import static com.soupsnzombs.utils.Images.coin;

public class Coin extends Rectangle {

    public Coin(int x, int y, int w, int h) {
        super(x, y, 20, 20);
    }

    public void draw(Graphics2D g2d) {
        // Update bullet position
        // Set bullet color to yellow

        g2d.setColor(Color.YELLOW);
        // Draw the bullet
        // g2d.drawImage(this.coinSprite, this.x, this.y, 10, 10, null);

        g2d.drawImage(coin, this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width, this.height, null);
        // g2d.fillOval(this.x + GamePanel.offsetX, this.y + GamePanel.offsetY,
        // this.width, this.height);
        g2d.setColor(Color.BLACK);
        // g2d.drawString("$", this.x + GamePanel.offsetX + 6, this.y +
        // GamePanel.offsetY + 14);

        if (GamePanel.debugging) {
            g2d.setColor(Color.GREEN);
            g2d.drawRect(this.x, this.y, this.width, this.height);
        }
    }

}
