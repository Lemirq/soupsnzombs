package com.soupsnzombs.utils;

import java.awt.Rectangle;

/**
 * This class is a custom rectangle class that extends the Rectangle class from
 * the java.awt package. It adds a few more methods to the Rectangle class.
 */
public class CRectangle extends Rectangle {
    public CRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean collidesWith(CRectangle other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }
}
