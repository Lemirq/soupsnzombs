package com.soupsnzombs.UI.MainMenu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

// import com.soupsnzombs.utils.Images;

public class Instructions {

    public Instructions() {
    }

    public void drawInstructions(Graphics2D g2d) {
            // Draw the background
            g2d.drawImage(Images.instructions, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
            // g2d.setColor(Color.DARK_GRAY);
            // g2d.fillRect(GamePanel.screenWidth / 10, GamePanel.screenHeight / 10, GamePanel.screenWidth - GamePanel.screenWidth / 5, GamePanel.screenHeight - GamePanel.screenHeight / 5);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Lucida Console", Font.BOLD, 45));
            g2d.drawString("Instructions B to exit", GamePanel.screenWidth / 2 - ("Instructions B to exit".length() * 28) / 2, GamePanel.screenHeight / 15);

    }


    
}
