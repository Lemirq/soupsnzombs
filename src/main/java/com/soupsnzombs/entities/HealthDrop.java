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
    int[] healthVals = { 10, 20, 50 };
    int[] spawnLocationsX = {-98, 458, 687, -345, 899, 1000 , 780, 1100, 1500};
    int[] spawnLocationsY = {-45, 715, 862, 911, 49, 1000, 920, 1000, 900};
    boolean visible;
    boolean animation;
    Timer respawnTimer, animationTimer;
    int yPos; // for animating purposes
    Random rand = new Random();

    /**
     * Constructor
     * @param x x-position of health drop
     * @param y y-position of helath drop
     * @param cooldown cooldown time for health drop respawn
     */
    public HealthDrop(int x, int y, int cooldown) {
        super(x, y, 50, 50);
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

    /**
     * draws animation text for health pickup amount
     * @param g2d passes in g2d object for rednering
     */
    public void animatePickUp(Graphics g2d) {
        animationTimer.start();
        g2d.setColor(Color.GREEN);
        //g2d.drawString("+" + healthIncreaseVal + " HP", this.x + GamePanel.offsetX - 24, this.y + GamePanel.offsetY - 6 - yPos);
        g2d.drawString("+" + healthIncreaseVal + " HP", this.x + GamePanel.offsetX - 24, this.y + GamePanel.offsetY - 6 - yPos);
        //System.out.println("drew the msg " + yPos);
    }

    /**
     * draws health drop
     * @param g2d passes in Graphics2D object for rendeirng
     */
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
            case 10:
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

    /**
     * gets the bounds of the healthdrop
     * @return returns the bounds
     */
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }

    /**
     * getter method for healthDropVal
     * @return health drop val
     */
    public int getHealthDropVal() {
        return healthIncreaseVal;
    }

    /**
     * sets new spawn locaiton for when health drop is collected
     */
    /*
    public void setNewLocation(){
        this.x = spawnLocationsX[rand.nextInt(0,spawnLocationsX.length)];
        this.y = spawnLocationsY[rand.nextInt(0,spawnLocationsY.length)];
    }
     */

    /**
     * changes the heal type for when health drop is collected
     */
    public void changeHealType(){
        this.healthIncreaseVal = healthVals[rand.nextInt(0, healthVals.length)];
    }

    /**
     * sets visible to true
     * @param state passes in the state
     */
    public void setVisible(boolean state) {
        this.visible = state;
    }

    /**
     * sets the animation to the state
     * @param state
     */
    public void setAnimation(boolean state) {
        this.animation = state;
    }

    /**
     * used to reset the respawn timer
     */
    public void startRespawnTimer() {
        respawnTimer.start();
    }

    /**
     * @return returns true for visible and false for not visible
     */
    public boolean isVisible() {
        return visible;
    }
}