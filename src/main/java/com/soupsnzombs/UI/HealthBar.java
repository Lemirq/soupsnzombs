package com.soupsnzombs.UI;

import java.awt.*;

public class HealthBar {
    // Constants for the health bar dimensions and position
    private static final int BAR_WIDTH = 400;
    private static final int BAR_HEIGHT = 50;
    private int value;

    public HealthBar(int value) {
        this.value = value;

    }

    // Setter to update health
    public void update(int value) {
        this.value = value;
    }

    public void draw(Graphics2D g2d) {
    }
}