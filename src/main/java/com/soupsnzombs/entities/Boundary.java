package com.soupsnzombs.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

public class Boundary {
    private BufferedImage oceanTile;
    private static final int TILE_SIZE = 64; // Size of each ocean tile

    public Boundary() {
        oceanTile = Images.ocean; // Make sure "ocean" is loaded in Images class
    }

    public void draw(Graphics2D g2d, int leftBoundary, int rightBoundary, int topBoundary, int bottomBoundary) {
        if (oceanTile == null) {
            System.out.println("Warning: Ocean tile is null!");
            return;
        }

        // Draw repeating ocean tiles for left boundary
        if (leftBoundary > 0) {
            for (int y = 0; y < GamePanel.screenHeight; y += TILE_SIZE) {
                for (int x = 0; x < leftBoundary; x += TILE_SIZE) {
                    g2d.drawImage(oceanTile, x, y, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        // Draw repeating ocean tiles for right boundary
        if (rightBoundary > 0) {
            int startX = GamePanel.screenWidth - rightBoundary;
            for (int y = 0; y < GamePanel.screenHeight; y += TILE_SIZE) {
                for (int x = startX; x < GamePanel.screenWidth; x += TILE_SIZE) {
                    g2d.drawImage(oceanTile, x, y, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        // Draw repeating ocean tiles for top boundary
        if (topBoundary > 0) {
            for (int y = 0; y < topBoundary; y += TILE_SIZE) {
                for (int x = 0; x < GamePanel.screenWidth; x += TILE_SIZE) {
                    g2d.drawImage(oceanTile, x, y, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        // Draw repeating ocean tiles for bottom boundary
        if (bottomBoundary > 0) {
            int startY = GamePanel.screenHeight - bottomBoundary;
            for (int y = startY; y < GamePanel.screenHeight; y += TILE_SIZE) {
                for (int x = 0; x < GamePanel.screenWidth; x += TILE_SIZE) {
                    g2d.drawImage(oceanTile, x, y, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        // Reset the transform
        g2d.setTransform(GamePanel.oldTransformation);
    }
}