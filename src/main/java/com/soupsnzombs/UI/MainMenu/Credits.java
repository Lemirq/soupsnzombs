package com.soupsnzombs.UI.MainMenu;

import java.awt.Color;
import java.awt.Graphics2D;

import com.soupsnzombs.GamePanel;
import static com.soupsnzombs.utils.FontLoader.font30;
import com.soupsnzombs.utils.Images;

// import com.soupsnzombs.utils.Images;

public class Credits {

    public Credits() {
    }

    public void drawCredits(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.CREDITS) {
            // Draw the background
            g2d.drawImage(Images.credits, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
            // g2d.setColor(Color.DARK_GRAY);
            // g2d.fillRect(GamePanel.screenWidth / 10, GamePanel.screenHeight / 10,
            // GamePanel.screenWidth - GamePanel.screenWidth / 5, GamePanel.screenHeight -
            // GamePanel.screenHeight / 5);
            g2d.setColor(Color.BLACK);
            g2d.setFont(font30);
            g2d.drawString("[B] to Exit", GamePanel.screenWidth / 2 - ("[B] to Exit".length() * 28) / 2,
                    GamePanel.screenHeight / 15);

        }
    }

}
