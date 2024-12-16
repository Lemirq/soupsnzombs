package com.soupsnzombs.buildings;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Wall extends Building {
    private BufferedImage wallSprite;
    int mapX, mapY;

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.wallSprite = Images.spriteImages.get("manBlue_gun.png");
        mapX = x;
        mapY = y;
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int w, int h) {
        // Calculate the building's position on the screen
        int buildingX = mapX + GamePanel.offsetX;
        int buildingY = mapY + GamePanel.offsetY;

        // Draw the building
        g2d.setColor(Color.BLUE);
        g2d.drawImage(wallSprite, buildingX, buildingY, width, height, null);

        g2d.fillRect(buildingX, buildingY, width, height);

        if (GamePanel.debugging) {

            // Draw the building's border
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(buildingX, buildingY, width, height);

            // For debugging: draw the building's rectangle
            g2d.setColor(Color.GREEN);
            g2d.drawRect(x, y, width, height);
        }
    }

    public boolean isColliding(Rectangle rect) {
        return intersects(rect);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }
}
