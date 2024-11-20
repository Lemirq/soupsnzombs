// package com.soupsnzombs;

// import java.awt.Graphics2D;

// public class MenuGUI {
//     enum MenuState {
//         MAIN, OPTIONS, GAME, PAUSE, GAMEOVER
//     }

//     // draw menu screen with play and leaderboard buttons
//     public void drawMenu(Graphics2D g2d) {
//         // draw menu screen
//         g2d.drawString("main menu", 100, 100);
//         // g2d.drawImage(Images.gameMenu, 0, 0, GamePanel.screenWidth,
//         // GamePanel.screenHeight, null);
//     }
// }

package com.soupsnzombs;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MenuGUI {
    enum MenuState {
        MAIN, OPTIONS, GAME, PAUSE, GAMEOVER
    }

    private BufferedImage backgroundImage;
    public MenuGUI() {
        // Load images using the static loadImage method
        backgroundImage = loadImage("src/main/resources/bg.png");
        // soupImage = loadImage("src/main/resources/soup.png");
    }

    // Static method to load images
    static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "An image failed to load: " + filename, "Error", JOptionPane.ERROR_MESSAGE);
        }
        // DEBUG
        if (img == null) {
            System.out.println("Failed to load image: " + filename);
        } else {
            System.out.printf("Loaded image '%s': w=%d, h=%d%n", filename, img.getWidth(), img.getHeight());
        }
        return img;
    }

    // Draw the menu screen
    public void drawMenu(Graphics2D g2d) {
        // Draw the background
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, GamePanel.screenWidth, GamePanel.screenHeight, null);
        }

        // Draw buttons
        g2d.setColor(Color.GREEN);
        g2d.fillRect(250, 400, 150, 50);        // "Play" button
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("PLAY", 285, 435);

        g2d.setColor(Color.BLUE);
        g2d.fillRect(250, 500, 150, 50);        // "Credits" button
        g2d.setColor(Color.BLACK);
        g2d.drawString("CREDITS", 265, 535);

    }
}
