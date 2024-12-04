package com.soupsnzombs.buildings;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.CollisionManager;

import java.awt.*;
import java.util.ArrayList;

public class AllTrees {
    public ArrayList<GenericBuilding> trees = new ArrayList<>();

    public void addBuilding(Building t) {
        trees.add((GenericBuilding) t);
    }

    public AllTrees() {
        trees.add(new GenericBuilding(-400, 40, 300, 50));
        for (Building b : trees) {
            CollisionManager.addCollidable(b);
        }

    }

    public void draw(Graphics2D g2d) {
        for (Building t : trees) {
            t.draw(g2d);
            // draw rect x,y,w,h
            g2d.setColor(Color.RED);
            if (GamePanel.debugging) {
                g2d.drawString("X: " + t.x + " Y: " + t.y + " W: " + t.width + " H: " + t.height, 20,
                        trees.indexOf(t) * 20 + 40);
            }
        }

    }
}


