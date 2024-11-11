package com.soupsnzombs;

import javax.swing.JFrame;

public class MainFrame {
    public static void main(String[] args) {

        JFrame frame = new JFrame("2D Game");
        GamePanel game = new GamePanel();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frame.setUndecorated(true);

        frame.setVisible(true);
        System.out.println();

    }
}