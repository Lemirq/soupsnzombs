package com.soupsnzombs;

import javax.swing.*;
import java.awt.event.*;

public class HiWorld implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JButton button;

    public static void main(String[] args) {
        /*
         * Methods that create and show an event-driven
         * GUI should be run from an event-dispatching
         * thread
         */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HiWorld();
            }
        });
    }

    public HiWorld() {
        // Create and set up frame
        frame = new JFrame("HiWorld");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create a content panel
        panel = new JPanel();
        // Create and add a label
        label = new JLabel("Hello, World!");
        panel.add(label);
        // Create and add button
        button = new JButton("Hide");
        button.setActionCommand("Hide");
        button.addActionListener(this);
        panel.add(button);
        // Add content panel to frame
        frame.setContentPane(panel);
        // Add key listener to the frame
        frame.addKeyListener(new KeyHandler());
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        // Size and then display the frame
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Hide".equals(e.getActionCommand())) {
            label.setVisible(false);
            button.setText("Show");
            button.setActionCommand("Show");
        } else {
            label.setVisible(true);
            button.setText("Hide");
            button.setActionCommand("Hide");
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_H:
                    System.out.println("H key pressed");
                    label.setVisible(!label.isVisible());
                    button.setText(label.isVisible() ? "Hide" : "Show");
                    button.setActionCommand(label.isVisible() ? "Hide" : "Show");
                    break;
                // Add more cases if needed
            }
        }
    }
}