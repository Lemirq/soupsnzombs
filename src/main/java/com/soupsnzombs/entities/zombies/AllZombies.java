package com.soupsnzombs.entities.zombies;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Coin;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.entities.zombies.Zombie.ZombieType;
import com.soupsnzombs.utils.CollisionManager;

import javax.swing.*;

import static com.soupsnzombs.entities.AllCoins.coins;

public class AllZombies {
    public static ArrayList<Zombie> zombies = new ArrayList<>();
    private int waveNumber = 1;
    private int spawnRadius = 1000;
    private Random random = new Random();
    private Timer waveTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            seconds++;
        }
    });

    int seconds = 0;

    public void addZombie(Zombie z) {
        zombies.add(z);
    }

    public AllZombies() {
        System.out.println("Zombie size: " + zombies.size());
        for (Zombie z : zombies) {
            CollisionManager.addCollidable(z);
        }
    }

    private void spawnZombies(Player player) {
        int numberOfZombies = waveNumber + 2;
        for (int i = 0; i < numberOfZombies; i++) {
            int x = player.x + random.nextInt(spawnRadius * 2) - spawnRadius;
            int y = player.y + random.nextInt(spawnRadius * 2) - spawnRadius;
            zombies.add(new Zombie(x, y, ZombieType.SMALL));
        }
        System.out.println("Wave " + waveNumber + numberOfZombies + "zombies spawned");
    }

    public void draw(Graphics2D g2d, Player player) {
        Iterator<Zombie> zombieIterator = zombies.iterator();
        while (zombieIterator.hasNext()) {
            Zombie z = zombieIterator.next();
            if (!z.alive) {
                Player.score += z.pointsDropped;
                zombieIterator.remove();
                coins.add(new Coin(z.x + 5, z.y + 5, 10, 10));
                break;
            } else {
                z.draw(g2d, player);
                z.chasePlayer(player, g2d);

                if (GamePanel.debugging) {
                    g2d.setColor(Color.RED);
                    g2d.drawString("X: " + z.x + " Y: " + z.y + " W: " + z.width + " H: " +
                                    z.height, GamePanel.screenWidth - 300,
                            zombies.indexOf(z) * 20 + 500);
                }


                // z.chasePlayerLegacy(player);
                // draw rect x,y,w,h

            }

        }

        if (zombies.isEmpty()) {
            if (seconds > 3) {
                seconds = 0;
            }
            waveTimer.start();
            if (seconds == 3) {
                waveNumber++;
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Arial", Font.PLAIN, 100));
                g2d.drawString("WARNING, WAVE STARTING IN: " + seconds, 350, 300);
                spawnZombies(player);
                }
        }




    }
}

