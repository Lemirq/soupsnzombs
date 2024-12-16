package com.soupsnzombs.entities;

import java.awt.Rectangle;

public abstract class Entity extends Rectangle {
    public int health;
    public double speed;
    public boolean alive = true;

    public Entity(int x, int y, int w, int h, int health, double speed) {
        super(x, y, w, h);
        this.health = health;
        this.speed = speed;
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
