package com.soupsnzombs.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.GamePanel.GameState;
import com.soupsnzombs.GamePanel.PlayerDir;
import com.soupsnzombs.UI.MainMenu.MenuGUI;
import com.soupsnzombs.UI.MainMenu.NameSelect;
import com.soupsnzombs.UI.Shop.MainShop;
import com.soupsnzombs.entities.Player;

import javax.swing.Timer;

public class KeyHandler extends KeyAdapter {
    GamePanel game;
    //boolean released = true; // trigger for non-automatic guns
    // KeyHandler class to handle key events

    boolean canShoot = true;
    boolean released = true;
    Timer t;
    Timer coolDown;

    public KeyHandler(GamePanel game) {
        this.game = game;
        t = new Timer(game.getGun().getFireRate(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canShoot = true; // Enable shooting
                t.stop(); 
                coolDown.stop();
                Player.shotCoolDownTime = 100;
                Player.showFireRateBar = false;
            }
        });

        coolDown = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.shotCoolDownTime -= 7;
            }
        });
       
    }

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

                if (GamePanel.gameState == GameState.NAME_SELECT)
                    NameSelect.selectUp(game);
                break;

            case KeyEvent.VK_S:
                GamePanel.downPressed = true;
                GamePanel.direction = PlayerDir.DOWN;
                if (GamePanel.gameState == GameState.NAME_SELECT)
                    NameSelect.selectDown(game);
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
                    NameSelect.selectLeft(game);
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
                    NameSelect.selectRight(game);
                }
                if (GamePanel.gameState == GameState.GAME) {
                    GamePanel.rightPressed = true;
                    GamePanel.direction = PlayerDir.RIGHT;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (GamePanel.gameState == GameState.NAME_SELECT) {
                    NameSelect.selectEnter(game);
                }
                MenuGUI.pressed = true;
                break;

            case KeyEvent.VK_P:
                if (GamePanel.gameState == GameState.SCORES) {
                    GamePanel.gameState = GameState.MAIN_MENU;
                } else if (GamePanel.gameState == GameState.INSTRUCTIONS) {
                    GamePanel.gameState = GameState.GAME;
                } else if (GamePanel.gameState == GameState.NAME_SELECT) {
                    GamePanel.gameState = GameState.GAMEOVER;
                    // write the name to the file
                    Leaderboard.writeScores();

                } else if (GamePanel.gameState == GameState.GAME || GamePanel.gameState == GameState.SHOP) {
                    MainShop.open = !MainShop.open;
                    GamePanel.gameState = MainShop.open ? GameState.SHOP : GameState.GAME;
                } else if (GamePanel.gameState == GameState.CREDITS) {
                    GamePanel.gameState = GameState.MAIN_MENU;
                }
                break;

            case KeyEvent.VK_F:
                GamePanel.debugging = !GamePanel.debugging;
                break;

            case KeyEvent.VK_SPACE:
                if (released) {
                    if (canShoot) {
                        GamePanel.shootPressed = true; 
                        Player.showFireRateBar = true;
                        canShoot = false;
                        t.start();
                        coolDown.start(); 
                        
                    }
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