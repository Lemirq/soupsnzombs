package com.soupsnzombs.UI.MainMenu;

import static com.soupsnzombs.GamePanel.timeLeft;
import static com.soupsnzombs.utils.FontLoader.font20;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.FontLoader;

public class NameSelect {

    public static final String[][] keyboardLayout = {
            { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P" },
            { "A", "S", "D", "F", "G", "H", "J", "K", "L" },
            { "Z", "X", "C", "V", "B", "N", "M" },
            { "Space", "Backspace" }
    };
    public static boolean message = false;
    public static int cursorRow = 0;
    public static int cursorCol = 0;
    public static StringBuilder name = new StringBuilder();
    public static final int keyWidth = 120, keyHeight = 120;
    public static final int largeKeyWidth = 200, largeKeyHeight = 100;
    public static final int KEYBOARD_X = (GamePanel.screenWidth - (keyboardLayout[0].length * (keyWidth + 10) - 10))
            / 2;
    public static final int KEYBOARD_Y = 275;
    public static final int MAX_NAME_LENGTH = 15;
    public static boolean blink;
    public static int time;

    /**
     * Adjusts the cursor position when navigating to "Space" or "Backspace"
     * Makes sure column range is not exeeded
     */
    public static void adjustCursorForSpaceBackspace() {
        if (cursorRow == 3) {
            if (cursorCol < 0) {
                cursorCol = 0;
            } else if (cursorCol > 1) {
                cursorCol = 1;
            }
        }
    }

    /**
     * draws the keyboard layoiut on screen
     * 
     * @param g2d passes in the g2d object for rendering
     */
    public void drawKeyboard(Graphics2D g2d) {
        g2d.setFont(FontLoader.font20);
        g2d.setBackground(Color.BLACK);
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
                    g2d.setColor(Color.darkGray);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.fillRect(x, y, keyWidthToUse, keyHeightToUse);
                g2d.drawRect(x, y, keyWidthToUse, keyHeightToUse);
                FontMetrics fm = g2d.getFontMetrics();
                String textToDraw = keyboardLayout[row][col];
                int textX = x + (keyWidthToUse - fm.stringWidth(textToDraw)) / 2;
                int textY = y + (keyHeightToUse + fm.getAscent()) / 2 - 5;
                g2d.setColor(new Color(0, 102, 0));
                g2d.drawString(textToDraw, textX + 1, textY + 1);
                g2d.setColor(Color.white);
                g2d.drawString(textToDraw, textX, textY);
            }
        }

    }

    /**
     * Draws the current name
     * Draws as blinking cursor
     * 
     * @param g2d passes in the g2d object for rendering
     */
    public void drawName(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(FontLoader.font50);

        time++;

        if (time % 100 == 0) {
            blink = !blink;
        }
        String nameText;

        if (blink) {
            nameText = "Name: " + name.toString() + "|";
            // System.out.println("test1");
        } else {
            nameText = "Name: " + name.toString();
            // System.out.println("test2");
        }

        int boxWidth = 750;
        int boxHeight = 80;
        int padding = 20;

        int boxX = (GamePanel.screenWidth - boxWidth) / 2;
        int boxY = 175;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(boxX - padding, boxY - padding, boxWidth, boxHeight);

        g2d.setColor(Color.RED);
        g2d.drawRect(boxX - padding, boxY - padding, boxWidth, boxHeight);

        g2d.setColor(Color.BLACK);
        g2d.drawString(nameText, boxX, boxY + g2d.getFontMetrics().getAscent());
    }

    /**
     * Draws the keyboard instructions at the top of the screen
     * 
     * @param g2d passes in the g2d object for rendering
     */
    public void drawInstructions(Graphics2D g2d) {
        g2d.setFont(FontLoader.font30);
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        String instructionText = "Enter a name - [B] to Enter/Skip, [C] to select";
        int textWidth = metrics.stringWidth(instructionText);
        int xPosition = (GamePanel.screenWidth - textWidth) / 2;
        g2d.setColor(new Color(0, 102, 0));
        g2d.drawString(instructionText, xPosition+2, 127);
        g2d.setColor(Color.WHITE);
        g2d.drawString(instructionText, xPosition, 125);
        g2d.setColor(Color.RED);
        g2d.drawString(String.valueOf(timeLeft), GamePanel.screenWidth - 250, GamePanel.screenHeight - 685);
    }

    /**
     * Draws name mis-input message
     * 
     * @param g2d passes in the g2d object for rendering
     */
    public void drawRejected(Graphics2D g2d) {
        if (message) {
            g2d.setFont(font20);
            g2d.setColor(Color.RED);
            g2d.drawString("The name contains inappropriate language. Please choose a different name.", 50, 50);
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
        adjustCursorForSpaceBackspace();
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
        cursorRow = Math.min(keyboardLayout.length - 1, cursorRow + 1);
        adjustCursorForSpaceBackspace();
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
        adjustCursorForSpaceBackspace();
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
        cursorCol = Math.min(keyboardLayout[cursorRow].length - 1,
                cursorCol + 1);
        adjustCursorForSpaceBackspace();
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
        if ("Space".equals(keyboardLayout[cursorRow][cursorCol])) {
            if (name.length() < MAX_NAME_LENGTH) {
                name.append(" ");
            }
        } else if ("Backspace"
                .equals(keyboardLayout[cursorRow][cursorCol])) {
            if (name.length() > 0) {
                name.deleteCharAt(name.length() - 1);
            }
        } else {
            if (name.length() < MAX_NAME_LENGTH) {
                name
                        .append(keyboardLayout[cursorRow][cursorCol]);
            }
        }
        game.repaint();
        game.revalidate();
    }

}
