package com.soupsnzombs.buildings;

import com.soupsnzombs.GamePanel;
import static com.soupsnzombs.utils.Images.*;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Couch extends Building {
    public Couch(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int w, int h) {
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }

    public void draw(Graphics2D g2d) {
        int buildingX = x + GamePanel.offsetX;
        int buildingY = y + GamePanel.offsetY;

        g2d.drawImage(couch, buildingX, buildingY, width, height, null);

        if (couch == null) {
            System.out.println("couch is null");
        }
    }
}
