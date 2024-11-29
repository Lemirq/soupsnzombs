package com.soupsnzombs.UI.MainMenu;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.Images;

public class MenuGUI {
    public static int selected = 0;

    public static boolean pressed = false;

    public MenuGUI() {
        // Load the images
        // GamePanel.gameState = GamePanel.GameState.MAIN;
    }

    public void drawMenu(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.MAIN_MENU) {
            // Draw the background
            if (Images.background != null) {
                g2d.drawImage(Images.background, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
            }

            // Define the spacing between buttons
            int buttonSpacing = 20; // Adjust the spacing as needed

            // Calculate the starting x position for the first button
            int totalWidth = Images.playButton.getWidth() + Images.scoresButton.getWidth()
                    + Images.creditsButton.getWidth()
                    + 2 * buttonSpacing;
            int startX = (GamePanel.screenWidth - totalWidth) / 2;
            int yPosition = 600;

            int playButtonX = startX;
            int scoresButtonX = startX + Images.playButton.getWidth() + buttonSpacing;
            int creditsButtonX = startX + Images.playButton.getWidth() + Images.scoresButton.getWidth()
                    + 2 * buttonSpacing;

            // Draw the "Play" button
            if (Images.playButton != null) {
                g2d.drawImage(Images.playButton, playButtonX, yPosition, Images.playButton.getWidth(),
                        Images.playButton.getHeight(), null);
            }

            // Draw the "Scores" button
            if (Images.scoresButton != null) {
                g2d.drawImage(Images.scoresButton, scoresButtonX, yPosition, Images.scoresButton.getWidth(),
                        Images.scoresButton.getHeight(), null);
            }

            // Draw the "Credits" button
            if (Images.creditsButton != null) {
                g2d.drawImage(Images.creditsButton, creditsButtonX, yPosition, Images.creditsButton.getWidth(),
                        Images.creditsButton.getHeight(), null);
            }

            // Draw arrows around the selected button
            if (Images.arrowImage != null) {
                int arrowWidth = Images.arrowImage.getWidth();
                int arrowHeight = Images.arrowImage.getHeight();
                int arrowX, arrowYTop, arrowYBottom;

                if (selected == 0) {
                    arrowX = playButtonX + (Images.playButton.getWidth() - arrowWidth) / 2;
                    arrowYTop = yPosition - arrowHeight - 5; // Adjust the spacing as needed
                    arrowYBottom = yPosition + Images.playButton.getHeight() + 5; // Adjust the spacing as needed
                } else if (selected == 1) {
                    arrowX = scoresButtonX + (Images.scoresButton.getWidth() - arrowWidth) / 2;
                    arrowYTop = yPosition - arrowHeight - 5; // Adjust the spacing as needed
                    arrowYBottom = yPosition + Images.scoresButton.getHeight() + 5; // Adjust the spacing as needed
                } else {
                    arrowX = creditsButtonX + (Images.creditsButton.getWidth() - arrowWidth) / 2;
                    arrowYTop = yPosition - arrowHeight - 5; // Adjust the spacing as needed
                    arrowYBottom = yPosition + Images.creditsButton.getHeight() + 5; // Adjust the spacing as needed
                }

                g2d.drawImage(Images.arrowImage, arrowX, arrowYTop, null);
                // transform to flip the arrow

                g2d.drawImage(Images.arrowImage, arrowX, arrowYBottom + arrowHeight, arrowWidth, -arrowHeight, null);

            }
        }
    }

    public void checkPlay() {
        if (selected == 0 && pressed) { //selected == 0 means "Play"
            GamePanel.gameState = GamePanel.GameState.INSTRUCTIONS;

        }
    }

    public void checkScores() {
        if (selected == 1 && pressed) { //selected == 1 means "Scores"
            GamePanel.gameState = GamePanel.GameState.SCORES;
            System.out.println(GamePanel.gameState);
        }
    }

    public void checkCredits() {
        if (selected == 2 && pressed) { //selected == 2 means "Instructions"
            GamePanel.gameState = GamePanel.GameState.CREDITS;
            System.out.println(GamePanel.gameState);
        }
    }
}
