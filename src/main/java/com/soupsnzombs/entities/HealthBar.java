package com.soupsnzombs.entities;

import java.awt.*;

public class HealthBar {
    private int health;
	HealthBar() {
        this.health = 100;
    }
		
	
	public void drawBar(Graphics2D g2d) {

    }

	public void updateBar(int val) {
        this.health = val;
    }
		
}
