package com.soupsnzombs.UI.MainMenu;
import com.soupsnzombs.GamePanel;
import java.awt.*;
import com.soupsnzombs.utils.Images;

// import com.soupsnzombs.utils.Images;

public class Scores {

    public Scores() {
    }

    public void drawScores(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.SCORES) {
            // Draw the background
            g2d.drawImage(Images.scoresbg, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
            // g2d.setColor(Color.DARK_GRAY);
            // g2d.fillRect(GamePanel.screenWidth / 10, GamePanel.screenHeight / 10, GamePanel.screenWidth - GamePanel.screenWidth / 5, GamePanel.screenHeight - GamePanel.screenHeight / 5);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Lucida Console", Font.BOLD, 45));
            g2d.drawString("SCORES (unfinished) p to exit", GamePanel.screenWidth / 2 - ("SCORES (unfinished) p to exit".length() * 28) / 2, GamePanel.screenHeight / 4);

        }
    }


    
}