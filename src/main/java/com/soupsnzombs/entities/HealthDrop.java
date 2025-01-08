package com.soupsnzombs.entities;

import java.awt.*;
import javax.swing.Timer;
import java.awt.event.*;
import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;
import java.util.Random;

public class HealthDrop extends Rectangle {

    int healthIncreaseVal;
    Color color;
    int[] healthVals = { 15, 20, 50 };
    int[] spawnLocationsX = {-98, 458, 687, -345, 899};
    int[] spawnLocationsY = {-45, 715, 862, 911, 49};
    boolean visible;
    boolean animation;
    Timer respawnTimer, animationTimer;
    int yPos; // for animating purposes
    Random rand = new Random();

    public HealthDrop(int x, int y, int cooldown) {
        super(x, y, 70, 60);
        this.healthIncreaseVal = healthVals[rand.nextInt(0, healthVals.length)];
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
        animationTimer = new Timer(2, new ActionListener() {
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
        g2d.drawString("+" + healthIncreaseVal + " HP", this.x + GamePanel.offsetX - 24,
                this.y + GamePanel.offsetY - 6 - yPos);

    }

    public void draw(Graphics g2d) {

        if (animation) {
            animatePickUp(g2d);
        }
        if (!this.visible) {
            return;
        }

        // System.out.println("healthVal: " + healthIncreaseVal);
        // for (int i = 0; i < 10; i++){
        //     System.out.println(rand.nextInt(0,3));
        // }

        // System.out.println("x "+this.x + " y " + this.y);

        //draw sprites
        switch (healthIncreaseVal) {
            case 15:
                g2d.drawImage(Images.energyDrink, this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width,
                        this.height, null);
                break;
            case 20:
                g2d.drawImage(Images.milk, this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width,
                        this.height, null);
                break;
            case 50:
                g2d.drawImage(Images.soup, this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width,
                        this.height, null);
                break;
            default:
                // draws heals of different colours
                this.color = new Color(healthIncreaseVal * 10, 0, 0);
                g2d.setColor(this.color);
                g2d.fillRoundRect(this.x + GamePanel.offsetX, this.y + GamePanel.offsetY, this.width, this.height, 15,
                        15);

        }

    }

    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }

    public int getHealthDropVal() {
        return healthIncreaseVal;
    }

    public void setNewLocation(){
        this.x = spawnLocationsX[rand.nextInt(0,spawnLocationsX.length)];
        this.y = spawnLocationsY[rand.nextInt(0,spawnLocationsY.length)];
    }

    public void changeHealType(){
        this.healthIncreaseVal = healthVals[rand.nextInt(0, healthVals.length)];
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