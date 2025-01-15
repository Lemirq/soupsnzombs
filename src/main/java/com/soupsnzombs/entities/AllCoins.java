package com.soupsnzombs.entities;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.SoundManager;
import com.soupsnzombs.utils.SoundManager.Sound;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class AllCoins {
    public static ArrayList<Coin> coins = new ArrayList<>();

    public void draw(Graphics2D g2d, Entity player) {
        Iterator<Coin> iterator = coins.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.draw(g2d);

            if (player.intersects(coin)) {
                iterator.remove();
                SoundManager.playSound(Sound.COIN_PICKUP, false);
                Player.money++;
            }

            if (GamePanel.debugging) {
                g2d.setColor(Color.RED);
                g2d.drawString("X: " + coin.x + " Y: " + coin.y + " W: " + coin.width + " H: " +
                        coin.height, GamePanel.screenWidth - 1000,
                        coins.indexOf(coin) * 20 + 500);
            }

        }

    }

    public void clear() {
        coins.clear();
    }
}
