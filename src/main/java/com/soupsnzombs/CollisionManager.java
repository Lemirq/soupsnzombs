package com.soupsnzombs;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.soupsnzombs.buildings.GenericBuilding;

public class CollisionManager {
    private List<GenericBuilding> collidables;

    public CollisionManager() {
        collidables = new ArrayList<>();
    }

    public void addCollidable(GenericBuilding b) {
        collidables.add(b);
    }

    public boolean isColliding(Rectangle rect) {
        for (GenericBuilding collidable : collidables) {
            if (collidable.getBounds().intersects(rect)) {
                return true;
            }
        }
        return false;
    }
}