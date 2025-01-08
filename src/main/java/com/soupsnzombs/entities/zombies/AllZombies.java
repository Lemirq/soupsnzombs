package com.soupsnzombs.entities.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import com.soupsnzombs.GamePanel;
import com.soupsnzombs.buildings.Bush;
import com.soupsnzombs.entities.Coin;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.entities.zombies.Zombie.ZombieType;
import com.soupsnzombs.utils.CollisionManager;
import static com.soupsnzombs.entities.AllCoins.coins;

public class AllZombies {
    public static ArrayList<Zombie> zombies = new ArrayList<>();
    private Zombie zombie;

    public void addZombie(Zombie z) {
        zombies.add(z);
    }

    public AllZombies() {
        // random zombie spawn x between -1000,1000 and y between -1000,1000
        for (int i = 0; i < 1; i++) {
            int x = (int) (Math.random() * 2000 - 1000);
            int y = (int) (Math.random() * 2000 - 1000);
            zombies.add(new Zombie(x, y, ZombieType.SMALL));
        }

        System.out.println("zombie size: " + zombies.size());
        for (Zombie z : zombies) {
            CollisionManager.addCollidable(z);
        }
    }

    public void draw(Graphics2D g2d, Player player) {
        for (Zombie z : zombies) {
            if (!z.alive) {
                Player.score += z.pointsDropped;
                zombies.remove(z);
                coins.add(new Coin(z.x + 5, z.y + 5, 10, 10));
                break;
            }
            z.draw(g2d, player);
            z.chasePlayer(player, g2d);
            // z.chasePlayerLegacy(player);
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
