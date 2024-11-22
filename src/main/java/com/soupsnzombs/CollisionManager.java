package com.soupsnzombs;

import java.util.ArrayList;

public class CollisionManager {
    private ArrayList<CRectangle> collidables = new ArrayList<>();

    public void addCollidable(CRectangle b) {
        collidables.add(b);
    }

    public boolean isColliding(CRectangle rect) {
        // print collidable details
        for (CRectangle collidable : collidables) {
            System.out.println("Collidable: " + collidable.x + " " + collidable.y + " " + collidable.width + " "
                    + collidable.height);
            if (collidable.collidesWith(rect)) {
                System.out.println("Collision detected with: " + collidable);
                return true;
            }
        }
        System.out.println("Returning false");
        return false;
    }
}