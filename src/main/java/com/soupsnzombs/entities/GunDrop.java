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
    int swapTime;
    Timer swapTimer;
    boolean collectable;
    public GunDrop(int x, int y, Gun gunToBeStored, Color c) {
        super(x, y, 30, 30);
        gunInfo = gunToBeStored;
        color = c;
        
        
         
        gracePeriod = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    gracePeriod.stop();
                    interactable = true;
            }
        });
        

        swapTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    swapTime+= 5;
                    if (swapTime == 100) collectable = true;
            }
        });
        startGrace();
    }

    public void startGrace() {
        swapTime = 0;
        interactable = false;
        collectable = false;
        gracePeriod.start();
    }

    public void draw(Graphics2D g2d, Player player) {
        g2d.setColor(this.color);
        g2d.fillOval(this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width, this.height);
        if (isSwapTimerRunning()) drawSwapBar(g2d, this.x + GamePanel.offsetX, this.y-10+GamePanel.offsetY);
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

    
    public void drawSwapBar(Graphics2D g2d, int x, int y) {
        int barWidth = 50;
        int barHeight = 10;
        int swapBarWidth = (int) ((swapTime / 100.0) * barWidth);

        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x-10, y-2, swapBarWidth, barHeight, 10, 10); 
     }

     public void startSwapTimer() {
        swapTimer.start();
     }  

     public void stopSwapTimer() {
        swapTimer.stop();
        swapTime = 0;
     }  

     public boolean isCollectable() {
        return collectable;
     }

     public boolean isSwapTimerRunning() {
        return swapTimer.isRunning();
     }
}
