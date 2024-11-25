package com.soupsnzombs.entities;

import java.awt.Rectangle;

public abstract class Entity extends Rectangle {
    int health;
    int movementSpeed;

    public Entity(int x, int y, int w, int h, int health, int movementSpeed) {
        super(x, y, w, h);
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

    public boolean collidesWith(Rectangle other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }

}