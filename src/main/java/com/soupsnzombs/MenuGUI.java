package com.soupsnzombs;

import java.awt.*;

public class MenuGUI {

    public static int selected = 1; // 1 for button1, 2 for button2
    public static boolean play = false;

    public MenuGUI() {
        // Load the images
        // GamePanel.gameState = GamePanel.GameState.MAIN;
    }

    // Draw the menu screen
    public void drawMenu(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.MAIN_MENU) {
            // Draw the background
            if (Images.background != null) {
                g2d.drawImage(Images.background, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
            }

            // Draw buttons centered on the screen
            int buttonWidth = 200;
            int buttonHeight = 100;
            int centerX = GamePanel.screenWidth / 2 - buttonWidth / 2;

            // Draw the "Play" button
            if (Images.playButton != null) {
                g2d.drawImage(Images.playButton, centerX, 450, buttonWidth, buttonHeight, null);
            } else {
                // Fallback: draw a green rectangle with "PLAY" text
                g2d.setColor(Color.GREEN);
                g2d.fillRect(centerX, 300, buttonWidth, buttonHeight);
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                g2d.drawString("PLAY", centerX + 60, 360);
            }

            // Draw the "Credits" button
            if (Images.creditsButton != null) {
                g2d.drawImage(Images.creditsButton, centerX, 300, buttonWidth, buttonHeight, null);
            } else {
                // Fallback: draw a blue rectangle with "CREDITS" text
                g2d.setColor(Color.BLUE);
                g2d.fillRect(centerX, 450, buttonWidth, buttonHeight);
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                g2d.drawString("CREDITS", centerX + 40, 510);
            }

            // Position and dimension of the starting rectangles
            g2d.setColor(Color.GREEN);
            if (selected == 1) {
                g2d.drawRect(centerX, 300, buttonWidth, buttonHeight);
            } else {
                g2d.drawRect(centerX, 450, buttonWidth, buttonHeight);
            }
        }
    }

    public void checkPlay() {
        if (selected == 1 && play) { // Assuming selected == 1 means "Play"
            GamePanel.gameRunning = true; // Transition game state
            GamePanel.gameState = GamePanel.GameState.GAME;
        }
    }
}
