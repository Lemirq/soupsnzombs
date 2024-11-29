package com.soupsnzombs;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.soupsnzombs.utils.Images;

public class Map {
    public static final int GRID_SIZE = 50;

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
    }
}
