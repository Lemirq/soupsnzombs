package com.soupsnzombs.UI.MainMenu;

import java.awt.*;

public class NameSelect {

    public static final String[][] keyboardLayout = {
        {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
        {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
        {"Z", "X", "C", "V", "B", "N", "M"},
        {"Space", "Backspace"}
    };
    public static int cursorRow = 0;
    public static int cursorCol = 0;
    public static StringBuilder name = new StringBuilder();
    public static final int keyWidth = 60, keyHeight = 60;
    public static final int largeKeyWidth = 120, largeKeyHeight = 60;
    public static final int KEYBOARD_X = 50, KEYBOARD_Y = 200;
    public static final int MAX_NAME_LENGTH = 15;

    public static void adjustCursorForSpaceBackspace() {
        if (cursorRow == 3) {
            if (cursorCol < 0) {
                cursorCol = 0;
            } else if (cursorCol > 1) {
                cursorCol = 1;
            }
        }
    }

    public void drawKeyboard(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        for (int row = 0; row < keyboardLayout.length; row++) {
            for (int col = 0; col < keyboardLayout[row].length; col++) {
                int keyWidthToUse = keyWidth;
                int keyHeightToUse = keyHeight;

                if ("Space".equals(keyboardLayout[row][col]) || "Backspace".equals(keyboardLayout[row][col])) {
                    keyWidthToUse = largeKeyWidth;
                }

                int x = KEYBOARD_X + col * (keyWidthToUse + 10);
                int y = KEYBOARD_Y + row * (keyHeightToUse + 10);

                if (row == cursorRow && col == cursorCol) {
                    g2d.setColor(Color.YELLOW);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                }
                g2d.fillRect(x, y, keyWidthToUse, keyHeightToUse);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, keyWidthToUse, keyHeightToUse);
                FontMetrics fm = g2d.getFontMetrics();
                String textToDraw = keyboardLayout[row][col];
                int textX = x + (keyWidthToUse - fm.stringWidth(textToDraw)) / 2;
                int textY = y + (keyHeightToUse + fm.getAscent()) / 2 - 5;
                g2d.drawString(textToDraw, textX, textY);
            }
        }

    }

    public void drawName(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        
        String nameText = "Name: " + name.toString();
        
        int boxX = 50, boxY = 100, padding = 10;
        int boxWidth = 400;
        int boxHeight = 40;
        
        g2d.setColor(Color.WHITE);
        g2d.fillRect(boxX - padding, boxY - padding, boxWidth, boxHeight);
        
        g2d.setColor(Color.BLACK);
        g2d.drawRect(boxX - padding, boxY - padding, boxWidth, boxHeight);
        
        g2d.setColor(Color.BLACK);
        g2d.drawString(nameText, boxX, boxY + g2d.getFontMetrics().getAscent());
    }

    public void drawInstructions(Graphics2D g2d) {
        
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("Arial", Font.PLAIN, 40));
        String instructionText = "Enter a name - Press P to enter";
        g2d.drawString(instructionText, 50, 50); 

        
    }
    
}
