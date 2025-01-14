package com.soupsnzombs.buildings;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.soupsnzombs.GamePanel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static com.soupsnzombs.utils.Images.grayWall;

public class Wall extends Building {
    int mapX, mapY;
    BufferedImage sprite;
    private int tileWidth = 0;
    private int tileHeight = 0;

    public Wall(int x, int y, int width, int height, BufferedImage sprite, int tileWidth, int tileHeight) {
        super(x, y, width, height);
        this.mapX = x;
        this.mapY = y;
        this.sprite = sprite;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.mapX = x;
        this.mapY = y;
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int w, int h) {
    }

    public void draw(Graphics2D g2d) {

        // Calculate the building's position on the screen
        int buildingX = mapX + GamePanel.offsetX;
        int buildingY = mapY + GamePanel.offsetY;

        // Draw the building
        g2d.setColor(Color.BLUE);

        // g2d.fillRect(buildingX, buildingY, width, height);

        if (sprite == null) {
            g2d.drawImage(grayWall, buildingX, buildingY, width, height, null);
        } else if (tileWidth > 0 && tileHeight > 0) {
            // Tile the sprite
            for (int x = 0; x < width; x += tileWidth) {
                for (int y = 0; y < height; y += tileHeight) {
                    // Calculate the actual width and height for this tile (handles edge cases)
                    int tileW = Math.min(tileWidth, width - x);
                    int tileH = Math.min(tileHeight, height - y);
                    g2d.drawImage(sprite, buildingX + x, buildingY + y, tileW, tileH, null);
                }
            }
        } else {
            // Draw single sprite stretched
            g2d.drawImage(sprite, buildingX, buildingY, width, height, null);
        }

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
