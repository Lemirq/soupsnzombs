package com.soupsnzombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.soupsnzombs.utils.CollisionManager;

public class AllBuildings {
    public ArrayList<Building> buildings = new ArrayList<>();

    public void addBuilding(Building b) {
        buildings.add(b);
    }

    public AllBuildings() {
        for (Building b : buildings) {
            CollisionManager.addCollidable(b);
        }

    }

    public void draw(Graphics2D g2d) {
        for (Building b : buildings) {
            b.draw(g2d);
            // draw rect x,y,w,h
            g2d.setColor(Color.RED);
            // if (GamePanel.debugging) {
            // g2d.drawString("X: " + b.x + " Y: " + b.y + " W: " + b.width + " H: " +
            // b.height, 20,
            // buildings.indexOf(b) * 20 + 40);
            // }
        }

    }

}
