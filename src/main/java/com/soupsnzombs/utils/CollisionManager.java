package com.soupsnzombs.utils;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionManager {
    private ArrayList<Rectangle> collidables = new ArrayList<>();

    public void addCollidable(Rectangle b) {
        collidables.add(b);
    }

    public boolean isColliding(Rectangle rect) {
        // print collidable details
        for (Rectangle collidable : collidables) {
            // System.out.println("Collidable: " + collidable.x + " " + collidable.y + " " +
            // collidable.width + " "
            // + collidable.height);
            if (collidable.intersects(rect)) {
                // System.out.println("Collision detected with: " + collidable);
                return true;
            }
        }
        // System.out.println("Returning false");
        return false;
    }
}