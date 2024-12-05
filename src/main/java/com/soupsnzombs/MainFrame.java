package com.soupsnzombs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import com.soupsnzombs.utils.KeyHandler;
public class MainFrame extends JFrame {
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
        setTitle("Soup N Zombs");
        setUndecorated(true);
        game = new GamePanel();
        game.setPreferredSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
        add(game);
        pack();
        setFocusable(true);
        setResizable(false);
        addKeyListener(new KeyHandler(game));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        

        
    }
}   