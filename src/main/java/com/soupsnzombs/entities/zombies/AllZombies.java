package com.soupsnzombs.entities.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import java.util.Iterator;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Bullet;
import com.soupsnzombs.entities.Gun;
import com.soupsnzombs.utils.CollisionManager;

public class AllZombies {
    ArrayList<Zombie> zombies = new ArrayList<>();

    public void addZombie(Zombie z) {
        zombies.add(z);
    }

    public AllZombies(CollisionManager mgr) {
        zombies.add(new Zombie(-10, -10));
        //zombies.add(new Zombie(100, 100));
        //zombies.add(new Zombie(200, 200));
        //zombies.add(new Zombie(300, 300));
        //zombies.add(new Zombie(400, 400));
        //zombies.add(new Zombie(500, 500));
        // for (Zombie z : zombies) {
        // mgr.addCollidable(z);
        // }

    }

    public void draw(Graphics2D g2d, Rectangle player) {
        for (Zombie z : zombies) {
            z.chasePlayer(player);
            z.draw(g2d);
            // draw rect x,y,w,h
            g2d.setColor(Color.RED);
            g2d.drawString("X: " + z.x + " Y: " + z.y + " W: " + z.width + " H: " +
            z.height, GamePanel.screenWidth - 300,
            zombies.indexOf(z) * 20 + 500);
            //System.out.println("Zombie x: " + z.x + " y: " + z.y);
            
        }

        //bullet collision logic, doesn't work because bullet bounds and zombie bounds never intersect if you print out coords
        /*  
        Iterator<Bullet> bulletIterator = Gun.bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Rectangle bulletBounds = bullet.getBounds();

            for (Zombie zombie : zombies) {
                Rectangle zombieBounds = zombie.getBounds();
                // System.out.println("Zombie x: " + zombieBounds.getX() + " y: " + zombieBounds.getY());
                if (bulletBounds.intersects(zombieBounds)) {
                    //System.out.println("Bullet collided with zombie");
                    bulletIterator.remove();
                    // Decrease the zombie's health
                    zombie.takeDamage(10);
                    break;
                }
            }
        }

        */
    }
}
