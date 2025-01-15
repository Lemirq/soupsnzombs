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

public class AllZombies {
    //public static int FOVWidth = GamePanel.screenWidth + 50;
    //public static int FOVHeight = GamePanel.screenHeight + 50;
    public static ArrayList<Zombie> zombies = new ArrayList<>();
    public static int waveNumber = 1;
    public static final int spawnRadius = 1500;
    public static final Random random = new Random();
    public static Timer waveTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            zombieSpawnDelay--;
        }
    });

    public static int zombieSpawnDelay = 3;

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

    public static void spawnZombies(Player player) {
        //Rectangle FOV = new Rectangle((player.x + GamePanel.offsetX) - FOVWidth / 2, (player.y + GamePanel.offsetY) - FOVHeight / 2, FOVWidth, FOVHeight);
        int x, y;
        boolean validSpawn = true;
        int numberOfZombies = waveNumber + 25;
        for (int i = 0; i < numberOfZombies; i++) {
            int maxAttempts = 1000; // Maximum number of attempts to find a valid spawn location
            int attempts = 0;

            do {
                x = random.nextInt(spawnRadius * 2) - spawnRadius + (player.x + GamePanel.offsetX);
                        //random.nextInt(spawnRadius * 2) - spawnRadius + (player.x + GamePanel.offsetX);
                y = random.nextInt(spawnRadius * 2) - spawnRadius + (player.y + GamePanel.offsetY);
                        //random.nextInt(spawnRadius * 2) - spawnRadius + (player.y + GamePanel.offsetY);


                for (Zombie zombie : zombies) {

                    /*
                    if (isInPlayerFOV(player, x, y)) {
                        validSpawn = false;
                        continue;

                    }

                    if (FOV.contains(zombie)) {
                        validSpawn = false;
                        continue;
                    }

                     */

                    for (Building building : buildings) {
                        if (building.getBounds().contains(zombie.getBounds())) {
                            validSpawn = false;
                            break;
                        }
                    }

                    for (Tree t : trees) {
                        if (t.getBounds().contains(zombie.getBounds())) {
                            validSpawn = false;
                            break;
                        }
                    }

                    for (Wall w : walls) {
                        if (w.getBounds().contains(zombie.getBounds())) {
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
                // if (waveNumber <= 9) {
                // zombies.add(new Zombie(x, y, ZombieType.DEFAULT));
                // } else {
                if (waveNumber % 5 == 0) {
                    numberOfZombies = waveNumber / 5;
                    zombies.add(new Zombie(x, y, ZombieType.BOSS));
                }
                else {
                    numberOfZombies = waveNumber + 25;
                    int kindOfZombie = random.nextInt(4) + 1;

                switch (kindOfZombie) {
                    case 1:
                        zombies.add(new Zombie(x, y, ZombieType.DEFAULT));
                        break;
                    case 2:
                        zombies.add(new Zombie(x, y, ZombieType.DEFAULT));
                        break;
                    case 3:
                        zombies.add(new Zombie(x, y, ZombieType.FAT));
                        break;
                    case 4:
                        zombies.add(new Zombie(x, y, ZombieType.SMALL));
                        break;
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


    /*
    public static boolean isInPlayerFOV(Player player, int x, int y) {
        Rectangle FOV = new Rectangle((player.x + GamePanel.offsetX) - FOVWidth / 2,
                (player.y + GamePanel.offsetY) - FOVHeight / 2, FOVWidth, FOVHeight);
        return FOV.contains(x, y);
    }
     */

    public void draw(Graphics2D g2d, Player player) {
        Iterator<Zombie> zombieIterator = zombies.iterator();
        while (zombieIterator.hasNext()) {
            Zombie z = zombieIterator.next();
            if (!z.alive) {
                Player.score += z.pointsDropped;

                switch (Zombie.zombieType) {
                    case DEFAULT:
                        int dropCoin = random.nextInt(2) + 1;
                        if (dropCoin == 1) {
                            coins.add(new Coin(z.x + 5, z.y + 5, 10, 10));
                        }
                        break;

                    case SMALL:
                        int dropCoin2 = random.nextInt(6) + 1;
                        if (dropCoin2 == 1) {
                            coins.add(new Coin(z.x + 5, z.y + 5, 10, 10));
                        }
                        break;

                    case FAT:
                        coins.add(new Coin(z.x + 5, z.y + 5, 10, 10));
                        break;

                    case BOSS:
                        coins.add(new Coin(z.x, z.y + 5, 10, 10));
                        coins.add(new Coin(z.x + 5, z.y, 10, 10));
                        coins.add(new Coin(z.x + 2, z.y, 10, 10));
                        coins.add(new Coin(z.x, z.y + 2, 10, 10));
                        coins.add(new Coin(z.x + 2, z.y + 5, 10, 10));
                        break;
                }
                zombieIterator.remove();
                break;

            } else {
                z.draw(g2d, player);
                z.chasePlayer(player, g2d); // only follow player if they are within 500 blocks.

                if (GamePanel.debugging) {
                    g2d.setColor(Color.RED);
                    g2d.drawString("X: " + z.x + " Y: " + z.y + " W: " + z.width + " H: " +
                            z.height, GamePanel.screenWidth - 300, zombies.indexOf(z) * 20 + 500);
                }

            }

        }

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
