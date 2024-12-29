package com.soupsnzombs.utils;

public class Node {
    public enum Type {
        EMPTY, WALL, START, END, PATH, VISITED
    }

    private int x, y;
    private Type type;
    private Node parent;
    private double g; // Cost from start to current node
    private double h; // Heuristic cost from current node to end
    private double f; // Total cost (g + h)

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = Type.EMPTY;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public Node getParent() { return parent; }
    public void setParent(Node parent) { this.parent = parent; }
    public double getG() { return g; }
    public void setG(double g) { this.g = g; }
    public double getH() { return h; }
    public void setH(double h) { this.h = h; }
    public double getF() { return f; }
    public void setF(double f) { this.f = f; }

    public void toggleWall() {
        if (type == Type.WALL) {
            type = Type.EMPTY;
        } else {
            type = Type.WALL;
        }
    }
}
