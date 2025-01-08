package com.soupsnzombs.entities.zombies;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.*;
import com.soupsnzombs.utils.CollisionManager;
import com.soupsnzombs.utils.Images;
import com.soupsnzombs.utils.Node;
import com.soupsnzombs.utils.Pathfinder;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Zombie extends Entity implements GameObject {
    // private static int direction;
    public static int screenX;
    public static int screenY;
    private int health = 100;
    private BufferedImage sprite;
    public int moneyDropped = 10;
    public int pointsDropped = 10;
    public int damageTime = 500;
    // TODO make pathfinder toggleable
    Pathfinder pathfinder = new Pathfinder();
    // linked list of path nodes, already converted to world coordinates
    private int pathRefreshCounter = 0;
    private static final int PATH_REFRESH_INTERVAL = 60; // Refresh path every 60 updates

    public Zombie(int startX, int startY) {
        super(startX, startY, 0, 0, 100, 1);
        this.x = startX + GamePanel.offsetX;
        this.y = startY + GamePanel.offsetY;

        this.sprite = Images.spriteImages.get("zoimbie1_stand.png");
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }
    /*
     * public void dropCoins() {
     * if (health == 0) {
     * //if (randInt == 1) {
     * Coin coin = new Coin(x, y, 10, 10, 1);
     * //}
     * }
     * }
     * 
     */

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // System.out.println("Zombie is dead");
            alive = false;
        }
    }

    public void draw(Graphics2D g2d, Player p) {
        // TODO Make pathfinding stuff toggleable
        pathfinder.updateGrid(p, this);
        pathfinder.draw(g2d);

        // Calculate the screen position based on the world position and camera offsets
        screenX = x + GamePanel.offsetX;
        screenY = y + GamePanel.offsetY;

        // Calculate the angle of the velocity vector
        double angle = Math.atan2(p.y - y, p.x - x);

        // Save the original transform

        // Apply the rotation transformation
        g2d.rotate(angle, screenX + width / 2, screenY + height / 2);

        // Draw the zombie at the calculated screen position
        g2d.drawImage(sprite, screenX, screenY, null);

        // Reset the transformation
        g2d.setTransform(GamePanel.oldTransformation);

        // Draw health bar above the zombie
        drawHealthBar(g2d, screenX, screenY - 10);

        // For debugging: draw the zombie's rectangle
        if (GamePanel.debugging) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(x, y, width, height);
        }
    }

    private void drawHealthBar(Graphics2D g2d, int x, int y) {
        int barWidth = 50;
        int barHeight = 10;
        int healthBarWidth = (int) ((health / 100.0) * barWidth);
        g2d.setColor(Color.RED);
        g2d.fillRoundRect(x - 10, y - 2, barWidth, barHeight, 10, 10); // subtract 10 to center the rectangle onto
                                                                       // zombie

        g2d.setColor(Color.GREEN);
        g2d.fillRoundRect(x - 10, y - 2, healthBarWidth, barHeight, 10, 10); // subtract 10 to center the rectangle onto
                                                                             // zombie
    }

    public boolean isColliding(Rectangle rect) {
        return intersects(rect);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }

    public void chasePlayer(Player p, Graphics2D g2d) {
        // TODO Make pathfinding stuff toggleable
        pathRefreshCounter++;

        // Calculate direct line to player
        Line2D directLine = new Line2D.Double(this.x, this.y, p.x, p.y);
        boolean directPathClear = true;

        // Check for collidables in the direct path
        ArrayList<Rectangle> collidables = CollisionManager.collidables;
        for (Rectangle rect : collidables) {
            if (directLine.intersects(rect)) {
                directPathClear = false;
                break;
            }
        }

        if (directPathClear) {
            chasePlayerDirectly(p);
            return;
        }

        boolean path = pathfinder.findPath();

        if (pathRefreshCounter >= PATH_REFRESH_INTERVAL) {
            pathfinder.updateGrid(p, this);
            pathfinder.findPath();
            pathRefreshCounter = 0;
        }

        if (path) {

            Node[][] tempGrid = pathfinder.getGrid();
            int gridSize = pathfinder.getGridSize();
            int gridOriginX = pathfinder.getGridOriginX();
            int gridOriginY = pathfinder.getGridOriginY();

            for (int i = 0; i < tempGrid.length; i++) {
                for (int j = 0; j < tempGrid[0].length; j++) {
                    if (tempGrid[i][j].getType() == Node.Type.PATH) {
                        // Convert grid coordinates to world coordinates using the grid's origin
                        double targetX = gridOriginX + (i * gridSize);
                        double targetY = gridOriginY + (j * gridSize);

                        // Debug visualization
                        // if (GamePanel.debugging) {
                        // g2d.setColor(Color.CYAN);
                        // g2d.fillRect((int) targetX, (int) targetY, gridSize, gridSize);
                        // }

                        // Calculate direction to next path node
                        double deltaX = targetX - this.x;
                        double deltaY = targetY - this.y;
                        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                        System.out.println("distance: " + distance);

                        // draw a rect at TARGETX and TARGETY
                        // g2d.setColor(Color.RED);
                        // g2d.fillRect((int) targetX + GamePanel.offsetX, (int) targetY +
                        // GamePanel.offsetY, gridSize,
                        // gridSize);

                        if (distance > 0) {
                            // Normalize and apply speed
                            double directionX = Math.floor(deltaX / distance);
                            double directionY = Math.floor(deltaY / distance);
                            System.out.println("directionX: " + directionX + " directionY: " +
                                    directionY);
                            // Move zombie towards path node
                            Rectangle nextPos = new Rectangle(
                                    x + (int) (directionX * speed),
                                    y + (int) (directionY * speed),
                                    width,
                                    height);

                            ArrayList<Rectangle> collisions = new ArrayList<>(CollisionManager.collidables);
                            collisions.remove(this);

                            if (!CollisionManager.isColliding(nextPos, collisions)) {
                                x += directionX * speed;
                                y += directionY * speed;
                                // Remove the path node once the zombie reaches it
                                if (Math.abs(x - targetX) < speed && Math.abs(y - targetY) < speed) {
                                    tempGrid[i][j].setType(Node.Type.EMPTY);
                                    Pathfinder.g.setGrid(tempGrid);
                                }
                            } else {
                                // Force pathfinder to recalculate on next update when we hit an obstacle
                                // pathfinder.resetPath();
                            }
                            return; // Only move towards the first path node
                        } else {
                            // Force pathfinder to recalculate on next update when we hit an obstacle
                            // pathfinder.resetPath();
                        }
                    }
                }
            }
        }

    }

    // legacy chasePlayer method
    public void chasePlayerDirectly(Player p) {
        // boolean path = pathfinder.findPath();
        // int vy = 0;
        // int vx = 0;
        double deltaX = p.x - this.x;
        double deltaY = p.y - this.y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Normalize the direction vector
        double directionX = deltaX / distance;
        double directionY = deltaY / distance;
        // Calculate the velocity components
        double vx = directionX * speed;
        double vy = directionY * speed;

        // make zombie walk on hypotenuse towards player

        if (x < p.x) {
            vx += speed;
        } else if (x > p.x) {
            vx -= speed;
        }

        if (y < p.y) {
            vy += speed;
        } else if (y > p.y) {
            vy -= speed;
        }

        Rectangle rect = new Rectangle(x + (int) vx, y + (int) vy, width, height);
        ArrayList<Rectangle> newCollisions = CollisionManager.collidables;
        // find the current zombie in list of collisions
        newCollisions.remove(this);

        if (!CollisionManager.isColliding(rect, newCollisions)) {
            x += vx;
            y += vy;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Getters for the zombie's health
    public int getHealth() {
        return health;
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

}
