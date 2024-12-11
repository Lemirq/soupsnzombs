package com.soupsnzombs.entities;
import java.awt.*;

import com.soupsnzombs.GamePanel;

public class GunDrop extends Rectangle {
    Gun gunInfo;
    Color color;
    public GunDrop(int x, int y, Gun gunToBeStored, Color c) {
        super(x, y, 30, 30);
        this.gunInfo = gunToBeStored;
        this.color = c;
    }

    public void draw(Graphics2D g2d, Player player) {
        g2d.setColor(this.color);
        g2d.fillOval(this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width, this.height);

        if (player.intersects(this)) {
            GamePanel.gunDrops.remove(this);
            player.dropGun();
            player.setGun(gunInfo);
            
        }
        
    }

    
}
