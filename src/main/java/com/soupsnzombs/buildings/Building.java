package com.soupsnzombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.soupsnzombs.GamePanel;

public abstract class Building extends Rectangle {

    public Building(int x, int y, int width, int height) {
        super(x, y, width, height);
        System.out.println("Building created at x: " + x + " y: " + y);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    abstract void drawBuilding(Graphics2D g2d, int x, int y, int leftEdge, int topEdge);

    public abstract Rectangle getBounds();

    public void draw(Graphics2D g2d) {
        // Display render coordinates in the top-right corner (for debugging)
        g2d.setColor(Color.WHITE);
        // g2d.drawString("X: " + renderX + " Y: " + renderY, screenWidth - 100, 20);

        // as soon as the building is off the screen, remove it, as soon as we're past
        // left edge show it
        int leftEdge = GamePanel.offsetX + (GamePanel.screenWidth / 2);
        int topEdge = GamePanel.offsetY + (GamePanel.screenHeight / 2);
        drawBuilding(g2d, x, y, leftEdge, topEdge);
    }
}
