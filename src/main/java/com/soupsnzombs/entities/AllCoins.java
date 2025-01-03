package com.soupsnzombs.entities;
import java.awt.*;
import java.util.ArrayList;

public class AllCoins {
    public static ArrayList<Coin> coins = new ArrayList<>();

    public void draw(Graphics2D g2d) {
        for (Coin coin : coins) {
            coin.draw(g2d);
        }
    }
}
