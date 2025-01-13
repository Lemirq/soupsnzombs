package com.soupsnzombs.entities.zombies;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.buildings.*;
import com.soupsnzombs.entities.Coin;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.entities.zombies.Zombie.ZombieType;
import com.soupsnzombs.utils.CollisionManager;

import javax.swing.*;

import static com.soupsnzombs.buildings.AllBuildings.*;
import static com.soupsnzombs.entities.AllCoins.coins;
import static com.soupsnzombs.utils.FontLoader.font30;

public class AllZombies {
    int FOVWidth = GamePanel.screenWidth + 50;
    int FOVHeight = GamePanel.screenHeight + 50;
    public static ArrayList<Zombie> zombies = new ArrayList<>();
    public static int waveNumber = 1;
    private final int spawnRadius = 1500;
    private final Random random = new Random();
    private final Timer waveTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            seconds--;
        }
    });

    int seconds = 3;

    public void addZombie(Zombie z) {
        zombies.add(z);
    }

    public void clear() {
        zombies.clear();
    }

    public AllZombies() {
        System.out.println("Zombie size: " + zombies.size());
        for (Zombie z : zombies) {
            CollisionManager.addCollidable(z);
        }
    }

    private void spawnZombies(Player player) {
        Rectangle FOV = new Rectangle((player.x + GamePanel.offsetX) - FOVWidth / 2,
                (player.y + GamePanel.offsetY) - FOVHeight / 2, FOVWidth, FOVHeight);
        int x, y;
        boolean validSpawn = true;
        int numberOfZombies = waveNumber + 2;
        for (int i = 0; i < numberOfZombies; i++) {
            int maxAttempts = 1000; // Maximum number of attempts to find a valid spawn location
            int attempts = 0;

            do {
                x = random.nextInt(spawnRadius * 2) - spawnRadius + (player.x + GamePanel.offsetX);
                y = random.nextInt(spawnRadius * 2) - spawnRadius + (player.y + GamePanel.offsetY);

                for (Zombie zombie : zombies) {
                    if (isInPlayerFOV(player, x, y)) {
                        validSpawn = false;
                        continue;
                    }

                    if (FOV.contains(zombie)) {
                        validSpawn = false;
                        continue;
                    }

                    for (Building building : buildings) {
                        if (building.contains(zombie)) {
                            validSpawn = false;
                            break;
                        }
                    }

                    for (Tree t : trees) {
                        if (t.contains(zombie)) {
                            validSpawn = false;
                            break;
                        }
                    }

                    for (Bush b : bushes) {
                        if (b.contains(zombie)) {
                            validSpawn = false;
                            break;
                        }
                    }

                    for (Wall w : walls) {
                        if (w.contains(zombie)) {
                            validSpawn = false;
                            break;
                        }
                    }

                }

                attempts++;
                if (attempts >= maxAttempts) {
                    if (GamePanel.debugging) {
                        System.out.println("Could not find a valid spawn location after " + maxAttempts + " attempts.");
                    }
                    break;
                }

            } while (!validSpawn);
            if (validSpawn) {
                if (waveNumber <= 9) {
                    zombies.add(new Zombie(x, y, ZombieType.DEFAULT));
                } else {
                    int kindOfZombie = random.nextInt(4) + 1;
                    switch (kindOfZombie) {
                        case 1:
                            zombies.add(new Zombie(x, y, ZombieType.DEFAULT));
                        case 2:
                            zombies.add(new Zombie(x, y, ZombieType.DEFAULT));
                        case 3:
                            zombies.add(new Zombie(x, y, ZombieType.FAT));
                        case 4:
                            zombies.add(new Zombie(x, y, ZombieType.SMALL));
                    }
                }

            } else {
                if (GamePanel.debugging) {
                    System.out.println("Failed to spawn a zombie.");
                }
            }
        }
        System.out.println("Wave " + waveNumber + ": " + numberOfZombies + " zombies spawned");
    }

    private boolean isInPlayerFOV(Player player, int x, int y) {
        Rectangle FOV = new Rectangle((player.x + GamePanel.offsetX) - FOVWidth / 2,
                (player.y + GamePanel.offsetY) - FOVHeight / 2, FOVWidth, FOVHeight);
        return FOV.contains(x, y);
    }

    public void draw(Graphics2D g2d, Player player) {
        g2d.drawRect((player.x + GamePanel.offsetX) - FOVWidth / 2, (player.y + GamePanel.offsetY) - FOVHeight / 2,
                FOVWidth, FOVHeight);
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
                            z.height, GamePanel.screenWidth - 300, zombies.indexOf(z) * 20 + 500);
                }

                // z.chasePlayerLegacy(player);
                // draw rect x,y,w,h

            }

        }

        g2d.setColor(Color.RED);
        g2d.setFont(font30);
        if (zombies.isEmpty()) {
            if (seconds > 0) {

                g2d.drawString("WAVE #" + waveNumber + " STARTING IN: " + seconds + " sec", 20, 50);
            }

            if (seconds < 0) {
                seconds = 3;
            }
            waveTimer.start();

            if (seconds == 0) {
                waveNumber++;
                spawnZombies(player);
            }

        } else
            g2d.drawString(String.format("WAVE #%d", waveNumber - 1), 20, 50);

        /*
         * 
         * //spawn one random zombie for testing
         * int x = player.x + random.nextInt(spawnRadius * 2) - spawnRadius;
         * int y = player.y + random.nextInt(spawnRadius * 2) - spawnRadius;
         * zombies.add(new Zombie(x, y, ZombieType.SMALL));
         * 
         */

    }
}
