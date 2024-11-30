package com.soupsnzombs;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.GamePanel.GameState;
import com.soupsnzombs.GamePanel.PlayerDir;

import com.soupsnzombs.UI.MainMenu.MenuGUI;
import com.soupsnzombs.UI.MainMenu.NameSelect;
import com.soupsnzombs.UI.MainMenu.Scores;
import com.soupsnzombs.UI.Shop.MainShop;

public class MainFrame extends JFrame {
    GamePanel game;
    Boolean released = true; // trigger for non-automatic guns
    // KeyHandler class to handle key events

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (e.isShiftDown()) {
                GamePanel.MOVE_SPEED = 30;
            } else if (e.isAltDown()) {
                GamePanel.MOVE_SPEED = 1;
            } else {
                GamePanel.MOVE_SPEED = 5;
            }

            switch (key) {
                case KeyEvent.VK_W:
                    GamePanel.upPressed = true;
                    GamePanel.direction = PlayerDir.UP;

                    if (GamePanel.gameState == GameState.NAME_SELECT) {
                        NameSelect.cursorRow = Math.max(0, NameSelect.cursorRow - 1);
                        NameSelect.adjustCursorForSpaceBackspace();
                        game.repaint();
                        game.revalidate();

                    }
                    break;

                case KeyEvent.VK_S:
                    GamePanel.downPressed = true;
                    GamePanel.direction = PlayerDir.DOWN;
                    if (GamePanel.gameState == GameState.NAME_SELECT) {
                        NameSelect.cursorRow = Math.min(NameSelect.keyboardLayout.length - 1, NameSelect.cursorRow + 1);
                        NameSelect.adjustCursorForSpaceBackspace();
                        game.repaint();
                        game.revalidate();
                    }
                    // Add more cases if needed
                    break;
                case KeyEvent.VK_A:

                    // check if game hasnt started, use this to move the selection
                    if (GamePanel.gameState == GameState.MAIN_MENU) {
                        if (MenuGUI.selected == 0) {
                            MenuGUI.selected = 2;
                        } else {
                            MenuGUI.selected--;
                        }
                    } else if (GamePanel.gameState == GameState.NAME_SELECT) {
                        NameSelect.cursorCol = Math.max(0, NameSelect.cursorCol - 1);
                        NameSelect.adjustCursorForSpaceBackspace();
                        game.repaint();
                        game.revalidate();
                    }
                    if (GamePanel.gameState == GameState.GAME) {
                        GamePanel.leftPressed = true;
                        GamePanel.direction = PlayerDir.LEFT;
                    }
                    break;

                case KeyEvent.VK_D:
                    if (GamePanel.gameState == GameState.MAIN_MENU) {
                        if (MenuGUI.selected == 2) {
                            MenuGUI.selected = 0;
                        } else {
                            MenuGUI.selected++;
                        }
                    } else if (GamePanel.gameState == GameState.NAME_SELECT) {
                        NameSelect.cursorCol = Math.min(NameSelect.keyboardLayout[NameSelect.cursorRow].length - 1,
                                NameSelect.cursorCol + 1);
                        NameSelect.adjustCursorForSpaceBackspace();
                        game.repaint();
                        game.revalidate();
                    }
                    if (GamePanel.gameState == GameState.GAME) {
                        GamePanel.rightPressed = true;
                        GamePanel.direction = PlayerDir.RIGHT;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (GamePanel.gameState == GameState.NAME_SELECT) {
                        if ("Space".equals(NameSelect.keyboardLayout[NameSelect.cursorRow][NameSelect.cursorCol])) {
                            if (NameSelect.name.length() < NameSelect.MAX_NAME_LENGTH) {
                                NameSelect.name.append(" ");
                            }
                        } else if ("Backspace"
                                .equals(NameSelect.keyboardLayout[NameSelect.cursorRow][NameSelect.cursorCol])) {
                            if (NameSelect.name.length() > 0) {
                                NameSelect.name.deleteCharAt(NameSelect.name.length() - 1);
                            }
                        } else {
                            if (NameSelect.name.length() < NameSelect.MAX_NAME_LENGTH) {
                                NameSelect.name
                                        .append(NameSelect.keyboardLayout[NameSelect.cursorRow][NameSelect.cursorCol]);
                            }
                        }
                        game.repaint();
                        game.revalidate();
                    }
                    MenuGUI.pressed = true;
                    break;

                case KeyEvent.VK_P:
                    if (GamePanel.gameState == GameState.SCORES) {
                        GamePanel.gameState = GameState.MAIN_MENU;
                    } else if (GamePanel.gameState == GameState.INSTRUCTIONS) {
                        GamePanel.gameState = GameState.NAME_SELECT;
                    } else if (GamePanel.gameState == GameState.NAME_SELECT) {
                        System.out.println("done");
                        // write a setter method for leader board name using this(for Ryan):
                        //NameSelect.name.toString();
                        GamePanel.gameState = GameState.GAME;
                    } else if (GamePanel.gameState == GameState.GAME || GamePanel.gameState == GameState.SHOP) {
                        MainShop.open = !MainShop.open;
                        GamePanel.gameState = MainShop.open ? GameState.SHOP : GameState.GAME;
                    } else if (GamePanel.gameState == GameState.CREDITS) {
                        GamePanel.gameState = GameState.MAIN_MENU;
                    }
                    break;

                case KeyEvent.VK_SPACE:
                    if (released) {
                        GamePanel.shootPressed = true;
                        released = false;
                    }
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_W:

                    GamePanel.upPressed = false;
                    break;

                case KeyEvent.VK_S:

                    GamePanel.downPressed = false;
                    break;
                case KeyEvent.VK_A:

                    GamePanel.leftPressed = false;
                    break;

                case KeyEvent.VK_D:
                    GamePanel.rightPressed = false;
                    break;
                case KeyEvent.VK_ENTER:
                    MenuGUI.pressed = false;
                    break;
                case KeyEvent.VK_SPACE:
                    released = true;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }

    MainFrame() {
        setTitle("Soup N Zombs");
        setUndecorated(true);
        game = new GamePanel();
        game.setPreferredSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
        add(game);
        pack();
        setFocusable(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyHandler());
        setLocationRelativeTo(null);
        setVisible(true);
    }
}