package com.soupsnzombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.soupsnzombs.GamePanel;

public class GenericBuilding extends Building {
    int mapX, mapY;

    public GenericBuilding(int x, int y, int width, int height) {
        super(x, y, width + 40, height + 40);
        mapX = x;
        mapY = y;
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int w, int h) {
        this.x = mapX + GamePanel.offsetX - 10;
        this.y = mapY + GamePanel.offsetY - 10;

        // Draw a square
        g2d.setColor(Color.BLUE);
        int buildingX = (int) mapX + GamePanel.offsetX; // adding to offset the building
        int buildingY = (int) mapY + GamePanel.offsetY; // adding to offset the building
        g2d.fillRect(buildingX, buildingY, width, height);

        // for debugging
        // draw rect x,y,w,h
        g2d.setColor(Color.RED);
        g2d.drawRect(this.x, this.y, (int) getWidth(), (int) getHeight());

    }

    public boolean isColliding(Rectangle rect) {
        return intersects(rect);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }
}
