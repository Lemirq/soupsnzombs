package com.soupsnzombs.UI.Shop;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.FontLoader;
import com.soupsnzombs.utils.Images;

public class Shop {
    public static boolean open = false;

    public static final String[][] shopLayout = {
            { "Machine gun", "semi-auto", "sniper" },
            { "milk", "soup" }

    };
    public static int cursorRow = 0;
    public static int cursorCol = 0;
    public static final int OPTION_X = 75, OPTION_Y = 275;
    public static final int optionWidth = 100, optionHeight = 100;

    public void drawShop(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.SHOP) {
            // Draw the background
            if (Images.shopBackground != null) {
                g2d.drawImage(Images.shopBackground, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
            }

        }
    }

    public void drawOptions(Graphics2D g2d) {
        g2d.setFont(FontLoader.font20);
        g2d.setBackground(Color.BLACK);
        g2d.setColor(Color.LIGHT_GRAY);
        for (int row = 0; row < shopLayout.length; row++) {
            for (int col = 0; col < shopLayout[row].length; col++) {
                int optionWidthToUse = optionWidth;
                int optionHeightToUse = optionHeight;

                int x = OPTION_X + col * (optionWidthToUse + 10);
                int y = OPTION_Y + row * (optionHeightToUse + 10);

                if (row == cursorRow && col == cursorCol) {
                    g2d.setColor(Color.darkGray);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.fillRect(x, y, optionWidthToUse, optionHeightToUse);
                g2d.drawRect(x, y, optionWidthToUse, optionHeightToUse);
                FontMetrics fm = g2d.getFontMetrics();
                String textToDraw = shopLayout[row][col];
                int textX = x + (optionWidthToUse - fm.stringWidth(textToDraw)) / 2;
                int textY = y + (optionHeightToUse + fm.getAscent()) / 2 - 5;
                g2d.setColor(new Color(0, 102, 0));
                g2d.drawString(textToDraw, textX+1, textY+1);
                g2d.setColor(Color.white);
                g2d.drawString(textToDraw, textX, textY);
            }
        }

    }

    public static void selectUp(GamePanel game) {
        cursorRow = Math.max(0, cursorRow - 1);
        game.repaint();
        game.revalidate();
    }

    public static void selectDown(GamePanel game) {
        cursorRow = Math.min(shopLayout.length - 1, cursorRow + 1);
        game.repaint();
        game.revalidate();
    }

    public static void selectLeft(GamePanel game) {
        cursorCol = Math.max(0, cursorCol - 1);
        game.repaint();
        game.revalidate();
    }

    public static void selectRight(GamePanel game) {
        cursorCol = Math.min(shopLayout[cursorRow].length - 1,
                cursorCol + 1);
        game.repaint();
        game.revalidate();
    }

    public static void selectEnter(GamePanel game) {
        //write method to select weapon and health
        game.repaint();
        game.revalidate();
    }

    public void checkShop() {
        if (open) {
            GamePanel.gameState = GamePanel.GameState.SHOP;
        } else {
            GamePanel.gameState = GamePanel.GameState.GAME;
        }
    }

}
