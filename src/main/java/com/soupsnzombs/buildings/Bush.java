package com.soupsnzombs.buildings;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bush extends Building {
    BufferedImage bushSprite;
    int mapX, mapY, bushX, bushY;

    public Bush(int x, int y, int width, int height) {
        super(x, y, width, height);
        mapX = x;
        mapY = y;
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int w, int h) {
    }

    public void draw(Graphics2D g2d) {
        bushSprite = Images.tileImages.get("greenBush");
        bushX = mapX + GamePanel.offsetX;
        bushY = mapY + GamePanel.offsetY;

        // Store original composite
        Composite originalComposite = g2d.getComposite();

        g2d.setColor(Color.CYAN);
        // Set transparency to 70%
        float transparency = 0.7f;
        if (getBounds().intersects(GamePanel.player.getBounds())) g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));

        g2d.drawImage(bushSprite, bushX, bushY, width, height, null);

        // Restore original composite
        g2d.setComposite(originalComposite);

        if (bushSprite == null) {
            System.out.println("bushSprite is null");
        }

        // System.out.println("bush drawn");

        // TODO: just a reminder that matthew removed drawing the tree hitbox because
        // the player can go through it
        /*
         * if (GamePanel.debugging) {
         * 
         * // Draw the building's border
         * g2d.setColor(Color.RED);
         * g2d.setStroke(new BasicStroke(1));
         * g2d.drawRect(bushX, bushY, width, height);
         * 
         * // For debugging: draw the building's rectangle
         * g2d.setColor(Color.GREEN);
         * g2d.drawRect(x, y, width, height);
         * }
         */

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }

    /*
     * public void setSize(int width, int height) {
     * this.width = width;
     * this.height = height;
     * }
     * 
     * public void drawCoords(Graphics2D g2d) {
     * // Display render coordinates in the top-right corner (for debugging)
     * g2d.setColor(Color.WHITE);
     * // g2d.drawString("X: " + renderX + " Y: " + renderY, screenWidth - 100, 20);
     * 
     * // as soon as the building is off the screen, remove it, as soon as we're
     * past
     * // left edge show it
     * int leftEdge = GamePanel.offsetX + (GamePanel.screenWidth / 2);
     * int topEdge = GamePanel.offsetY + (GamePanel.screenHeight / 2);
     * drawBuilding(g2d, x, y, leftEdge, topEdge);
     * }
     * 
     */
}
