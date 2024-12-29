package com.soupsnzombs.utils;

import java.util.*;

public class AStar {
    private Node[][] grid;
    private Node startNode;
    private Node endNode;
    private PriorityQueue<Node> openSet;
    private HashSet<Node> closedSet;

    public void setGrid(Node[][] grid, Node startNode, Node endNode) {
        this.grid = grid;
        this.startNode = startNode;
        this.endNode = endNode;
        this.openSet = new PriorityQueue<>((a, b) -> Double.compare(a.getF(), b.getF()));
        this.closedSet = new HashSet<>();
    }

    public boolean findPath() {
        // Reset all nodes
        for (Node[] row : grid) {
            for (Node node : row) {
                if (node.getType() != Node.Type.WALL) {
                    node.setType(Node.Type.EMPTY);
                }
                node.setParent(null);
                node.setG(Double.POSITIVE_INFINITY);
                node.setF(Double.POSITIVE_INFINITY);
            }
        }

        startNode.setType(Node.Type.START);
        endNode.setType(Node.Type.END);

        startNode.setG(0);
        startNode.setH(calculateHeuristic(startNode, endNode));
        startNode.setF(startNode.getG() + startNode.getH());

        openSet.clear();
        closedSet.clear();
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current == endNode) {
                reconstructPath();
                return true;
            }

            closedSet.add(current);

            for (Node neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor) || neighbor.getType() == Node.Type.WALL) {
                    continue;
                }

                double tentativeG = current.getG() + 1;

                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (tentativeG >= neighbor.getG()) {
                    continue;
                }

                neighbor.setParent(current);
                neighbor.setG(tentativeG);
                neighbor.setH(calculateHeuristic(neighbor, endNode));
                neighbor.setF(neighbor.getG() + neighbor.getH());

                if (neighbor != endNode) {
                    neighbor.setType(Node.Type.VISITED);
                }
            }
        }

        return false;
    }

    private double calculateHeuristic(Node a, Node b) {
        // Manhattan distance
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

        for (int[] dir : directions) {
            int newX = node.getX() + dir[0];
            int newY = node.getY() + dir[1];

            if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }

    public Node[][] getGrid() {
        return grid;
    }

    public void reconstructPath() {
        Node current = endNode;
        while (current != startNode) {
            current = current.getParent();
            if (current != startNode) {
                current.setType(Node.Type.PATH);
            }
        }
    }
}