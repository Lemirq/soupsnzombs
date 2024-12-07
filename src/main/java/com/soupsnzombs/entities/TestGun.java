package com.soupsnzombs.entities;
import java.awt.*;

import com.soupsnzombs.GamePanel;

public class TestGun extends Rectangle {
    boolean collided = false;
    int newFireRate;
    int automatic; //-1 = false, 0 = semi, 1 = automatic 
    int newDamage;
    Color color;
    public TestGun(int x, int y, int newDamage, int newFireRate, int automatic, Color c) {
        super(x, y, 30, 30);
        this.newDamage = newDamage;
        this.automatic = automatic;
        this.newFireRate = newFireRate;
        this.color = c;
    }

    public void draw(Graphics2D g2d, Player player) {
        if (!collided) {
            g2d.setColor(this.color);
            g2d.fillOval(this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width, this.height);
        }
        if (player.intersects(this) && !collided) {
            player.setGun(new Gun(this.newDamage, this.newFireRate, 600, 5, 5, 5, 5, this.automatic));
            collided = true;
        }
        
    }
}
