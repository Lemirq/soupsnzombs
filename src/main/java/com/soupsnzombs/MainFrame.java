package com.soupsnzombs;

import java.awt.Dimension;
import javax.swing.*;
import com.soupsnzombs.utils.KeyHandler;

public class MainFrame extends JFrame {
    GamePanel game;
    Boolean released = true; // trigger for non-automatic guns
    // KeyHandler class to handle key events

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
        addKeyListener(new KeyHandler(game));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}