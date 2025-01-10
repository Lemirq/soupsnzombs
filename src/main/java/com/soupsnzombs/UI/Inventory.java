package com.soupsnzombs.UI;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Gun;
import com.soupsnzombs.utils.FontLoader;

public class Inventory {
    public void draw(Graphics2D g2d, GamePanel game, Gun gun) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(7));
        g2d.setFont(FontLoader.font20);
        g2d.drawString("Current Gun: ", game.getWidth() - 175, game.getHeight() - 150);
        g2d.drawRect(game.getWidth() - 120, game.getHeight() - 130, 80, 80);
        if (gun.getDamage() != 0)
            gun.getDrop().drawInventoryVersion(g2d, game.getWidth() - 108, game.getHeight() - 160);
    }
}
