package com.soupsnzombs;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GamePanel();
            }
        });
    }

    GamePanel(){

    }

}
