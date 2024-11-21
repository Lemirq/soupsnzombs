package com.soupsnzombs;

import java.awt.Rectangle;

public class Entity extends Rectangle {
    int health;

    // methods to check intersection
    public boolean intersects(Entity e) {
        return this.intersects(e);
    }

    public boolean contains(Entity e) {
        return this.contains(e);
    }
}
