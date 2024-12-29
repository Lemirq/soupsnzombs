package com.soupsnzombs.entities.zombies;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.entities.Entity;
import com.soupsnzombs.entities.GameObject;
import com.soupsnzombs.entities.Player;
import com.soupsnzombs.utils.CollisionManager;
import com.soupsnzombs.utils.Images;
import com.soupsnzombs.utils.Node;
import com.soupsnzombs.utils.Pathfinder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Zombie extends Entity implements GameObject {
    // private static int direction;
    private int health = 100;
    private BufferedImage sprite;
    public int moneyDropped = 10;
    public int pointsDropped = 10;
    public int damageTime = 500;
    Pathfinder pathfinder = new Pathfinder();

    public Zombie(int startX, int startY) {
        super(startX, startY, 0, 0, 100, 1);
        this.x = startX + GamePanel.offsetX;
        this.y = startY + GamePanel.offsetY;

        this.sprite = Images.spriteImages.get("zoimbie1_stand.png");
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // System.out.println("Zombie is dead");
            alive = false;
        }
    }

    public void draw(Graphics2D g2d, Player p) {
        pathfinder.updateGrid(p, this);
        // Calculate the screen position based on the world position and camera offsets
        int screenX = x + GamePanel.offsetX;
        int screenY = y + GamePanel.offsetY;

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
        // get current path through the pathfinder
        boolean path = pathfinder.findPath();
        if (path) {
            Node[][] grid = pathfinder.getGrid();
            int gridSize = Math.max(Math.max(this.width, p.width), Math.max(this.height, p.height));
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j].getType() == Node.Type.PATH) {
                        // Convert grid coordinates back to world coordinates
                        double targetX = i * gridSize; // Use zombie width as grid cell size
                        double targetY = j * gridSize;

                        // Calculate direction to next path node
                        double deltaX = targetX - this.x;
                        double deltaY = targetY - this.y;
                        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY); // hypotenuse

                        if (distance > 0) {
                            // Normalize and apply speed
                            double directionX = deltaX / distance;
                            double directionY = deltaY / distance;

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
                            } else {
                                // Force pathfinder to recalculate on next update when we hit an obstacle
                                pathfinder.resetPath();
                            }
                            return; // Only move towards the first path node
                        }
                    }
                }
            }
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
