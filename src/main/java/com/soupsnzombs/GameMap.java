package com.soupsnzombs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.soupsnzombs.entities.Boundary;
import com.soupsnzombs.utils.Images;

public class GameMap {
    public static final int GRID_SIZE = 50;
    public int leftBoundary, rightBoundary, topBoundary, bottomBoundary;
    Boundary boundary = new Boundary();

    public void draw(Graphics2D g2d, int width, int height) {
        // draw grid
        // g2d.setStroke(new BasicStroke(4));
        // g2d.setColor(Theme.GRID);
        // for (int x = GamePanel.offsetX % GRID_SIZE; x < width; x += GRID_SIZE) {
        // // g2d.drawLine(x, 0, x, height);
        // }
        // for (int y = GamePanel.offsetY % GRID_SIZE; y < height; y += GRID_SIZE) {
        // g2d.drawLine(0, y, width, y);
        // }

        // draw map from Images.grass with offsetX and offsetY
        // accounting for the player's position
        // BufferedImage grass1 = Images.tileImages.get("tile_3360000");
        BufferedImage grass1 = Images.tileImages.get("tile_030000");
        // BufferedImage grass3 = Images.tileImages.get("tile_040000");
        for (int x = GamePanel.offsetX % GRID_SIZE - Images.grass.getWidth(); x <= width
                + Images.grass.getWidth(); x += GRID_SIZE) {
            for (int y = GamePanel.offsetY % GRID_SIZE - Images.grass.getHeight(); y <= height
                    + Images.grass.getHeight(); y += GRID_SIZE) {
                g2d.drawImage(grass1, x, y, null);
            }
        }

        int centerX = GamePanel.screenWidth / 2;
        int centerY = GamePanel.screenHeight / 2;
        // draw boundaries
        int leftBoundary = GamePanel.offsetX - centerX - GamePanel.X_Bounds[1] + GamePanel.screenWidth;
        int rightBoundary = -(GamePanel.offsetX + centerX) + GamePanel.X_Bounds[0] + GamePanel.screenWidth;
        int topBoundary = GamePanel.offsetY - centerY - GamePanel.Y_Bounds[1] + GamePanel.screenHeight;
        int bottomBoundary = -(GamePanel.offsetY + centerY) + GamePanel.Y_Bounds[0] + GamePanel.screenHeight;

        boundary.draw(g2d, leftBoundary, rightBoundary, topBoundary, bottomBoundary);

        if (GamePanel.debugging) {
            g2d.setColor(Color.red);
            // bottom left corner, draw stringgs for left,right,top,bottom boundaries
            g2d.drawString("Left: " + leftBoundary, 10, GamePanel.screenHeight - 10);
            g2d.drawString("Right: " + rightBoundary, 10, GamePanel.screenHeight - 30);
            g2d.drawString("Top: " + topBoundary, 10, GamePanel.screenHeight - 50);
            g2d.drawString("Bottom: " + bottomBoundary, 10, GamePanel.screenHeight - 70);
        }
    }
}