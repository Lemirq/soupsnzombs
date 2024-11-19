package com.soupsnzombs;

import java.awt.Graphics2D;

public class MenuGUI {
    enum MenuState {
        MAIN, OPTIONS, GAME, PAUSE, GAMEOVER
    }

    // draw menu screen with play and leaderboard buttons
    public void drawMenu(Graphics2D g2d) {
        // draw menu screen
        g2d.drawString("main menu", 100, 100);
        // g2d.drawImage(Images.gameMenu, 0, 0, GamePanel.screenWidth,
        // GamePanel.screenHeight, null);
    }
}
