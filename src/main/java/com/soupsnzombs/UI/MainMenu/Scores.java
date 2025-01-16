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
import java.util.Map;

import com.soupsnzombs.utils.FontLoader;
import com.soupsnzombs.utils.Leaderboard;

// import com.soupsnzombs.utils.Images;

public class Scores {

    HashMap<String, Integer> leaderboardMap = new HashMap<>();

    public Scores() {
    }

    /**
     * draws the score screen components
     * 
     * @param g2d passes in the Graphics2D object for rendering
     */
    public void drawScores(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.SCORES) {
            // Optional background drawing
            // g2d.drawImage(Images.scoresbg, 0, 0, GamePanel.screenWidth,
            // GamePanel.screenHeight, null);
            // g2d.setColor(Color.DARK_GRAY);
            // g2d.fillRect(GamePanel.screenWidth / 10, GamePanel.screenHeight / 10,
            // GamePanel.screenWidth - GamePanel.screenWidth / 5, GamePanel.screenHeight -
            // GamePanel.screenHeight / 5);

            g2d.setFont(FontLoader.font80);
            String instructionText = "High Scores - B to EXIT";
            g2d.setColor(Color.ORANGE);
            FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
            int textWidth = metrics.stringWidth(instructionText);
            int xPosition = (GamePanel.screenWidth - textWidth) / 2;
            g2d.drawString(instructionText, xPosition + 2, 117);
            g2d.setColor(Color.RED);
            g2d.drawString(instructionText, xPosition, 115);

            leaderboardMap = Leaderboard.readScores();

            g2d.setFont(FontLoader.font40);
            int yPosition = 200; // Starting Y position for the leaderboard entries
            for (Map.Entry<String, Integer> entry : leaderboardMap.entrySet()) {
                String scoreText = entry.getKey() + ": " + entry.getValue();
                textWidth = metrics.stringWidth(scoreText);
                xPosition = (GamePanel.screenWidth - textWidth) / 2;
                g2d.setColor(Color.CYAN);
                g2d.drawString(scoreText, xPosition + 2, yPosition + 2);
                g2d.setColor(Color.BLUE);
                g2d.drawString(scoreText, xPosition, yPosition);
                yPosition += 50; // Adjust the spacing between entries as needed
            }
        }
    }
}
