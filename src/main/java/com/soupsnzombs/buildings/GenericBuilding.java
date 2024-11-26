package com.soupsnzombs.buildings;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

import java.awt.Rectangle;

public class GenericBuilding extends Building {
    int mapX, mapY;

    public GenericBuilding(int x, int y, int width, int height) {
        super(x, y, width, height);
        mapX = x;
        mapY = y;
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int w, int h) {
        this.x = mapX + GamePanel.offsetX;
        this.y = mapY + GamePanel.offsetY;

        // Draw a square
        g2d.setColor(Color.BLUE);
        int buildingX = (int) mapX + GamePanel.offsetX; // adding to offset the building
        int buildingY = (int) mapY + GamePanel.offsetY; // adding to offset the building
        g2d.fillRect(buildingX, buildingY, width, height);

        // for debugging
        // draw rect x,y,w,h
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(this.x, this.y, (int) getWidth(), (int) getHeight());
        // draw image on top of the building
        g2d.drawImage(Images.spriteImages.get("hitman1_gun.png"), buildingX, buildingY, width, height, null);
    }

    public boolean isColliding(Rectangle rect) {
        return intersects(rect);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }
}
