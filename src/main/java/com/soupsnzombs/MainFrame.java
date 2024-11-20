package com.soupsnzombs;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class MainFrame extends JFrame {
    GamePanel game;

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_W:
                    GamePanel.upPressed = true;
                    break;

                case KeyEvent.VK_S:

                    GamePanel.downPressed = true;
                    // Add more cases if needed
                    break;
                case KeyEvent.VK_A:

                    GamePanel.leftPressed = true;
                    break;

                case KeyEvent.VK_D:

                    GamePanel.rightPressed = true;
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
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }

    MainFrame() {
        setTitle("Soup N Zombs");
        game = new GamePanel();
        add(game);
        setPreferredSize(new Dimension(1600, 1200));
        pack();
        setFocusable(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyHandler());
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}