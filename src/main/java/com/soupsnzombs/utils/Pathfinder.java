package com.soupsnzombs.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.entities.zombies.GridVisualizer;
import com.soupsnzombs.entities.zombies.Zombie;

public class Pathfinder {

    AStar a = new AStar();
    public static GridVisualizer g = new GridVisualizer();
    public static Node[][] grid;
    private Node startNode;
    private Node endNode;
    private int gridOriginX;
    private int gridOriginY;
    private int gridSize;

    public Pathfinder() {
        if (GamePanel.debugging) {

            g.setVisible(true);

        } else {
            g.dispose();
            g = null;
        }
    }

    public void setGrid(Node[][] grid, Node startNode, Node endNode) {
        a.setGrid(grid, startNode, endNode);
        g.setGrid(grid);
    }

    public void updateGrid(Player p, Zombie z) {
        // Find the bounding rectangle that contains both zombie and player
        int minX = (int) Math.min(z.x, p.x);
        int minY = (int) Math.min(z.y, p.y);
        int maxX = (int) Math.max(z.x + z.width, p.x + p.width);
        int maxY = (int) Math.max(z.y + z.height, p.y + p.height);

        // Add some padding around the bounds
        int padding = 500; // increased padding for better pathfinding
        minX -= padding;
        minY -= padding;
        maxX += padding;
        maxY += padding;

        this.gridSize = 20;
        this.g.gridSize = gridSize;

        // Calculate grid dimensions
        int gridWidth = (int) Math.ceil((maxX - minX) / (double) gridSize);
        int gridHeight = (int) Math.ceil((maxY - minY) / (double) gridSize);

        // Create grid with minimum size of 3x3
        gridWidth = Math.max(3, gridWidth);
        gridHeight = Math.max(3, gridHeight);

        grid = new Node[gridWidth][gridHeight];

        // Initialize grid with nodes
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j] = new Node(i, j);
            }
        }

        // Convert collidable objects to wall nodes
        ArrayList<Rectangle> collidables = CollisionManager.collidables;
        // remove current zombie from collidables
        collidables.remove(z.getBounds());
        for (Rectangle rect : collidables) {
            int rectX = (int) rect.getX();
            int rectY = (int) rect.getY();
            int rectWidth = (int) rect.getWidth();
            int rectHeight = (int) rect.getHeight();

            int rectGridX = (int) Math.floor((rectX - minX) / (double) gridSize);
            int rectGridY = (int) Math.floor((rectY - minY) / (double) gridSize);
            int rectGridWidth = (int) Math.ceil(rectWidth / (double) gridSize);
            int rectGridHeight = (int) Math.ceil(rectHeight / (double) gridSize);

            for (int i = rectGridX; i < rectGridX + rectGridWidth; i++) {
                for (int j = rectGridY; j < rectGridY + rectGridHeight; j++) {
                    if (i >= 0 && i < gridWidth && j >= 0 && j < gridHeight) {
                        grid[i][j].setType(Node.Type.WALL);
                    }
                }
            }

        }

        // Calculate zombie and player positions on grid
        int zombieGridX = (int) Math.floor((z.x - minX) / (double) gridSize);
        int zombieGridY = (int) Math.floor((z.y - minY) / (double) gridSize);

        int playerGridX = (int) Math.floor((p.x - minX) / (double) gridSize);
        int playerGridY = (int) Math.floor((p.y - minY) / (double) gridSize);

        // Ensure start and end positions are within bounds
        zombieGridX = Math.max(0, Math.min(zombieGridX, gridWidth - 1));
        zombieGridY = Math.max(0, Math.min(zombieGridY, gridHeight - 1));
        playerGridX = Math.max(0, Math.min(playerGridX, gridWidth - 1));
        playerGridY = Math.max(0, Math.min(playerGridY, gridHeight - 1));

        // Clear the start and end positions (in case they were set as walls)
        grid[zombieGridX][zombieGridY].setType(Node.Type.START);
        grid[playerGridX][playerGridY].setType(Node.Type.END);

        startNode = grid[zombieGridX][zombieGridY];
        endNode = grid[playerGridX][playerGridY];

        // Store the grid's origin in world coordinates, not minx and miny

        this.gridOriginX = minX;
        this.gridOriginY = minY;

        setGrid(grid, startNode, endNode);
        if (g != null)
            g.setGrid(grid);
    }

    public boolean findPath() {
        return a.findPath();
    }

    public Node[][] getGrid() {
        return a.getGrid();
    }

    public void reconstructPath() {
        a.reconstructPath();
    }

    public void resetPath() {
        // Clear the current path by resetting relevant nodes
        Node[][] grid = getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].setType(Node.Type.EMPTY);
            }
        }

    }

    public int getGridOriginX() {
        return gridOriginX;
    }

    public int getGridOriginY() {
        return gridOriginY;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void draw(Graphics2D g2d) {
        // draw all the nodes, converte zd to world coordinates
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int x = (gridOriginX + (i * gridSize)) + GamePanel.offsetX;
                int y = (gridOriginY + (j * gridSize)) + GamePanel.offsetY;
                if (grid[i][j].getType() == Node.Type.PATH) {
                    g2d.setColor(Color.RED);
                    g2d.fillRect((int) x, (int) y, 10, 10);
                }
            }
        }
    }
}
