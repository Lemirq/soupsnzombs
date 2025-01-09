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
    public Zombie zombie;
    public static int screenX;
    public static int screenY;
    private int health;
    public BufferedImage sprite;
    public int moneyDropped = 10;
    public int pointsDropped = 10;
    public int damageTime = 500;
    // TODO make pathfinder toggleable
    Pathfinder pathfinder = new Pathfinder();
    // linked list of path nodes, already converted to world coordinates
    private int pathRefreshCounter = 0;
    private static final int PATH_REFRESH_INTERVAL = 60; // Refresh path every 60 updates
    private static final double PLAYER_MOVE_THRESHOLD = 50.0;
    private double lastPlayerX = -1;
    private double lastPlayerY = -1;

    private ZombieType type;
    private double healthMax;
    private int damage;

    public enum ZombieType {
        DEFAULT, FAT, SMALL;
    }

    public Zombie(int startX, int startY, ZombieType type) {
        super(startX, startY, 0, 0, 100, 1);
        this.x = startX + GamePanel.offsetX;
        this.y = startY + GamePanel.offsetY;

        this.type = type;
        switch (this.type) {
            case DEFAULT:
                health = 100;
                healthMax = 100;
                // TODO change loaded png file accordingly to the type of zomb
                this.sprite = Images.spriteImages.get("zoimbie1_stand.png");
                this.damage = 10;
                break;

            case FAT:
                health = 200;
                healthMax = 200;
                this.damage = 35;
                speed = 1;

                // TODO change loaded png file accordingly to the type of zomb
                this.sprite = Images.spriteImages.get("zoimbie1_stand.png");
                break;

            case SMALL:
                health = 75;
                healthMax = 75;
                this.damage = 5;
                speed = 2;

                // TODO change loaded png file accordingly to the type of zomb
                this.sprite = Images.spriteImages.get("zoimbie1_stand.png");
                break;
        }

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
        int healthBarWidth = (int) ((this.health / this.healthMax) * barWidth);

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

        // TODO: TURN BACK ON
        if (directPathClear) {
            chasePlayerDirectly(p);
            return;
        }

        boolean path = false;
        // Check if the player has moved significantly
        double playerMoveDistance = Math.sqrt(Math.pow(p.x - lastPlayerX, 2) + Math.pow(p.y - lastPlayerY, 2));
        if (playerMoveDistance >= PLAYER_MOVE_THRESHOLD || pathRefreshCounter >= PATH_REFRESH_INTERVAL) {
            pathfinder.updateGrid(p, this);
            path = pathfinder.findPath();
            pathRefreshCounter = 0;
            lastPlayerX = p.x;
            lastPlayerY = p.y;
        }

        if (path) {
            Node[][] tempGrid = pathfinder.getGrid();
            int gridSize = pathfinder.getGridSize();
            int gridOriginX = pathfinder.getGridOriginX();
            int gridOriginY = pathfinder.getGridOriginY();

            // debugging, drawing all the path nodes
            for (int i = 0; i < tempGrid.length; i++) {
                for (int j = 0; j < tempGrid[0].length; j++) {
                    if (tempGrid[i][j].getType() == Node.Type.PATH) {
                        // Convert grid coordinates to world coordinates using the grid's origin
                        double targetX = gridOriginX + (i * gridSize);
                        double targetY = gridOriginY + (j * gridSize);

                        // Adjust target coordinates based on player's position relative to the zombie
                        if (p.x < this.x) {
                            targetX -= gridSize; // Move target to the left
                        } else if (p.x > this.x) {
                            targetX += gridSize; // Move target to the right
                        }

                        if (p.y < this.y) {
                            targetY -= gridSize; // Move target up
                        } else if (p.y > this.y) {
                            targetY += gridSize; // Move target down
                        }

                        // Draw the red rectangle for the path node
                        g2d.setColor(Color.RED);
                        g2d.fillRect((int) targetX + GamePanel.offsetX, (int) targetY + GamePanel.offsetY, gridSize,
                                gridSize);
                    }
                }
            }

            for (int i = 0; i < tempGrid.length; i++) {
                for (int j = 0; j < tempGrid[0].length; j++) {
                    if (tempGrid[i][j].getType() == Node.Type.PATH) {
                        // Convert grid coordinates to world coordinates using the grid's origin
                        double targetX = gridOriginX + (i * gridSize);
                        double targetY = gridOriginY + (j * gridSize);

                        // Adjust target coordinates based on player's position relative to the zombie
                        if (p.x < this.x) {
                            targetX -= gridSize; // Move target to the left
                        } else if (p.x > this.x) {
                            targetX += gridSize; // Move target to the right
                        }

                        if (p.y < this.y) {
                            targetY -= gridSize; // Move target up
                        } else if (p.y > this.y) {
                            targetY += gridSize; // Move target down
                        }

                        // Calculate direction to next path node
                        System.out.println(this.x + " " + this.y);
                        double deltaX = targetX - (this.x + this.width / 2);
                        double deltaY = targetY - (this.y + this.height / 2);
                        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                        // Debugging output
                        System.out.printf("Target Position: (%.2f, %.2f)\n", targetX, targetY);
                        System.out.printf("deltaX: %.2f, deltaY: %.2f, distance: %.2f\n", deltaX,
                                deltaY, distance);

                        // TODO: TURN BACK ON
                        if (distance < 10) {
                            chasePlayerDirectly(p);
                            System.out.println("Chasing player directly");
                            return;
                        }

                        if (distance > 0) {
                            // Normalize and apply speed
                            double directionX = deltaX / distance;
                            double directionY = deltaY / distance;
                            // Move zombie towards path node
                            int vx = (int) (directionX * speed);
                            int vy = (int) (directionY * speed);
                            // Debugging output
                            System.out.printf("directionX: %.2f, directionY: %.2f\n", directionX,
                                    directionY);
                            System.out.printf("Velocity: (vx: %d, vy: %d)\n", vx, vy);

                            // x += directionX * speed;
                            // y += directionY * speed;

                            // if (targetX < x) {
                            // vx += directionX * speed;
                            // }
                            // if (targetX > x) {
                            // vx -= directionX * speed;
                            // }

                            // if (targetY < y) {
                            // vy += directionY * speed;
                            // }
                            // if (targetY > y) {
                            // vy += directionY * speed;
                            // }

                            System.out.println("x: " + directionX + " y: " + directionY);

                            if (isZombieMovable(vx, vy)) {
                                x += vx;
                                y += vy;

                                // Remove the path node once the zombie reaches it
                                if (Math.abs(x - targetX) < speed && Math.abs(y - targetY) < speed) {
                                    tempGrid[i][j].setType(Node.Type.EMPTY);
                                    Pathfinder.g.setGrid(tempGrid);
                                }
                            } else {
                                // Force pathfinder to recalculate on next update when we hit an obstacle
                                System.out.println("Colliding with obstacle, not moving");
                                pathfinder.resetPath(p, this);
                            }
                            return; // Only move towards the first path node
                        } else {
                            // Force pathfinder to recalculate on next update when we hit an obstacle
                            pathfinder.resetPath(p, this);
                        }
                    }
                }
            }
        }

    }

    public boolean isZombieMovable(int vx, int vy) {
        // Check for collisions
        Rectangle nextPos = new Rectangle(
                x + vx,
                y + vy,
                width,
                height);

        ArrayList<Rectangle> collisions = new ArrayList<>(CollisionManager.collidables);
        collisions.remove(this);

        if (!CollisionManager.isColliding(nextPos, collisions)) {
            return true;
        }
        return false;
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
        vx = x < p.x ? speed : -speed;
        vy = y < p.y ? speed : -speed;

        if (isZombieMovable((int) vx, (int) vy)) {
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

    public int getDamage() {
        return this.damage;
    }

}
