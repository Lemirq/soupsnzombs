package com.soupsnzombs;

import javax.swing.JFrame;

public class MainFrame {
    public static void main(String[] args) {

        JFrame frame = new JFrame("2D Game");
        GamePanel game = new GamePanel();
        frame.add(game);
        frame.pack();
        frame.setTitle("Soups N Zombs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // set as full screen
        // frame.setUndecorated(true);
        // frame.setUndecorated(true);

        frame.setVisible(true);
        System.out.println();

    }
}