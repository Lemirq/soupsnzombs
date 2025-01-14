package com.soupsnzombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import com.soupsnzombs.utils.CollisionManager;

public class AllBuildings {
    public static ArrayList<Building> buildings = new ArrayList<>();
    public static ArrayList<Wall> walls = new ArrayList<>();
    public static ArrayList<Tree> trees = new ArrayList<>();
    public static ArrayList<Bush> bushes = new ArrayList<>();
    ShopBuilding shopEntity = new ShopBuilding(250, 1400, 400, 200);

    public void addBuilding(Building b) {
        buildings.add(b);
    }

    public AllBuildings() {
        buildings.add(shopEntity);

        
        // walls.add(new Wall(-1000, 500, 1000, 1000));
        // trees.add(new Tree (-1000, 1500, 1000, 1000));
        Random random = new Random();

        // Define map bounds
        int minX = -4000, maxX = 1500;
        int minY = -1400, maxY = 2300;

        // Define exclusion zone
        int exclusionMinX = -3600, exclusionMaxX = 800;
        int exclusionMinY = 1300, exclusionMaxY = 2000;

        int x = minX;

        while (x <= maxX) {
            int y = minY;

            while (y <= maxY) {
                // Check if the point is within the exclusion zone
                if (!(x >= exclusionMinX && x <= exclusionMaxX && y >= exclusionMinY && y <= exclusionMaxY)) {
                    trees.add(new Tree(x, y, 25, 25)); // Add tree
                }

                // Add a random vertical gap
                y += 50 + random.nextInt(351); // Random gap between 50 and 400
            }

            // Add a random horizontal gap
            x += 150 + random.nextInt(151); // Random gap between 150 and 300
        }

        

        for (Tree t: trees) {
            bushes.add(new Bush(t.x-50, t.y-60, 150, 150));
        }

        for (Tree t : trees) {
            CollisionManager.addCollidable(t);
        }

        for (Wall w : walls) {
            CollisionManager.addCollidable(w);
        }

        for (Building building : buildings) {
            CollisionManager.addCollidable(building);
        }

    }

    public void drawTrees(Graphics2D g2d) {
        for (Bush bush : bushes) {
            bush.draw(g2d);
        }
        
    }

    public void draw(Graphics2D g2d) {
        shopEntity.drawShop(g2d);

        for (Tree t : trees) {
            t.draw(g2d);
        }

        for (Building b : buildings) {
            b.draw(g2d);
        }

        for (Wall w : walls) {
            w.draw(g2d);
        }

        

        
        // draw rect x,y,w,h
        g2d.setColor(Color.RED);
        // if (GamePanel.debugging) {
        // g2d.drawString("X: " + b.x + " Y: " + b.y + " W: " + b.width + " H: " +
        // b.height, 20,
        // buildings.indexOf(b) * 20 + 40);
        // }
    }

}
