package com.soupsnzombs;

import java.awt.Color;
import java.awt.Graphics2D;

public class Boundary {
    public void draw(Graphics2D g2d, int leftBoundary, int rightBoundary, int topBoundary, int bottomBoundary) {

        // Draw black rectangles outside the map bounds
        g2d.setColor(Color.BLACK);
        // if left boundary is between zero and negative centerx
        if (leftBoundary > 0) {
            g2d.fillRect(0, 0, leftBoundary, GamePanel.screenHeight);
        }
        // if right boundary is between centerx and width
        if (rightBoundary > 0) {
            g2d.fillRect(GamePanel.screenWidth - rightBoundary, 0, rightBoundary, GamePanel.screenHeight);
        }
        // if top boundary is between zero and negative centery
        if (topBoundary > 0) {
            g2d.fillRect(0, 0, GamePanel.screenWidth, topBoundary);
        }
        // // if bottom boundary is between centery and height
        if (bottomBoundary > 0) {
            g2d.fillRect(0, GamePanel.screenHeight - bottomBoundary, GamePanel.screenWidth, bottomBoundary);
        }

        // Reset the transform
        g2d.setTransform(GamePanel.oldTransformation);
    }
}
