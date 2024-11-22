package com.soupsnzombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.soupsnzombs.CollisionManager;

public class AllBuildings {
    ArrayList<Building> buildings = new ArrayList<>();

    public void addBuilding(Building b) {
        buildings.add(b);
    }

    public AllBuildings(CollisionManager mgr) {
        buildings.add(new GenericBuilding(100, 100, 50, 50));
        buildings.add(new GenericBuilding(200, 200, 50, 50));
        buildings.add(new GenericBuilding(300, 300, 50, 50));

        for (Building b : buildings) {
            mgr.addCollidable(b.getBounds());
        }

    }

    public void draw(Graphics2D g2d) {
        for (Building b : buildings) {
            b.draw(g2d);
            // draw rect x,y,w,h
            g2d.setColor(Color.RED);
            g2d.drawString("X: " + b.x + " Y: " + b.y + " W: " + b.width + " H: " + b.height, 20,
                    buildings.indexOf(b) * 20 + 40);
        }

    }
}
