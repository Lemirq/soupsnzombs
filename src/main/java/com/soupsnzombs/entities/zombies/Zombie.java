package com.soupsnzombs.entities.zombies;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.*;
import com.soupsnzombs.utils.CollisionManager;
import com.soupsnzombs.utils.Images;
import com.soupsnzombs.utils.Node;
import com.soupsnzombs.utils.Pathfinder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.soupsnzombs.entities.zombies.AllZombies.waveNumber;
import static com.soupsnzombs.utils.Images.*;

public class Zombie extends Entity implements GameObject {
    // private static int direction;
    public static int screenX;
    public static int screenY;
    private int health;
    private BufferedImage sprite;
    public int pointsDropped;
    public int damageTime = 500;
    // TODO make pathfinder toggleable
    Pathfinder pathfinder = new Pathfinder();
    // linked list of path nodes, already converted to world coordinates
    private int pathRefreshCounter = 0;
    private static final int PATH_REFRESH_INTERVAL = 120; // Refresh path every 60 updates
    private static final double PLAYER_MOVE_THRESHOLD = 30.0;
    private double lastPlayerX = -1;
    private double lastPlayerY = -1;

    public static ZombieType zombieType;
    private double healthMax;
    private int damage;

    private List<Node> pathNodes = new ArrayList<>();

    public enum ZombieType {
        DEFAULT, FAT, SMALL, BOSS;
    }

    private static int maxDistance = 800; // max following distance between zombie and player

    public Zombie(int startX, int startY, ZombieType type) {
        super(startX, startY, 0, 0, 100, 1);
        this.x = startX + GamePanel.offsetX;
        this.y = startY + GamePanel.offsetY;

        Zombie.zombieType = type;
        switch (Zombie.zombieType) {
            case DEFAULT:
                health = 100;
                healthMax = 100;
                // TODO change loaded png file accordingly to the type of zomb
                this.sprite = Images.spriteImages.get("zoimbie1_stand.png");
                this.damage = 10;
                pointsDropped = 10;
                break;

            case FAT:
                health = 200;
                healthMax = 200;
                this.damage = 35;
                speed = 1 + 5 * (waveNumber / 5);
                pointsDropped = 20;
                // TODO change loaded png file accordingly to the type of zomb
                this.sprite = bigZombie;
                break;

            case SMALL:
                health = 75;
                healthMax = 75;
                this.damage = 5;
                speed = 2 + 5 * (waveNumber / 5);
                pointsDropped = 5;

                // TODO change loaded png file accordingly to the type of zomb
                this.sprite = smallZombie;
                break;

            case BOSS:
                health = 100 * waveNumber;
                healthMax = 100 * waveNumber;
                this.damage = 35 + 5 * (waveNumber / 5);
                speed = 1 + 5 * (waveNumber / 5);
                this.sprite = kingZombie;
                pointsDropped = 30;
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

    public void doNothing(Player p) {
        lastPlayerX = p.x;
        lastPlayerY = p.y;
    }

    public void chasePlayer(Player p, Graphics2D g2d) {
        pathRefreshCounter++;

        // calculate the disatnce between the player and the zombie
        // Calculate direction to target
        double deltaX = p.x - this.x;
        double deltaY = p.y - this.y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // If the player is too far away, don't bother with pathfinding
        if (distance > 1200) {
            return;
        }

        // Replace the existing direct path check with:
        if (hasLineOfSight(p, g2d)) {
            chasePlayerDirectly(p, g2d);
            return;
        }

        // Force path recalculation in these cases:
        boolean shouldRecalculatePath = pathNodes.isEmpty() ||
                Math.abs(p.x - lastPlayerX) >= PLAYER_MOVE_THRESHOLD ||
                Math.abs(p.y - lastPlayerY) >= PLAYER_MOVE_THRESHOLD ||
                pathRefreshCounter >= PATH_REFRESH_INTERVAL;

        if (shouldRecalculatePath) {
            pathfinder.updateGrid(p, this);
            boolean pathFound = pathfinder.findPath();
            pathRefreshCounter = 0;
            lastPlayerX = p.x;
            lastPlayerY = p.y;

            if (pathFound) {
                pathNodes.clear();
                Node[][] tempGrid = pathfinder.getGrid();
                for (int i = 0; i < tempGrid.length; i++) {
                    for (int j = 0; j < tempGrid[0].length; j++) {
                        if (tempGrid[i][j].getType() == Node.Type.PATH) {
                            pathNodes.add(tempGrid[i][j]);
                        }
                    }
                }
            }
        }

        if (!pathNodes.isEmpty()) {
            Node currentTarget = pathNodes.get(0);
            int gridSize = pathfinder.getGridSize();
            int gridOriginX = pathfinder.getGridOriginX();
            int gridOriginY = pathfinder.getGridOriginY();

            // Convert grid coordinates to world coordinates
            double targetX = gridOriginX + (currentTarget.getX() * gridSize);
            double targetY = gridOriginY + (currentTarget.getY() * gridSize);

            if (distance > 0) {
                followPath(currentTarget, gridSize, g2d, gridOriginX, gridOriginY);
            }

            // Remove current target node if reached
            if (Math.abs(x - targetX) < speed && Math.abs(y - targetY) < speed) {
                pathNodes.remove(0);
            }
        }

        // Debug visualization
        if (GamePanel.debugging) {
            g2d.setColor(Color.YELLOW);
            for (Node node : pathNodes) {
                int x = (pathfinder.getGridOriginX() + (node.getX() * pathfinder.getGridSize())) + GamePanel.offsetX;
                int y = (pathfinder.getGridOriginY() + (node.getY() * pathfinder.getGridSize())) + GamePanel.offsetY;
                g2d.fillRect(x, y, pathfinder.getGridSize(), pathfinder.getGridSize());
            }
        }
    }

    private boolean hasLineOfSight(Player p, Graphics2D g2d) {
        // Define corners for zombie
        double[][] zombiePoints = {
                { x, y }, // Top-left
                { x + width, y }, // Top-right
                { x, y + height }, // Bottom-left
                { x + width, y + height }, // Bottom-right
                { x + width / 2, y + height / 2 } // Center
        };

        // Define corners for player
        double[][] playerPoints = {
                { p.x, p.y }, // Top-left
                { p.x + p.width, p.y }, // Top-right
                { p.x, p.y + p.height }, // Bottom-left
                { p.x + p.width, p.y + p.height }, // Bottom-right
                { p.x + p.width / 2, p.y + p.height / 2 } // Center
        };

        // Check lines between all points
        for (double[] startPoint : zombiePoints) {
            for (double[] endPoint : playerPoints) {
                if (isPathClear(startPoint[0], startPoint[1], endPoint[0], endPoint[1], g2d)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isPathClear(double startX, double startY, double endX, double endY, Graphics2D g2d) {
        double dx = endX - startX;
        double dy = endY - startY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normalize direction
        dx /= distance;
        dy /= distance;

        // Step along the ray in small increments
        double stepSize = 5.0;
        double currentX = startX;
        double currentY = startY;

        // Debug visualization
        // if (GamePanel.debugging) {
        g2d.setColor(new Color(255, 255, 0, 50)); // Semi-transparent yellow
        g2d.drawLine(
                (int) startX + GamePanel.offsetX,
                (int) startY + GamePanel.offsetY,
                (int) endX + GamePanel.offsetX,
                (int) endY + GamePanel.offsetY);
        // }

        while (distance > 0 && distance < maxDistance) {
            Rectangle checkPoint = new Rectangle(
                    (int) currentX - 1,
                    (int) currentY - 1,
                    2, 2);

            // Debug visualization
            if (GamePanel.debugging) {
                g2d.setColor(new Color(255, 0, 0, 50)); // Semi-transparent red
                g2d.fillRect(
                        (int) currentX - 1 + GamePanel.offsetX,
                        (int) currentY - 1 + GamePanel.offsetY,
                        2, 2);
            }

            for (Rectangle obstacle : CollisionManager.collidables) {
                // Skip checking collision with self
                if (obstacle.equals(getBounds())) {
                    continue;
                }

                if (obstacle.intersects(checkPoint)) { 
                    //System.out.println("help");
                    return false;
                }
            }

            currentX += dx * stepSize;
            currentY += dy * stepSize;
            distance -= stepSize;
        }

        return true;
    }

    // Replace the current angle-based movement with smoother path following
    private void followPath(Node currentTarget, double gridSize, Graphics2D g2d, int gridOriginX, int gridOriginY) {
        double targetX = gridOriginX + (currentTarget.getX() * gridSize);
        double targetY = gridOriginY + (currentTarget.getY() * gridSize);

        // Calculate direction vector
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0 && distance < maxDistance) {
            // Normalize and apply speed
            double vx = (dx / distance) * speed;
            double vy = (dy / distance) * speed;

            // Try to move, if blocked try sliding along walls
            if (!isZombieMovable((int) vx, (int) vy, g2d)) {
                // Try horizontal movement
                if (Math.abs(dx) > Math.abs(dy) && isZombieMovable((int) vx, 0, g2d)) {
                    y += 0;
                    x += vx;
                }
                // Try vertical movement
                else if (isZombieMovable(0, (int) vy, g2d)) {
                    x += 0;
                    y += vy;
                }
            } else {
                x += vx;
                y += vy;
            }
        }
    }

    public boolean isZombieMovable(int vx, int vy, Graphics2D g2d) {
        // Check for collisions
        Rectangle nextPos = new Rectangle(
                x + vx,
                y + vy,
                width,
                height);

        // Check if next position would be out of bounds
        if (nextPos.x < GamePanel.X_Bounds[0] ||
                nextPos.x + nextPos.width > GamePanel.X_Bounds[1] ||
                nextPos.y < GamePanel.Y_Bounds[0] ||
                nextPos.y + nextPos.height > GamePanel.Y_Bounds[1]) {
            return false;
        }

        // Draw this rectangle in orange (for debugging)
        g2d.setColor(Color.ORANGE);
        g2d.drawRect(nextPos.x + GamePanel.offsetX, nextPos.y + GamePanel.offsetY, nextPos.width, nextPos.height);

        ArrayList<Rectangle> collisions = new ArrayList<>(CollisionManager.collidables);
        collisions.remove(this);

        Iterator<Rectangle> collidableIterator = collisions.iterator();
        while (collidableIterator.hasNext()) {
            Rectangle collidable = collidableIterator.next();
            if (collidable.width == 25) collidableIterator.remove(); //use width == 25 to check if it is a tree log
        }

        if (!CollisionManager.isColliding(nextPos, collisions)) {
            return true;
        }
        return false;
    }

    // legacy chasePlayer method
    public void chasePlayerDirectly(Player p, Graphics2D g2d) {
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

        if (isZombieMovable((int) vx, (int) vy, g2d)) {
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
