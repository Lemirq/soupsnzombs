package com.soupsnzombs.buildings;
import com.soupsnzombs.GamePanel;

import java.awt.*;

public class Tree extends Building {
    int x, y, width, height;

    public Tree(int x, int y, int width, int height) {
        super(x, y, width, height);
        System.out.println("Tree created at x:" + x + ", y:" + y);
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int leftEdge, int topEdge) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

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
