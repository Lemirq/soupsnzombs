package com.soupsnzombs.UI;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.GamePanel.GameState;
import com.soupsnzombs.utils.Images;

public class Shop {
    public static boolean shop = false;

    public Shop() {
    }

    public void drawShop(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.SHOP) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(GamePanel.screenWidth / 10, GamePanel.screenHeight / 10, GamePanel.screenWidth - GamePanel.screenWidth / 5, GamePanel.screenHeight - GamePanel.screenHeight / 5);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            g2d.drawString("SHOP (unfinished)", GamePanel.screenWidth / 2 - ("SHOP (unfinished)".length() * 25) / 2, GamePanel.screenHeight / 2 + 15
);

        }
    }
    

    public void checkShop() {
        if (shop) {
            GamePanel.gameState = GamePanel.GameState.SHOP;
        }else{
            GamePanel.gameState = GamePanel.GameState.GAME;
        }
    }


    
}
