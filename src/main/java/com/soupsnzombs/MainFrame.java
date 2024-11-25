package com.soupsnzombs;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import com.soupsnzombs.GamePanel.GameState;
import com.soupsnzombs.UI.MenuGUI;
import com.soupsnzombs.UI.Shop;

public class MainFrame extends JFrame {
    GamePanel game;

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
                    break;

                case KeyEvent.VK_S:
                    GamePanel.downPressed = true;
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
                    if (GamePanel.gameState == GameState.GAME)
                        GamePanel.leftPressed = true;
                    break;

                case KeyEvent.VK_D:
                    if (GamePanel.gameState == GameState.MAIN_MENU) {
                        if (MenuGUI.selected == 2) {
                            MenuGUI.selected = 0;
                        } else {
                            MenuGUI.selected++;
                        }
                    }
                    if (GamePanel.gameState == GameState.GAME)
                        GamePanel.rightPressed = true;
                    break;
                case KeyEvent.VK_ENTER:
                    MenuGUI.play = true;
                    break;

                    case KeyEvent.VK_P:
                    Shop.shop = !Shop.shop;
                    if (Shop.shop && GamePanel.gameState != GamePanel.GameState.MAIN_MENU) {
                        GamePanel.gameState = GameState.SHOP;
                    } else {
                        return;
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