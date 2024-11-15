package com.soupsnzombs;

public class HealthBar {
    private int health;
    HealthBar() {
        this.health = 100;
    }

    void updateBar(int val) {
        this.health = val;
    }

    void draw() {

    }
}
