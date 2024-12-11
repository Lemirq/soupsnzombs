package com.soupsnzombs.entities;
import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;

import com.soupsnzombs.GamePanel;

public class GunDrop extends Rectangle {
    Gun gunInfo;
    Color color;
    Boolean interactable;
    Timer gracePeriod; 
    public GunDrop(int x, int y, Gun gunToBeStored, Color c) {
        super(x, y, 30, 30);
        this.gunInfo = gunToBeStored;
        this.color = c;
        interactable = false;
        gracePeriod = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gracePeriod.stop();
                interactable = true;
            }
        });
        gracePeriod.start();
    }

    public void draw(Graphics2D g2d, Player player) {
        g2d.setColor(this.color);
        g2d.fillOval(this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width, this.height);
    }

    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }

    public Gun getGunInfo() {
        return this.gunInfo;
    }

    public boolean isInteractable() {
        return interactable;
    }
}
