package com.soupsnzombs.UI.MainMenu;
import com.soupsnzombs.GamePanel;
import java.awt.*;
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.ArrayList;
import java.util.HashMap;
// import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.soupsnzombs.utils.FontLoader;
import com.soupsnzombs.utils.Leaderboard;

// import com.soupsnzombs.utils.Images;

public class Scores {

    HashMap<String, Integer> leaderboardMap = new HashMap<>();

    public Scores() {
    }

    public void drawScores(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.SCORES) {
            // Optional background drawing
            // g2d.drawImage(Images.scoresbg, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
            // g2d.setColor(Color.DARK_GRAY);
            // g2d.fillRect(GamePanel.screenWidth / 10, GamePanel.screenHeight / 10, GamePanel.screenWidth - GamePanel.screenWidth / 5, GamePanel.screenHeight - GamePanel.screenHeight / 5);
    
            g2d.setFont(FontLoader.font80);
            String instructionText = "High Scores - P to EXIT";
            g2d.setColor(Color.ORANGE);
            g2d.drawString(instructionText, 107, 117);
            g2d.setColor(Color.RED);
            g2d.drawString(instructionText, 105, 115);
    
            leaderboardMap = Leaderboard.readScores();
                 
            Iterator hmIterator = leaderboardMap.entrySet().iterator();
            
            int yPosition = 200; 
            
            int count = 0;

            g2d.setFont(FontLoader.font40);

            while (hmIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry) hmIterator.next();
                int marks = ((int) mapElement.getValue() + 10);
                count++;
                
                String scoreText = count + ". " + mapElement.getKey() + " : " + marks;
                g2d.setColor(Color.CYAN);
                g2d.drawString(scoreText, 402, yPosition+2);
                g2d.setColor(Color.BLUE);
                g2d.drawString(scoreText, 400, yPosition);
                
                yPosition += 70; 
            }
        }
    }
    

    


    
}
