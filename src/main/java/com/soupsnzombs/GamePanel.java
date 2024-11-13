package com.soupsnzombs;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GamePanel();
            }
        });
    }

}
