package com.soupsnzombs.buildings;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bush extends Building {
    int mapX, mapY, bushX, bushY;
    BufferedImage bushSprite;


    public Bush(int x, int y, int width, int height) {
        super(x, y, width, height);
        bushSprite = Images.spriteImages.get("manBlue_gun.png");
        mapX = x;
        mapY = y;
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int w, int h) {
        bushX = mapX + GamePanel.offsetX;
        bushY = mapY + GamePanel.offsetY;

        g2d.setColor(Color.CYAN);
        g2d.fillRect(this.bushX, this.bushY, this.width, this.height);
        g2d.drawImage(bushSprite, bushX, bushY, width, height, null);

       // System.out.println("bush drawn");
        if (GamePanel.debugging) {

            // Draw the building's border
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(bushX, bushY, width, height);

            // For debugging: draw the building's rectangle
            g2d.setColor(Color.GREEN);
            g2d.drawRect(x, y, width, height);
        }
    }

    @Override
    public Rectangle getBounds() {
            return new Rectangle(super.x, super.y, super.width, super.height);
    }

    /*
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void drawCoords(Graphics2D g2d) {
        // Display render coordinates in the top-right corner (for debugging)
        g2d.setColor(Color.WHITE);
        // g2d.drawString("X: " + renderX + " Y: " + renderY, screenWidth - 100, 20);

        // as soon as the building is off the screen, remove it, as soon as we're past
        // left edge show it
        int leftEdge = GamePanel.offsetX + (GamePanel.screenWidth / 2);
        int topEdge = GamePanel.offsetY + (GamePanel.screenHeight / 2);
        drawBuilding(g2d, x, y, leftEdge, topEdge);
    }

     */
}
