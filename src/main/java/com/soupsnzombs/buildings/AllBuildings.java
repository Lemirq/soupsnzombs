package com.soupsnzombs.buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.soupsnzombs.utils.CollisionManager;

public class AllBuildings {
    public static ArrayList<Building> buildings = new ArrayList<>();
    public static ArrayList<Wall> walls = new ArrayList<>();
    public static ArrayList<Tree> trees = new ArrayList<>();
    public static ArrayList<Bush> bushes = new ArrayList<>();
    ShopBuilding shopEntity = new ShopBuilding(500, -200, 400, 200);

    public void addBuilding(Building b) {
        buildings.add(b);
    }

    public AllBuildings() {
        buildings.add(shopEntity);

        walls.add(new Wall(-400, 40, 300, 50));
        walls.add(new Wall(100, 100, 50, 50));
        walls.add(new Wall(200, 200, 50, 50));
        walls.add(new Wall(300, 300, 50, 50));
        walls.add(new Wall(400, 400, 50, 50));
        walls.add(new Wall(500, 500, 50, 50));

        // walls.add(new Wall(-1000, 500, 1000, 1000));
        // trees.add(new Tree (-1000, 1500, 1000, 1000));

        trees.add(new Tree(350, 100, 150, 150));
        trees.add(new Tree(-250, -120, 150, 150));

        bushes.add(new Bush(250, 100, 75, 75));

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

    public void draw(Graphics2D g2d) {
        shopEntity.drawShop(g2d);

        for (Building b : buildings) {
            b.draw(g2d);
        }

        for (Wall w : walls) {
            w.draw(g2d);
        }

        for (Tree t : trees) {
            t.draw(g2d);
        }

        for (Bush bush : bushes) {
            bush.draw(g2d);
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
