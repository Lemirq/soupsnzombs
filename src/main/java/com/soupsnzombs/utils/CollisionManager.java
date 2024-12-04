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

    public static boolean isColliding(Rectangle rect, ArrayList<Rectangle> c) {
        for (Rectangle collidable : c) {
            if (collidable.intersects(rect)) {
                // write name of all things that are collidable, with true and false beside each
                System.out.println("Colliding with: " + collidable);
                return true;
            }
        }
        return false;
    }
}