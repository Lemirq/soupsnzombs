package com.soupsnzombs.entities;
import com.soupsnzombs.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class AllCoins {
    public static ArrayList<Coin> coins = new ArrayList<>();

    public void draw(Graphics2D g2d) {
        for (Coin coin : coins) {
            coin.draw(g2d);

            if (GamePanel.debugging) {
                g2d.setColor(Color.RED);
                g2d.drawString("X: " + coin.x + " Y: " + coin.y + " W: " + coin.width + " H: " +
                                coin.height, GamePanel.screenWidth - 1000,
                        coins.indexOf(coin) * 20 + 500);
            }
        }


    }
}
