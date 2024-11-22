package com.soupsnzombs;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionManager {
    private ArrayList<Rectangle> collidables = new ArrayList<>();

    public void addCollidable(Rectangle b) {
        collidables.add(b);
    }

    public boolean isColliding(Rectangle rect) {
        for (Rectangle collidable : collidables) {
            if (collidable.intersects(rect)) {
                return true;
            }
        }
        return false;
    }
}