package com.soupsnzombs.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.GamePanel.GameState;
import com.soupsnzombs.GamePanel.PlayerDir;
import com.soupsnzombs.UI.MainMenu.MenuGUI;
import com.soupsnzombs.UI.Shop.MainShop;

public class KeyHandler extends KeyAdapter {
    boolean released = true; // trigger for non-automatic guns
    // KeyHandler class to handle key events

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
                MenuGUI.pressed = true;
                break;

            case KeyEvent.VK_P:
                if (GamePanel.gameState == GameState.SCORES) {
                    GamePanel.gameState = GameState.MAIN_MENU;
                } else if (GamePanel.gameState == GameState.GAME || GamePanel.gameState == GameState.SHOP) {
                    MainShop.open = !MainShop.open;
                    GamePanel.gameState = MainShop.open ? GameState.SHOP : GameState.GAME;
                }
                break;

            case KeyEvent.VK_SPACE:
                if (released) {
                    GamePanel.shootPressed = true;
                    released = false;
                }
                break;

            case KeyEvent.VK_ESCAPE:
                GamePanel.offsetX = 900;
                GamePanel.offsetY = 900;
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
