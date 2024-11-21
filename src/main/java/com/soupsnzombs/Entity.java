package com.soupsnzombs;

import java.awt.Rectangle;

public abstract class Entity extends Rectangle {
    int width, height;
    int health;
    int movementSpeed;

    public Entity(int w, int h, int health, int movementSpeed) {
        super(w, h);
        this.width = w;
        this.height = h;
        this.health = health;
        this.movementSpeed = movementSpeed;
    }

    // methods to check intersection
    public boolean intersects(Entity e) {
        return this.intersects(e);
    }

    public boolean contains(Entity e) {
        return this.contains(e);
    }

}
