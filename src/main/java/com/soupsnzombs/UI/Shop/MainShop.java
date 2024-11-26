package com.soupsnzombs.UI.Shop;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

public class MainShop {
    public static boolean open = false;

    public MainShop() {
    }

    public void drawShop(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.SHOP) {
            // Draw the background
            if (Images.shopBackground != null) {
                g2d.drawImage(Images.shopBackground, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
            }
            
            //note - finnish instruction screen and shop buttons next

            //Next draw buttons using button images
//             g2d.setColor(new Color(128, 64, 0));
//             g2d.fillRect(0,0,GamePanel.screenWidth,GamePanel.screenHeight);
//             g2d.setColor(new Color(153, 102, 51));
//             g2d.fillRect(GamePanel.screenWidth / 10, GamePanel.screenHeight / 10, GamePanel.screenWidth - GamePanel.screenWidth / 5, GamePanel.screenHeight - GamePanel.screenHeight / 5);
//             g2d.setColor(Color.WHITE);
//             g2d.setFont(new Font("Arial", Font.BOLD, 50));
//             g2d.drawString("SHOP (unfinished)", GamePanel.screenWidth / 2 - ("SHOP (unfinished)".length() * 25) / 2, GamePanel.screenHeight / 2 + 15
// );

        }
    }
    

    public void checkShop() {
        if (open) {
            GamePanel.gameState = GamePanel.GameState.SHOP;
        }else{
            GamePanel.gameState = GamePanel.GameState.GAME;
        }
    }


    
}
