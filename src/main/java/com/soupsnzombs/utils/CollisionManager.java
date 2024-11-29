package com.soupsnzombs.utils;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionManager {
    public static ArrayList<Rectangle> collidables = new ArrayList<>();

    public static void addCollidable(Rectangle b) {
        collidables.add(b);
    }

    public static ArrayList<Rectangle> getCollidables() {
        return collidables;
    }
    public static boolean isColliding(Rectangle rect, ArrayList<Rectangle>c) {
        for (Rectangle collidable : c) {
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