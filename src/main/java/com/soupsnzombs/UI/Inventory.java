package com.soupsnzombs.UI;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Gun;
import com.soupsnzombs.utils.FontLoader;

public class Inventory {
    public void draw(Graphics2D g2d, Gun gun) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(7));
        g2d.setFont(FontLoader.font20);
        // g2d.drawString("Current Gun: ", game.getWidth() - 175, game.getHeight() -
        // 150);
        g2d.drawRoundRect(GamePanel.screenWidth - 120, GamePanel.screenHeight - 130, 80, 80, 10, 10);
        if (gun.getDamage() != 0)
            gun.getDrop().drawInventoryVersion(g2d, GamePanel.screenWidth - 108, GamePanel.screenHeight - 160);
    }
}
