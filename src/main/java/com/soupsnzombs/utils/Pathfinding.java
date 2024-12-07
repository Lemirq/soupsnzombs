package com.soupsnzombs.utils;

import java.util.*;

public class Pathfinding {
    private int[][] grid;
    private int rows, cols;

    public Pathfinding(int[][] grid) {
        this.grid = grid;
        rows = grid.length;
        cols = grid[0].length;
    }

    public List<Node> findPath(int startX, int startY, int targetX, int targetY) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        boolean[][] closedList = new boolean[rows][cols];

        openList.add(new Node(startX, startY, 0, heuristic(startX, startY, targetX, targetY), null));

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.x == targetX && current.y == targetY) {
                return constructPath(current);
            }

            closedList[current.x][current.y] = true;

            for (int[] direction : new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } }) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (isValid(newX, newY, closedList)) {
                    int newG = current.g + 1;
                    int newH = heuristic(newX, newY, targetX, targetY);

                    openList.add(new Node(newX, newY, newG, newH, current));
                }
            }
        }

        return new ArrayList<>(); // Return empty list if no path found
    }

    private boolean isValid(int x, int y, boolean[][] closedList) {
        return x >= 0 && x < rows && y >= 0 && y < cols && !closedList[x][y] && grid[x][y] == 0;
    }

    private int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan Distance
    }

    private List<Node> constructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node); // Add to the front
            node = node.parent;
        }
        return path;
    }
}
