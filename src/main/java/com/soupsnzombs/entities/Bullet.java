package com.soupsnzombs.entities;

import java.awt.*;

import java.awt.Rectangle;

public class Bullet extends Rectangle {
    private int width, height, speed = 10;

    Bullet(int w, int h) {
        super(0, 0, w, h);
        width = w;
        height = h;
    }

    void update(int direction) {
        switch (direction) {
            // 1: right, -1: left, 0: up, 2: down
            case 1:
                x += speed;
                break;
            case -1:
                x -= speed;
                break;
            case 0:
                y += speed;
                break;
            case 2:
                y -= speed;
                break;
        }
    }

    void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillOval(x, y, width, height);
    }
}
