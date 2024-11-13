package com.soupsnzombs;

import javax.swing.*;

public class MainFrame extends JFrame{
    GamePanel game;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }

    MainFrame() {
        this.setTitle("Soup N Zomboobs");
        game = new GamePanel();
        this.add(game);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // set as full screen
        // this.setUndecorated(true);
        // frame.setUndecorated(true);

        this.setVisible(true);
    }
}