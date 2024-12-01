package com.soupsnzombs.entities.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.utils.CollisionManager;

public class AllZombies {
    public static ArrayList<Zombie> zombies = new ArrayList<>();

    public void addZombie(Zombie z) {
        zombies.add(z);
    }

    public AllZombies() {
        zombies.add(new Zombie(500, 10));
        // zombies.add(new Zombie(100, 100));
        // zombies.add(new Zombie(200, 200));
        // zombies.add(new Zombie(300, 300));
        // zombies.add(new Zombie(400, 400));
        // zombies.add(new Zombie(500, 500));
        // for (Zombie z : zombies) {
        // mgr.addCollidable(z);
        // }
        for (Zombie z : zombies) {
            CollisionManager.addCollidable(z);
        }
    }

    public void draw(Graphics2D g2d, Player player) {
        for (Zombie z : zombies) {
            if (!z.alive) {
                zombies.remove(z);
                break;
            }
            z.chasePlayer(player);
            z.draw(g2d);
            // draw rect x,y,w,h

            if (GamePanel.debugging) {
                g2d.setColor(Color.RED);
                g2d.drawString("X: " + z.x + " Y: " + z.y + " W: " + z.width + " H: " +
                        z.height, GamePanel.screenWidth - 300,
                        zombies.indexOf(z) * 20 + 500);
            }

        }

    }
}
