package com.soupsnzombs.buildings;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static com.soupsnzombs.utils.Images.grayWall;

import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.AlphaComposite;
import java.awt.Composite;

public class Wall extends Building {
    int mapX, mapY;
    BufferedImage sprite;
    private int tileWidth = 0;
    private int tileHeight = 0;
    private static final int GRADIENT_SIZE = 30; // Size of the gradient transition
    private boolean isOceanWall = false;

    public Wall(int x, int y, int width, int height, BufferedImage sprite, int tileWidth, int tileHeight) {
        super(x, y, width, height);
        this.mapX = x;
        this.mapY = y;
        this.sprite = sprite;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.isOceanWall = sprite == Images.ocean;
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
        int buildingX = mapX + GamePanel.offsetX;
        int buildingY = mapY + GamePanel.offsetY;

        if (isOceanWall) {
            // Create gradient paint
            GradientPaint gradientPaint;
            Color oceanColor = new Color(0, 105, 148, 200); // Semi-transparent ocean blue
            Color groundColor = new Color(0, 105, 148, 0); // Fully transparent

            // Determine gradient direction based on wall position
            if (height > width) { // Vertical walls (left/right)
                if (mapX < 0) { // Left wall
                    gradientPaint = new GradientPaint(
                            buildingX + width - GRADIENT_SIZE, 0, oceanColor,
                            buildingX + width, 0, groundColor);
                } else { // Right wall
                    gradientPaint = new GradientPaint(
                            buildingX, 0, oceanColor,
                            buildingX + GRADIENT_SIZE, 0, groundColor);
                }
            } else { // Horizontal walls (top/bottom)
                if (mapY < 0) { // Top wall
                    gradientPaint = new GradientPaint(
                            0, buildingY + height - GRADIENT_SIZE, oceanColor,
                            0, buildingY + height, groundColor);
                } else { // Bottom wall
                    gradientPaint = new GradientPaint(
                            0, buildingY, oceanColor,
                            0, buildingY + GRADIENT_SIZE, groundColor);
                }
            }

            // Store original paint
            Paint originalPaint = g2d.getPaint();

            // Apply gradient
            g2d.setPaint(gradientPaint);
            g2d.fillRect(buildingX, buildingY, width, height);

            // Draw tiled ocean texture with transparency
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));

            // Tile the ocean sprite
            for (int x = 0; x < width; x += tileWidth) {
                for (int y = 0; y < height; y += tileHeight) {
                    int tileW = Math.min(tileWidth, width - x);
                    int tileH = Math.min(tileHeight, height - y);
                    g2d.drawImage(sprite, buildingX + x, buildingY + y, tileW, tileH, null);
                }
            }

            // Restore original composite and paint
            g2d.setComposite(originalComposite);
            g2d.setPaint(originalPaint);
        } else {
            // Regular wall drawing code
            if (sprite == null) {
                g2d.drawImage(grayWall, buildingX, buildingY, width, height, null);
            } else {
                for (int x = 0; x < width; x += tileWidth) {
                    for (int y = 0; y < height; y += tileHeight) {
                        int tileW = Math.min(tileWidth, width - x);
                        int tileH = Math.min(tileHeight, height - y);
                        g2d.drawImage(sprite, buildingX + x, buildingY + y, tileW, tileH, null);
                    }
                }
            }
        }

        if (GamePanel.debugging) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(buildingX, buildingY, width, height);
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
