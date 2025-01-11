package com.soupsnzombs.utils;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.GamePanel.GameState;
import com.soupsnzombs.GamePanel.PlayerDir;
import com.soupsnzombs.MainFrame;
import com.soupsnzombs.UI.MainMenu.MenuGUI;
import com.soupsnzombs.UI.MainMenu.NameSelect;
import com.soupsnzombs.UI.Shop.Shop;
// import com.soupsnzombs.entities.Entity;
import com.soupsnzombs.entities.Player;

import javax.swing.Timer;

public class KeyHandler extends KeyAdapter {
    GamePanel game;
    Player player;
    // boolean shootReleased = true; // trigger for non-automatic guns
    // KeyHandler class to handle key events
    public static boolean canShoot = true;
    private boolean shootReleased = true;
    public static Timer shootCooldown;
    public static Timer automaticGunTimer;
    public boolean dropReleased = true;
    public static Rectangle proximity;

    /**
     * constructor
     * @param game passses in the game object
     */
    public KeyHandler(GamePanel game) {
        this.game = game;
        this.player = game.getPlayer();
        shootCooldown = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.shotCoolDownTime -= 100 / (game.getPlayer().getGun().getFireRate() / 20); // 100 is the firerate
                                                                                                 // bar val, 20 is the
                                                                                                 // timer delay
                if (Player.shotCoolDownTime <= 0) {
                    canShoot = true;
                    shootCooldown.stop();
                    Player.showFireRateBar = false;
                    Player.shotCoolDownTime = 100;
                }
            }
        });

        automaticGunTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canShoot) {
                    GamePanel.shootPressed = true;
                    Player.showFireRateBar = true;
                    canShoot = false;
                    Player.shotCoolDownTime = 100;
                    shootCooldown.start(); // Start the cooldown timer
                }
            }
        });

        proximity = game.getShop().getBounds();
                        proximity.x -= 50;
                        proximity.y-=50;
                        proximity.width += 100;
                        proximity.height += 100;
    }

    /**
     * method to assign instructions when keys are pressed
     */
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

                if (GamePanel.gameState == GameState.SHOP) {
                    Shop.selectUp(game);
                }

                else if (GamePanel.gameState == GameState.NAME_SELECT) {
                    NameSelect.selectUp(game);
                }

                else if (GamePanel.gameState == GameState.GAME) {
                    GamePanel.upPressed = true;
                    GamePanel.direction = PlayerDir.UP;
                }
                break;

            case KeyEvent.VK_S:

                if (GamePanel.gameState == GameState.NAME_SELECT) {
                    NameSelect.selectDown(game);
                } else if (GamePanel.gameState == GameState.SHOP) {
                    Shop.selectDown(game);
                }

                else if (GamePanel.gameState == GameState.GAME) {
                    GamePanel.downPressed = true;
                    GamePanel.direction = PlayerDir.DOWN;
                }

                break;
            case KeyEvent.VK_A:

                // check if game hasn't started, use this to move the selection
                if (GamePanel.gameState == GameState.MAIN_MENU) {
                    if (MenuGUI.selected == 0) {
                        MenuGUI.selected = 2;
                    } else {
                        MenuGUI.selected--;
                    }
                } else if (GamePanel.gameState == GameState.NAME_SELECT) {
                    NameSelect.selectLeft(game);
                } else if (GamePanel.gameState == GameState.SHOP) {
                    
                    Shop.selectLeft(game);
                } else if (GamePanel.gameState == GameState.GAME) {
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
                } else if (GamePanel.gameState == GameState.SHOP) {
                    Shop.selectRight(game);
                } else if (GamePanel.gameState == GameState.GAME) {
                    GamePanel.rightPressed = true;
                    GamePanel.direction = PlayerDir.RIGHT;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (GamePanel.gameState == GameState.NAME_SELECT) {
                    NameSelect.selectEnter(game);
                } else if (GamePanel.gameState == GameState.SHOP) {
                    Shop.selectEnter(game);
                }
                break;
            case KeyEvent.VK_P:
                if (GamePanel.gameState == GameState.SCORES) {
                    GamePanel.gameState = GameState.MAIN_MENU;
                } else if (GamePanel.gameState == GameState.INSTRUCTIONS) {
                    GamePanel.gameState = GameState.GAME;
                    SoundManager.stopAllSounds();
                    SoundManager.playSound("bgm.wav");
                } else if (GamePanel.gameState == GameState.NAME_SELECT && !player.alive && (NameSelect.name.length()!=0)) {
                    Leaderboard.writeScores();
                    if (Leaderboard.valid){
                        GamePanel.gameState = GameState.MAIN_MENU;
                        Leaderboard.valid = false;
                        NameSelect.message = false;
                    }
                } else if (GamePanel.gameState == GameState.GAME || GamePanel.gameState == GameState.SHOP) {
                    if (Shop.open) {
                        
                        Shop.open = false;
                        GamePanel.gameState = GameState.GAME;
                    }
                        
                    else {
                        
                        
                        if (game.getPlayer().getBounds().intersects(proximity)) {
                            //System.out.println("Player is in shop proximity");
                            Shop.open = true;
                            GamePanel.gameState = GameState.SHOP;
                        }
                    }
                        
                    
                    
                    
                } else if (GamePanel.gameState == GameState.CREDITS) {
                    GamePanel.gameState = GameState.MAIN_MENU;
                } else
                    MenuGUI.pressed = true;
                break;

            case KeyEvent.VK_F:
                GamePanel.debugging = !GamePanel.debugging;
                break;

            case KeyEvent.VK_ESCAPE:
                if (GamePanel.gameState == GameState.GAME) {
                    player.alive = false;
                    System.out.println("Player died.");
                    // for debugging purposes, instantly kills the player
                } else if (GamePanel.gameState == GameState.MAIN_MENU) {
                    // System.exit(0);
                    MainFrame.frame.dispose();
                }
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
                } else if (game.getPlayer().getGun().getAutomaticState() == 1) {
                    if (!automaticGunTimer.isRunning()) {
                        automaticGunTimer.start(); // start firing when space held, a timer will ensure shooting won't
                                                   // get stuck when other keys are pressed
                    }
                    shootReleased = false;
                }
                break;

            case KeyEvent.VK_C:
                if (dropReleased) {
                    GamePanel.dropPressed = true;
                }
                dropReleased = false;
                break;
        }
    }

    /**
     * resets key pressed values when keys are released
     */
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
            case KeyEvent.VK_P:
                MenuGUI.pressed = false;
                break;
            case KeyEvent.VK_SPACE:
                shootReleased = true;
                if (game.getPlayer().getGun().getAutomaticState() == 1)
                    automaticGunTimer.stop(); // stop firing when space released
                break;
            case KeyEvent.VK_C:
                GamePanel.dropPressed = false;
                dropReleased = true;
                break;
        }
    }
}