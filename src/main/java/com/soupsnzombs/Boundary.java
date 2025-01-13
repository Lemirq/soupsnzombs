package com.soupsnzombs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

public class Boundary {
    private BufferedImage oceanTile;
    private static final int TILE_SIZE = 64;

    public Boundary() {
        oceanTile = Images.ocean;
    }

    public void draw(Graphics2D g2d, int leftBoundary, int rightBoundary, int topBoundary, int bottomBoundary) {
        if (oceanTile == null) {
            System.out.println("Warning: Ocean tile is null!");
            return;
        }

        // Calculate the offset for tiling based on game camera position
        int offsetTileX = GamePanel.offsetX % TILE_SIZE;
        int offsetTileY = GamePanel.offsetY % TILE_SIZE;

        // Draw left boundary
        if (leftBoundary > 0) {
            int startX = -TILE_SIZE + offsetTileX;
            for (int y = -TILE_SIZE + offsetTileY; y < GamePanel.screenHeight + TILE_SIZE; y += TILE_SIZE) {
                for (int x = startX; x < leftBoundary; x += TILE_SIZE) {
                    g2d.drawImage(oceanTile, x, y, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        // Draw right boundary
        if (rightBoundary > 0) {
            int startX = GamePanel.screenWidth - rightBoundary + offsetTileX;
            for (int y = -TILE_SIZE + offsetTileY; y < GamePanel.screenHeight + TILE_SIZE; y += TILE_SIZE) {
                for (int x = startX; x < GamePanel.screenWidth + TILE_SIZE; x += TILE_SIZE) {
                    g2d.drawImage(oceanTile, x, y, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        // Draw top boundary
        if (topBoundary > 0) {
            int startY = -TILE_SIZE + offsetTileY;
            for (int y = startY; y < topBoundary; y += TILE_SIZE) {
                for (int x = -TILE_SIZE + offsetTileX; x < GamePanel.screenWidth + TILE_SIZE; x += TILE_SIZE) {
                    g2d.drawImage(oceanTile, x, y, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        // Draw bottom boundary
        if (bottomBoundary > 0) {
            int startY = GamePanel.screenHeight - bottomBoundary + offsetTileY;
            for (int y = startY; y < GamePanel.screenHeight + TILE_SIZE; y += TILE_SIZE) {
                for (int x = -TILE_SIZE + offsetTileX; x < GamePanel.screenWidth + TILE_SIZE; x += TILE_SIZE) {
                    g2d.drawImage(oceanTile, x + GamePanel.offsetX, y + GamePanel.offsetY, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        g2d.setTransform(GamePanel.oldTransformation);
    }
}