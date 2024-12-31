package com.soupsnzombs.entities.zombies;

import javax.swing.*;
import com.soupsnzombs.utils.Node;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridVisualizer extends JFrame {
    private Node[][] grid;
    private Timer repaintTimer;
    private GridPanel panel;
    private static final int REPAINT_DELAY = 100; // 100ms refresh rate
    public int gridSize = 0;

    public GridVisualizer() {
        setTitle("Grid Visualizer");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusableWindowState(false);
        setUndecorated(true);
        // Create and add the panel
        panel = new GridPanel();
        add(panel);

        // Initialize the timer
        repaintTimer = new Timer(REPAINT_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        });
        repaintTimer.start();
    }

    public void setGrid(Node[][] grid) {
        this.grid = grid;
    }

    // Inner class for the panel
    private class GridPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (grid == null) {
                g.drawString("No grid to visualize", 10, 50);
                return;
            }

            int cellSize = 20;

            // Add padding to center the grid
            int paddingX = (getWidth() - (grid.length * cellSize)) / 2;
            int paddingY = (getHeight() - (grid[0].length * cellSize)) / 2;

            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    Node node = grid[y][x];

                    int screenX = paddingX + (y * cellSize);
                    int screenY = paddingY + (x * cellSize);

                    switch (node.getType()) {
                        case EMPTY:
                            g.setColor(Color.WHITE);
                            break;
                        case WALL:
                            g.setColor(Color.BLACK);
                            break;
                        case START:
                            g.setColor(Color.GREEN);
                            break;
                        case END:
                            g.setColor(Color.RED);
                            break;
                        case PATH:
                            g.setColor(Color.YELLOW);
                            break;
                        case VISITED:
                            g.setColor(new Color(173, 216, 230)); // Light blue
                            break;
                    }

                    g.fillRect(screenX, screenY, cellSize, cellSize);
                    g.setColor(Color.GRAY);
                    g.drawRect(screenX, screenY, cellSize, cellSize);
                }
            }

            // Draw grid statistics
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            drawStatistics(g);
        }

        private void drawStatistics(Graphics g) {
            if (grid == null)
                return;

            int visitedCount = 0;
            int pathCount = 0;
            int wallCount = 0;
            int totalNodes = grid.length * grid[0].length;

            for (Node[] row : grid) {
                for (Node node : row) {
                    switch (node.getType()) {
                        case VISITED:
                            visitedCount++;
                            break;
                        case PATH:
                            pathCount++;
                            break;
                        case WALL:
                            wallCount++;
                            break;
                    }
                }
            }

            int y = 20;
            g.drawString(String.format("Grid Size: %dx%d (%d cells)",
                    grid.length, grid[0].length, totalNodes), 10, y);
            g.drawString("Visited Nodes: " + visitedCount, 10, y + 15);
            g.drawString("Path Nodes: " + pathCount, 10, y + 30);
            g.drawString("Wall Nodes: " + wallCount, 10, y + 45);
            // draw start and end coordinates
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j].getType() == Node.Type.START) {
                        g.drawString("Start: " + i + ", " + j, 10, y + 60);
                    }
                    if (grid[i][j].getType() == Node.Type.END) {
                        g.drawString("End: " + i + ", " + j, 10, y + 75);
                    }
                }
            }

            // cell size
            g.drawString("Cell Size: " + gridSize, 10, y + 90);

        }
    }

    @Override
    public void dispose() {
        repaintTimer.stop();
        super.dispose();
    }
}