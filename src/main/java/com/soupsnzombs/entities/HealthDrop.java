package com.soupsnzombs.entities;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.*;
import com.soupsnzombs.GamePanel;

public class HealthDrop extends Rectangle {

    int healthIncreaseVal;
    Color color; 
    int[] healthVals = {50, 15, 20};
    boolean visible;
    boolean animation;
    Timer respawnTimer, animationTimer; 
    int yPos; //for animating purposes

    public HealthDrop(int x, int y, int cooldown) {
        super(x, y, 25, 25);
        this.healthIncreaseVal = healthVals[(int)(Math.random() * (healthVals.length - 1) + 1)];
        this.color = new Color(healthIncreaseVal*10, 0, 0);
        this.visible = true;
        this.animation = false;
        this.yPos = 0;

        
        respawnTimer = new Timer(cooldown, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visible = true;
                respawnTimer.stop();
            }
        });
        animationTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yPos += 10;
                if (yPos == 80) {
                    yPos = 0;
                    animation = false;
                    animationTimer.stop();
                }
            }
        });
    }

    public void animatePickUp(Graphics g2d) {
        animationTimer.start();
        g2d.setColor(Color.GREEN);
        g2d.drawString("+" + healthIncreaseVal + " HP", this.x + GamePanel.offsetX-24, this.y + GamePanel.offsetY-6-yPos); 

       
    }

    public void draw(Graphics g2d) {
        
        if (animation) {
            animatePickUp(g2d);
        }
        if (!this.visible) {
            return;
        }
        g2d.setColor(this.color);
        g2d.fillRoundRect(this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width, this.height, 15, 15);
        
    }

    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }

    public int getHealthDropVal() {
        return healthIncreaseVal;
    }

    public void setVisible(boolean state) {
        this.visible = state;
    }

    public void setAnimation(boolean state) {
        this.animation = state;
    }

    public void startRespawnTimer() {
        respawnTimer.start();
    }

    public boolean isVisible() {
        return visible;
    }
}