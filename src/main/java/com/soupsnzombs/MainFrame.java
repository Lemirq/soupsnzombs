package com.soupsnzombs;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import com.soupsnzombs.GamePanel.GameState;
import com.soupsnzombs.GamePanel.PlayerDir;

import com.soupsnzombs.UI.MainMenu.MenuGUI;
import com.soupsnzombs.UI.Shop.MainShop;

public class MainFrame extends JFrame {
    GamePanel game;
    Boolean released = true; //trigger for non-automatic guns
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
                    break;

                case KeyEvent.VK_S:
                    GamePanel.downPressed = true;
                    GamePanel.direction = PlayerDir.DOWN;
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
                    }
                    if (GamePanel.gameState == GameState.GAME) {
                        GamePanel.rightPressed = true;
                        GamePanel.direction = PlayerDir.RIGHT;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    MenuGUI.play = true;
                    break;

                case KeyEvent.VK_P:
                    MainShop.open = !MainShop.open;
                    if (MainShop.open && GamePanel.gameState != GamePanel.GameState.MAIN_MENU) {
                        GamePanel.gameState = GameState.SHOP;
                    } else {
                        break;
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
                    MenuGUI.play = false;
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