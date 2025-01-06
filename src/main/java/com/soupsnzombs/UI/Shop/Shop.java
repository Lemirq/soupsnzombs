/*
 * NOTE
 * fix image folder
 * fix background image
 * write functions for buttons
 * minor: bottom right corner bug with selection
 * minor: aesthetics
 */

package com.soupsnzombs.UI.Shop;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.utils.FontLoader;
import com.soupsnzombs.utils.Images;

public class Shop {
    public static boolean open = false;

    public static final String[][] shopLayout = {
            { "Machine gun", "Semi-auto", "Sniper" },
            { "Milk", "Soup", "Exit" }

    };
    public static int cursorRow = 0;
    public static int cursorCol = 0;
    public static int paddingWidth = 20;
    public static final int optionWidth = GamePanel.screenWidth / 3 - paddingWidth,
            optionHeight = GamePanel.screenHeight / 3 - paddingWidth; // hardcoded button sizes
    public static final int OPTION_X = (GamePanel.screenWidth
            - (shopLayout[0].length * (optionWidth + paddingWidth) - paddingWidth)) / 2;
    public static final int OPTION_Y = (GamePanel.screenHeight
            - (shopLayout.length * (optionHeight + paddingWidth) - paddingWidth)) / 2;

    public void drawShop(Graphics2D g2d) {
        if (GamePanel.gameState == GamePanel.GameState.SHOP) {
            // Draw the background
            if (Images.shopBackground != null) {
                g2d.drawImage(Images.shopBackground, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
            }

            // if (Images.tempImage != null) {
            // g2d.drawImage(Images.tempImage, 0, 0, Images.playButton.getWidth(),
            // Images.tempImage.getHeight(), null);
            // }

            // Draw title
            g2d.setFont(FontLoader.font200);
            String title = "SHOP";
            FontMetrics fm = g2d.getFontMetrics();
            int titleX = (GamePanel.screenWidth - fm.stringWidth(title)) / 2;
            int titleY = GamePanel.screenHeight / 5;
            g2d.setColor(new Color(4, 71, 22));// dark green
            g2d.drawString(title, titleX + 5, titleY + 4);
            g2d.setColor(Color.WHITE);
            g2d.drawString(title, titleX, titleY);

            // Draw cash
            String cashTitle = "cash: ";
            int cashX = GamePanel.screenWidth - GamePanel.screenWidth / 4;
            int cashY = GamePanel.screenHeight / 5;
            g2d.setFont(FontLoader.font60);
            g2d.setColor(new Color(4, 71, 22));// dark green
            g2d.drawString(cashTitle + Player.money, cashX + 3, cashY + 3);
            g2d.setColor(Color.white);
            g2d.drawString(cashTitle + Player.money, cashX, cashY);

        }
    }

    public void drawOptions(Graphics2D g2d) {
        g2d.setFont(FontLoader.font30);
        g2d.setBackground(Color.BLACK);
        g2d.setColor(Color.LIGHT_GRAY);

        for (int row = 0; row < shopLayout.length; row++) {
            for (int col = 0; col < shopLayout[row].length; col++) {
                int x = OPTION_X + col * (optionWidth + paddingWidth);
                int y = OPTION_Y + row * (optionHeight + paddingWidth) + GamePanel.screenHeight / 15;

                // Draw button background
                if (row == cursorRow && col == cursorCol) {
                    g2d.setColor(new Color(64, 64, 64, 200)); // Dark gray
                } else {
                    g2d.setColor(new Color(0, 0, 0, 200)); // Black
                }
                g2d.fillRect(x, y, optionWidth, optionHeight);

                // Draw border
                if (shopLayout[row][col] == "Exit") {
                    g2d.setColor(Color.red);
                } else {
                    g2d.setColor(new Color(4, 71, 22, 200));// dark green
                }
                g2d.setStroke(new BasicStroke(5));
                g2d.drawRect(x, y, optionWidth, optionHeight);
                g2d.setStroke(new BasicStroke(1)); // reset to default

                // Draw image
                if (Images.tempImage != null) {
                    int imageHeight = (int) (optionHeight * 0.75);
                    int imageWidth = optionWidth - 2 * paddingWidth;
                    int imageX = x + paddingWidth;
                    int imageY = y + paddingWidth;
                    g2d.drawImage(Images.tempImage, imageX, imageY, imageWidth, imageHeight, null);
                }

                // Draw text
                String textToDraw = shopLayout[row][col];
                g2d.setColor(Color.WHITE);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (optionWidth - fm.stringWidth(textToDraw)) / 2;
                int textY = y + paddingWidth - 5 + (int) (optionHeight * 0.85) + fm.getAscent() / 2;
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

        String selectedOption = shopLayout[cursorRow][cursorCol];

        switch (selectedOption) {
            case "Machine gun":
                System.out.println("Purchased Machine gun!");
                // Add logic
                break;
            case "Semi-auto":
                System.out.println("Purchased Semi-auto!");
                // Add logic
                break;
            case "Sniper":
                System.out.println("Purchased Sniper!");
                // Add logic
                break;
            case "Milk":
                System.out.println("Purchased Milk!");
                // Add logic
                break;
            case "Soup":
                System.out.println("Purchased Soup!");
                // Add logic
                break;
            case "Exit":
                System.out.println("Exiting shop.");
                open = false;
                GamePanel.gameState = GamePanel.GameState.GAME;
                break;
            default:
                System.out.println("Unknown selection.");
                break;
        }

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
