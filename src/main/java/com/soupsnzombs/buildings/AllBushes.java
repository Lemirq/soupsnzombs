package com.soupsnzombs.buildings;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.CollisionManager;

import java.awt.*;
import java.util.ArrayList;

public class AllBushes {
    public ArrayList<Bush> bushes = new ArrayList<>();

    public void addBush(Bush bush) {
        bushes.add(bush);
    }

    public AllBushes() {
        bushes.add(new Bush(250, 100,100, 50));
        for (Bush bush : bushes) {
            CollisionManager.addCollidable(bush);
        }

    }

    public void drawBush(Graphics2D g2d) {
        for (Bush bush : bushes) {
            bush.drawBuilding(g2d, 250, 100, 100, 50);
            // draw rect x,y,w,h
            g2d.setColor(Color.RED);
            if (GamePanel.debugging) {
                g2d.drawString("X: " + bush.x + " Y: " + bush.y + " W: " + bush.width + " H: " + bush.height, 20,
                        bushes.indexOf(bush) * 20 + 40);
            }
        }

    }
}


