package com.soupsnzombs;

import java.awt.Rectangle;

public abstract class Entity extends Rectangle {
    int position, positionY;
    int width, height;
    int health;
    int movementSpeed;

    // methods to check intersection
    public boolean intersects(Entity e) {
        return this.intersects(e);
    }

    public boolean contains(Entity e) {
        return this.contains(e);
    }





}
