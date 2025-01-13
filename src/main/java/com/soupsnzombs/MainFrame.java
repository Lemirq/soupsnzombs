package com.soupsnzombs;

import java.awt.Dimension;
import javax.swing.*;
import com.soupsnzombs.utils.KeyHandler;

public class MainFrame {
    public static JFrame frame = new JFrame();
    GamePanel game;

    // KeyHandler class to handle key events
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }

    MainFrame() {
        frame.setTitle("Soups 'N' Zombs");
        frame.setUndecorated(true);
        game = new GamePanel();
        game.setPreferredSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
        frame.add(game);
        frame.pack();
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.addKeyListener(new KeyHandler(game));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}