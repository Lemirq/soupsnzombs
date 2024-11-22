package com.soupsnzombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.soupsnzombs.GamePanel;

public class GenericBuilding extends Building {
    public GenericBuilding(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int w, int h) {
        // Draw a square
        g2d.setColor(Color.BLUE);
        int buildingX = (int) getX() + GamePanel.offsetX;
        int buildingY = (int) getY() + GamePanel.offsetY;
        int buildingWidth = (int) getWidth();
        int buildingHeight = (int) getHeight();
        g2d.fillRect(buildingX, buildingY, buildingWidth, buildingHeight);

        // for debugging
        // draw rect x,y,w,h
        g2d.setColor(Color.RED);

    }

    public boolean isColliding(Rectangle rect) {
        return intersects(rect);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }
}
