package com.soupsnzombs.entities.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.CollisionManager;

public class AllZombies {
    ArrayList<Zombie> zombies = new ArrayList<>();

    public void addZombie(Zombie b) {
        zombies.add(b);
    }

    public AllZombies(CollisionManager mgr) {
        zombies.add(new Zombie(-10, -10));
        zombies.add(new Zombie(100, 100));
        zombies.add(new Zombie(200, 200));
        zombies.add(new Zombie(300, 300));
        zombies.add(new Zombie(400, 400));
        zombies.add(new Zombie(500, 500));
        // for (Zombie b : zombies) {
        // mgr.addCollidable(b);
        // }

    }

    public void draw(Graphics2D g2d, Rectangle player) {
        for (Zombie b : zombies) {
            b.chasePlayer(player);
            b.draw(g2d);
            // draw rect x,y,w,h
            g2d.setColor(Color.RED);
            g2d.drawString("X: " + b.x + " Y: " + b.y + " W: " + b.width + " H: " +
                    b.height, GamePanel.screenWidth - 300,
                    zombies.indexOf(b) * 20 + 500);
        }

    }
}
