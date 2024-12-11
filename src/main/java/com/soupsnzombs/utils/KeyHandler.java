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
import com.soupsnzombs.UI.Shop.Shop;
import com.soupsnzombs.entities.Player;

import javax.swing.Timer;

public class KeyHandler extends KeyAdapter {
    GamePanel game;
    //boolean shootReleased = true; // trigger for non-automatic guns
    // KeyHandler class to handle key events

    public static boolean canShoot = true;
    boolean shootReleased = true;
    public static Timer shootCooldown;
    public static Timer automaticGunTimer;
    boolean dropReleased = true;

    public KeyHandler(GamePanel game) {
        this.game = game;
        shootCooldown = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.shotCoolDownTime -= 100/(game.getPlayer().getGun().getFireRate()/20); //100 is the firerate bar val, 20 is the timer delay
                if (Player.shotCoolDownTime <= 0) {
                    canShoot = true;
                    shootCooldown.stop(); 
                    Player.showFireRateBar = false;
                    Player.shotCoolDownTime = 100;
                }
                 // Enable shooting
            }
        });

        automaticGunTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canShoot && GamePanel.gameState == GameState.GAME) {
                    GamePanel.shootPressed = true;
                    Player.showFireRateBar = true;
                    canShoot = false;
                    shootCooldown.start(); // Start the cooldown timer
                }
                 // Enable shooting
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (e.isShiftDown() && GamePanel.debugging) {
            GamePanel.MOVE_SPEED = 30;
        } else if (e.isAltDown() && GamePanel.debugging) {
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
                    Shop.open = !Shop.open;
                    GamePanel.gameState = Shop.open ? GameState.SHOP : GameState.GAME;
                } else if (GamePanel.gameState == GameState.CREDITS) {
                    GamePanel.gameState = GameState.MAIN_MENU;
                }
                break;

            case KeyEvent.VK_F:
                GamePanel.debugging = !GamePanel.debugging;
                break;

            case KeyEvent.VK_SPACE:
                if (game.getPlayer().getGun().getAutomaticState() == -1) {
                    if (shootReleased && canShoot) {
                            GamePanel.shootPressed = true; 
                            Player.showFireRateBar = true;
                            canShoot = false;
                            shootCooldown.start();
                        }
                    shootReleased = false; 
                }
                else if (game.getPlayer().getGun().getAutomaticState() == 1) {
                    if (!automaticGunTimer.isRunning()) {
                        automaticGunTimer.start(); // start firing when space held, a timer will ensure shooting won't get stuck when other keys are pressed
                    }
                    shootReleased = false;
                }
                break;

            case KeyEvent.VK_C:
               if (dropReleased) {
                
               }
               dropReleased = false;
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
                shootReleased = true;
                if (game.getPlayer().getGun().getAutomaticState() == 1) automaticGunTimer.stop(); //stop firing when space released
                break;
            case KeyEvent.VK_C:
                dropReleased = true;
                break;
        }
    }
}