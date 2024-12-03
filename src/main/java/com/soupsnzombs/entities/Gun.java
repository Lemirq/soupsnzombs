package com.soupsnzombs.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import com.soupsnzombs.GamePanel;

public class Gun extends Entity {
    private int damage;
    private int fireRate;
    private int range;
    private int ammo;
    private int maxAmmo;
    private int reloadTime;
    private int bulletSpeed;
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    // boolean released ; //for the basic gun, you need to spam space; perhaps for
    // automatics, this can be removed.

    public Gun(int damage, int fireRate, int range, int ammo, int maxAmmo, int reloadTime, int bulletSpeed) {
        super(0, 0, 0, 0, 0, 0);
        this.damage = damage;
        this.fireRate = fireRate;
        this.range = range;
        this.ammo = ammo;
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.bulletSpeed = bulletSpeed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public void reload() {
        this.ammo = this.maxAmmo;
    }

    public void shoot() {
        this.ammo--;
    }

    public boolean canShoot() {
        return this.ammo > 0;
    }

    public boolean needsReload() {
        return this.ammo == 0;
    }

    public void shootBullet(Player player) {
        int gunMouthX = 0;
        int gunMouthY = 0;

        switch (GamePanel.direction) {
            case UP:
                gunMouthX = player.x + player.width / 2;
                gunMouthY = player.y + player.height / 2 + 20;
                break;

            case LEFT:
                gunMouthX = player.x - 50;
                gunMouthY = player.y + player.height / 2;
                break;

            case RIGHT:
                gunMouthX = player.x + 50;
                gunMouthY = (player.y + player.height / 2) - 10;
                break;

            case DOWN:
                gunMouthY = player.y + 20;
                break;
        }

        // gunMouthX = player.x;
        // gunMouthY = player.y + 20;

        // Create a new bullet object

        // calculate x and y position of the gun mouth
        // int collisionX = player.getBounds().x;
        // int collisionY = player.getBounds().y + 20;
        Bullet newBullet = new Bullet(gunMouthX, gunMouthY, GamePanel.direction, this.range);
        // Add the bullet to the list of bullets
        bullets.add(newBullet);

        System.out.println(bullets.size());
    }

    public void updateBullets() {
        // Update bullets
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            if (Math.abs(bullet.getX() - bullet.getStartX()) > bullet.getMaxDistance() ||
                    Math.abs(bullet.getY() - bullet.getStartY()) > bullet.getMaxDistance()) {
                System.out.println("Removing " + bullet);
                iterator.remove();
            }
        }
    }

    public void draw(Graphics2D g2d, Player player) {
        int centerXPlayer = GamePanel.screenWidth / 2 - width / 2;
        int centerYPlayer = GamePanel.screenHeight / 2 - height / 2;
        if (GamePanel.debugging) {
            g2d.setColor(Color.red);
            g2d.drawRect(centerXPlayer, centerYPlayer, 20, 20);
        }

        // Draw the bullets
        for (Bullet bullet : bullets) {
            bullet.draw(g2d);

            if (GamePanel.debugging) {
                for (int i = 0; i < bullets.size(); i++) {
                    g2d.drawString("Bullet Amt: " + bullets.size(), GamePanel.screenWidth - 300, 600);
                    g2d.drawString("Bullet " + i + " X: " + bullets.get(i).x + " Y: " + bullets.get(i).y,
                            GamePanel.screenWidth - 300, i * 20 + 700);
                }
            }
        }
    }
}
