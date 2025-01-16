/*
 * NOTE
 * fix image folder
 * fix background image
 * write functions for buttons
 * minor: bottom right corner bug with selection
 * minor: aesthetics
 */

package com.soupsnzombs.UI.Shop;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Gun;
import com.soupsnzombs.entities.GunDrop;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.utils.FontLoader;
import static com.soupsnzombs.utils.FontLoader.font60;
import com.soupsnzombs.utils.Images;
import static com.soupsnzombs.utils.Images.SMGImage;
import static com.soupsnzombs.utils.Images.bulletRange;
import static com.soupsnzombs.utils.Images.damage;
import static com.soupsnzombs.utils.Images.energyDrink;
import static com.soupsnzombs.utils.Images.semiAutoImage;
import static com.soupsnzombs.utils.Images.sniperImage;

public class Shop {
    public static boolean open = false;

    public static final String[][] shopLayout = {
            { "Machine Gun", "Semi-Auto", "Sniper" },
            { "Energy Drink", "Damage", "Range" }

    };
    public static final ArrayList<BufferedImage> shopImages = new ArrayList<>();

    private static int cost = 0;
    public static int cursorRow = 0;
    public static int cursorCol = 0;
    public static int paddingWidth = 20;
    public static final int optionWidth = GamePanel.screenWidth / 3 - paddingWidth,
            optionHeight = GamePanel.screenHeight / 3 - paddingWidth; // hardcoded button sizes
    public static final int OPTION_X = (GamePanel.screenWidth
            - (shopLayout[0].length * (optionWidth + paddingWidth) - paddingWidth)) / 2;
    public static final int OPTION_Y = (GamePanel.screenHeight
            - (shopLayout.length * (optionHeight + paddingWidth) - paddingWidth)) / 2;

    /**
     * draws shop interface on the screen (background, title, coins)
     * 
     * @param g2d passes in g2d for rendering
     */
    public void drawShop(Graphics2D g2d) {
        
        shopImages.add(SMGImage);
        shopImages.add(semiAutoImage);
        shopImages.add(sniperImage);
        shopImages.add(energyDrink);
        shopImages.add(damage);
        shopImages.add(bulletRange);

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

            // Draw exit message
            String exitMessage = "Press [Y] to Exit Shop";
            int exitX = GamePanel.screenWidth - 925;
            int exitY = GamePanel.screenHeight - 50;
            g2d.setFont(FontLoader.font60);
            g2d.setColor(new Color(4, 71, 22));// dark green
            g2d.drawString(exitMessage, exitX + 5, exitY + 4);
            g2d.setColor(Color.WHITE);
            g2d.drawString(exitMessage, exitX, exitY);

            // Draw coins
            String coinsTitle = "Coins: ";
            int coinsX = GamePanel.screenWidth - GamePanel.screenWidth / 4;
            int coinsY = GamePanel.screenHeight / 5;
            g2d.setFont(FontLoader.font60);
            g2d.setColor(new Color(4, 71, 22));// dark green
            g2d.drawString(coinsTitle + Player.money, coinsX + 3, coinsY + 3);
            g2d.setColor(Color.white);
            g2d.drawString(coinsTitle + Player.money, coinsX, coinsY);

        }
    }

    /**
     * draws shop options/buttons
     * changes the option/button colour when hovered over by the selection
     * 
     * @param g2d passes in g2d for rendering
     */
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
                    if (shopLayout[row][col] == "Machine Gun") {
                        g2d.drawImage(shopImages.get(0), imageX, imageY, imageWidth, imageHeight, null);
                    } else if (shopLayout[row][col] == "Semi-Auto") {
                        g2d.drawImage(shopImages.get(1), imageX, imageY, imageWidth, imageHeight, null);
                    } else if (shopLayout[row][col] == "Sniper") {
                        g2d.drawImage(shopImages.get(2), imageX, imageY, imageWidth, imageHeight, null);
                    } else if (shopLayout[row][col] == "Energy Drink") {
                        g2d.drawImage(shopImages.get(3), imageX, imageY, imageWidth, imageHeight, null);
                    } else if (shopLayout[row][col] == "Damage") {
                        g2d.drawImage(shopImages.get(4), imageX, imageY, imageWidth, imageHeight, null);
                    } else if (shopLayout[row][col] == "Range") {
                        g2d.drawImage(shopImages.get(5), imageX, imageY, imageWidth, imageHeight, null);
                    }

                    // draw cost
                    g2d.setFont(font60);
                    g2d.setColor(new Color(4, 71, 22));// dark green
                    g2d.drawString("Cost: " + cost, 93, (GamePanel.screenHeight / 5)+3);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("Cost: " + cost, 90, GamePanel.screenHeight / 5);
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

    public void getCost() {
        String selectedOption = shopLayout[cursorRow][cursorCol];

        switch (selectedOption) {
            case "Machine Gun":
                cost = 5;
                break;
            case "Semi-Auto":
                cost = 15;
                break;
            case "Sniper":
                cost = 20;
                break;
            case "Energy Drink":
                cost = 2;
                break;
            case "Damage":
                cost = 5;
                break;
            case "Range":
                cost = 5;
                break;
            default:
                cost = 0;
                break;
        }
    }

    /**
     * Moves the selected key up one row on the keyboard
     * Makes sure the selection stays in bounds
     * 
     * @param game passes in the gamepanel for UI updates
     */
    public static void selectUp(GamePanel game) {
        cursorRow = Math.max(0, cursorRow - 1);
        game.repaint();
        game.revalidate();
    }

    /**
     * Moves the selected key down one row on the keyboard
     * Makes sure the selection stays in bounds
     * 
     * @param game passes in the gamepanel for UI updates
     */
    public static void selectDown(GamePanel game) {
        cursorRow = Math.min(shopLayout.length - 1, cursorRow + 1);
        game.repaint();
        game.revalidate();
    }

    /**
     * Moves the selected key left one on the keyboard
     * Makes sure the selection stays in bounds
     * 
     * @param game passes in the gamepanel for UI updates
     */
    public static void selectLeft(GamePanel game) {
        cursorCol = Math.max(0, cursorCol - 1);
        game.repaint();
        game.revalidate();
    }

    /**
     * Moves the selected key right one on the keyboard
     * Makes sure the selection stays in bounds
     * 
     * @param game passes in the gamepanel for UI updates
     */
    public static void selectRight(GamePanel game) {
        cursorCol = Math.min(shopLayout[cursorRow].length - 1,
                cursorCol + 1);
        game.repaint();
        game.revalidate();
    }

    /**
     * inputs the keyboard selection based on which key is selected
     * space & alphanumeric keys are written in the text field
     * backspace is used to delete letters/spaces in the text field
     * 
     * @param game passes in the gamepanel for UI updates
     */
    public static void selectEnter(GamePanel game) {

        String selectedOption = shopLayout[cursorRow][cursorCol];

        switch (selectedOption) {
            case "Machine Gun":
                if (Player.money >= 5) {
                    GamePanel.gunDrops.add(new GunDrop((int)game.getPlayer().getX(), (int)game.getPlayer().getY(), new Gun(5, 100, 100, 0,  5, 1, SMGImage)));
                    Player.money-=5;
                }
                else System.out.println("Not enough money to purchase machine gun."); 
                // Add logic
                break;
            case "Semi-Auto":
                if (Player.money >= 15) {
                    GamePanel.gunDrops.add(new GunDrop((int)game.getPlayer().getX(), (int)game.getPlayer().getY(), new Gun(20, 200, 200, 0, 5, 0, semiAutoImage)));
                    Player.money-=15;
                }
            else System.out.println("Not enough money to purchase sniper."); 
                break;
            case "Sniper":
                if (Player.money >= 20) {
                    GamePanel.gunDrops.add(new GunDrop((int)game.getPlayer().getX(), (int)game.getPlayer().getY(), new Gun(80, 500, 300, 0, 1, -1, sniperImage)));
                    Player.money-=20;
                }
                else System.out.println("Not enough money to purchase sniper."); 
                // Add logic
                break;
            case "Exit":
                System.out.println("Exiting shop.");
                open = false;
                GamePanel.gameState = GamePanel.GameState.GAME;
                break;
            case "Damage":
                if (Player.money >= 5 && GamePanel.player.getGun().getDamage() != 0) {
                    System.out.println("Purchased Damage!");
                    GamePanel.player.getGun().setDamage(GamePanel.player.getGun().getDamage()+5);
                    Player.money-=5;
                }
                else System.out.println("player is holding no gun");
                
                break;
            case "Range":
                if (Player.money >= 5 && GamePanel.player.getGun().getDamage() != 0) {
                    System.out.println("Purchased Range!");
                    GamePanel.player.getGun().setRange(GamePanel.player.getGun().getRange()+50);
                    Player.money-=5;
                }
                else System.out.println("player is holding no gun");
                
                break;
            case "Energy Drink":
                if (Player.money >= 2 && GamePanel.player.getHealth() < 100) {
                    GamePanel.player.increaseHealth(5);
                    Player.money-=2;
                }
                else System.out.println("player has max health");
                
                
                break;
            default:
                System.out.println("Unknown selection.");
                break;
            }
            
        game.repaint();
        game.revalidate();
    }

    /**
     * checks if the shop is closed
     */
    public void checkShop() {
        if (open) {
            GamePanel.gameState = GamePanel.GameState.SHOP;
        } else {
            GamePanel.gameState = GamePanel.GameState.GAME;
        }
    }

}
