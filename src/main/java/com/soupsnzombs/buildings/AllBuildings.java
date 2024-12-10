package com.soupsnzombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.CollisionManager;

public class AllBuildings {
    public ArrayList<Building> buildings = new ArrayList<>();

    public void addBuilding(Building b) {
        buildings.add(b);
     //   CollisionManager.addCollidable(b);
    }

    public AllBuildings() {
        buildings.add(new Wall(-400, 40, 300, 50));
        buildings.add(new Wall(100, 100, 50, 50));
        buildings.add(new Wall(200, 200, 50, 50));
        buildings.add(new Wall(300, 300, 50, 50));
        buildings.add(new Wall(400, 400, 50, 50));
        buildings.add(new Wall(500, 500, 50, 50));
        for (Building b : buildings) {
            CollisionManager.addCollidable(b);
        }

    }

    public void draw(Graphics2D g2d) {
        for (Building b : buildings) {
            b.draw(g2d);
            // draw rect x,y,w,h
            g2d.setColor(Color.RED);
            if (GamePanel.debugging) {
                g2d.drawString("X: " + b.x + " Y: " + b.y + " W: " + b.width + " H: " + b.height, 20,
                        buildings.indexOf(b) * 20 + 40);
            }
        }

    }

  

}
