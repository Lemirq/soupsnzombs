package com.soupsnzombs.buildings;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.CollisionManager;

import java.awt.*;
import java.util.ArrayList;

public class AllWalls {
    public ArrayList<Wall> walls = new ArrayList<>();

    public void addWall(Wall w) {
        walls.add(w);
    }

    public AllWalls() {
        walls.add(new Wall(-400, 40, 300, 50));
        walls.add(new Wall(100, 100, 50, 50));
        walls.add(new Wall(200, 200, 50, 50));
        walls.add(new Wall(300, 300, 50, 50));
        walls.add(new Wall(400, 400, 50, 50));
        walls.add(new Wall(500, 500, 50, 50));
        for (Wall w : walls) {
            CollisionManager.addCollidable(w);
        }
    }

    public void draw(Graphics2D g2d) {
        for (Wall w : walls) {
            w.draw(g2d);
            // draw rect x,y,w,h
            g2d.setColor(Color.RED);
            if (GamePanel.debugging) {
                g2d.drawString("X: " + w.x + " Y: " + w.y + " W: " + w.width + " H: " + w.height, 20,
                        walls.indexOf(w) * 20 + 40);
            }
        }

    }
}