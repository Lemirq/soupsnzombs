package com.soupsnzombs.UI;

import java.awt.Color;
import java.awt.Graphics2D;

import com.soupsnzombs.entities.Player;
import com.soupsnzombs.entities.zombies.AllZombies;
import com.soupsnzombs.utils.FontLoader;

public class WaveIndicator {
    public void draw(Graphics2D g2d, Player player) {
        g2d.setColor(Color.RED);
        g2d.setFont(FontLoader.font30);
        if (AllZombies.zombies.isEmpty()) {


            if (AllZombies.zombieSpawnDelay < 0) {
                AllZombies.zombieSpawnDelay = 3;
            }
            AllZombies.waveTimer.start();

            if (AllZombies.zombieSpawnDelay == 0) {
                AllZombies.waveNumber++;
                AllZombies.spawnZombies(player);
                AllZombies.zombieSpawnDelay = -1;
            }

            if (AllZombies.zombieSpawnDelay > 0) {
                int nextWave = AllZombies.waveNumber+1;
                g2d.drawString(
                        "WAVE #" + nextWave + " STARTING IN: " + AllZombies.zombieSpawnDelay + " sec", 20,
                        50);
            }

        } else
            g2d.drawString(String.format("WAVE #%d", AllZombies.waveNumber), 20, 50);

    }
}
