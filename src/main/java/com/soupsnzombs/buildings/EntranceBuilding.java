package com.soupsnzombs.buildings;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.CollisionManager;
import java.util.ArrayList;

public class EntranceBuilding extends Building {
    Wall left;
    Wall top;
    Wall right;
    Wall bottom;
    int displayX, displayY;
    int wallThickness;
    public ArrayList<Wall> surroundingWalls = new ArrayList<>();

    public EntranceBuilding(int x, int y, int width, int height, int entranceGap, int entranceDirection,
            int wallThickness) {
        super(x, y, width, height);

        this.wallThickness = wallThickness;
        left = new Wall(x - wallThickness, y, wallThickness, height);
        bottom = new Wall(x, y + height, width, wallThickness);
        right = new Wall(x + width, y, wallThickness, height);
        top = new Wall(x, y - wallThickness, width, wallThickness);
        switch (entranceDirection) {
            case 1: // create entrance on the left
                // two special walls for the entrance
                surroundingWalls.add(new Wall(left.x, left.y, left.width, left.height / 2 - entranceGap / 2));
                surroundingWalls.add(new Wall(left.x, left.y + left.height / 2 + entranceGap / 2, left.width,
                        left.height / 2 - entranceGap / 2));

                // rest of the normal walls
                surroundingWalls.add(bottom);

                surroundingWalls.add(right);

                surroundingWalls.add(top);
                break;

            case 2: // create entrance on the right
                surroundingWalls.add(new Wall(right.x, right.y, right.width, right.height / 2 - entranceGap / 2));
                surroundingWalls.add(new Wall(right.x, right.y + right.height / 2 + entranceGap / 2, right.width,
                        right.height / 2 - entranceGap / 2));

                surroundingWalls.add(bottom);
                surroundingWalls.add(top);
                surroundingWalls.add(left);

                break;

            case 3:
                surroundingWalls.add(new Wall(top.x, top.y, top.width / 2 - entranceGap / 2, top.height));
                surroundingWalls.add(new Wall(top.x + top.width / 2 + entranceGap / 2, top.y,
                        top.width / 2 - entranceGap / 2, top.height));

                surroundingWalls.add(bottom);
                surroundingWalls.add(right);
                surroundingWalls.add(left);
                break;
            case 4:
                surroundingWalls.add(new Wall(bottom.x, bottom.y, bottom.width / 2 - entranceGap / 2, bottom.height));
                surroundingWalls.add(new Wall(bottom.x + bottom.width / 2 + entranceGap / 2, bottom.y,
                        bottom.width / 2 - entranceGap / 2, bottom.height));

                surroundingWalls.add(top);
                surroundingWalls.add(right);
                surroundingWalls.add(left);
                break;

            default:
                surroundingWalls.add(bottom);
                surroundingWalls.add(top);
                surroundingWalls.add(right);
                surroundingWalls.add(left);
        }

        // fill in the 4 corners
        surroundingWalls.add(new Wall(left.x, left.y - wallThickness, wallThickness, wallThickness));
        surroundingWalls.add(new Wall(right.x, right.y - wallThickness, wallThickness, wallThickness));
        surroundingWalls.add(new Wall(left.x, left.y + left.height, wallThickness, wallThickness));
        surroundingWalls.add(new Wall(right.x, right.y + right.height, wallThickness, wallThickness));

        for (Wall wall : surroundingWalls) {
            CollisionManager.addCollidable(wall);
        }
    }

    public Wall getLeftWall() {
        return left;
    }

    public Wall getRightWall() {
        return right;
    }

    public Wall getTopWall() {
        return top;
    }

    public Wall getBottomWall() {
        return bottom;
    }

    @Override
    void drawBuilding(Graphics2D g2d, int x, int y, int leftEdge, int topEdge) {
        int buildingX = x + GamePanel.offsetX;
        int buildingY = y + GamePanel.offsetY;

        // Draw the building interior
        g2d.setColor(Color.GRAY);
        g2d.fillRect(buildingX, buildingY, width, height);
        if (GamePanel.debugging) {

            // Draw the building's border
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(buildingX, buildingY, width, height);

            // For debugging: draw the building's rectangle
            g2d.setColor(Color.GREEN);
            g2d.drawRect(x, y, width, height);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(super.x, super.y, super.width, super.height);
    }

    public void removeWall(int wallSide) {
        switch (wallSide) {
            case 1:
                try {
                    surroundingWalls.remove(left);
                    CollisionManager.collidables.remove(left);
                } catch (Exception e) { // this means that the entrace is on the left because left doesn't exist.

                }

                break;

            case 2:
                try {
                    surroundingWalls.remove(right);
                    CollisionManager.collidables.remove(right);
                } catch (Exception e) { // this means that the entrace is on the right because right doesn't exist.

                }

                break;

            case 3:
                try {
                    surroundingWalls.remove(top);
                    CollisionManager.collidables.remove(top);
                } catch (Exception e) {

                }

                break;

            case 4:
                try {
                    surroundingWalls.remove(bottom);
                    CollisionManager.collidables.remove(bottom);

                } catch (Exception e) {

                }

                break;
        }
    }

    public void removeWallLeft(int startingY, int endingY) {

        /*
         * Iterator<Wall> wallIterator = surroundingWalls.iterator();
         * while (wallIterator.hasNext()) { //remove potential corners, entrances and
         * "left" category walls
         * Wall wall = wallIterator.next();
         * if (wall.x == left.x) {
         * wallIterator.remove();
         * CollisionManager.collidables.remove(wall);
         * }
         * }
         */
        surroundingWalls.remove(left);
        CollisionManager.collidables.remove(left);
        surroundingWalls.add(new Wall(left.x, left.y, this.wallThickness, startingY - left.y));
        surroundingWalls.add(new Wall(left.x, endingY, this.wallThickness, left.height - (endingY - startingY)));
        for (Wall wall : surroundingWalls) {
            CollisionManager.addCollidable(wall);
        }
    }

    public void removeWallRight(int startingY, int endingY) {
        /*
         * Iterator<Wall> wallIterator = surroundingWalls.iterator();
         * while (wallIterator.hasNext()) { //remove potential corners, entrances and
         * "right" category walls
         * Wall wall = wallIterator.next();
         * if (wall.x == right.x) {
         * wallIterator.remove();
         * CollisionManager.collidables.remove(wall);
         * }
         * }
         */
        surroundingWalls.remove(right);
        CollisionManager.collidables.remove(right);
        surroundingWalls.add(new Wall(right.x, right.y, this.wallThickness, startingY - right.y));
        surroundingWalls.add(new Wall(right.x, endingY, this.wallThickness, right.height - (endingY - startingY)));
        for (Wall wall : surroundingWalls) {
            CollisionManager.addCollidable(wall);
        }
    }

    public void removeWallTop(int startingX, int endingX) {
        /*
         * Iterator<Wall> wallIterator = surroundingWalls.iterator();
         * while (wallIterator.hasNext()) {
         * Wall wall = wallIterator.next();
         * if (wall.x == top.x) {
         * wallIterator.remove();
         * CollisionManager.collidables.remove(wall);
         * }
         * }
         */
        surroundingWalls.remove(top);
        CollisionManager.collidables.remove(top);
        surroundingWalls.add(new Wall(top.x, top.y, startingX - top.x, this.wallThickness));
        surroundingWalls.add(new Wall(endingX, top.y, top.width - (endingX - startingX), this.wallThickness));
        for (Wall wall : surroundingWalls) {
            CollisionManager.addCollidable(wall);
        }
    }

    public void removeWallBottom(int startingX, int endingX) {
        /*
         * Iterator<Wall> wallIterator = surroundingWalls.iterator();
         * while (wallIterator.hasNext()) {
         * Wall wall = wallIterator.next();
         * if (wall.x == bottom.x) {
         * wallIterator.remove();
         * CollisionManager.collidables.remove(wall);
         * }
         * }
         */

        surroundingWalls.remove(bottom);
        CollisionManager.collidables.remove(bottom);
        // System.out.println(bottom.x);
        surroundingWalls.add(new Wall(bottom.x, bottom.y, startingX - bottom.x, this.wallThickness));

        surroundingWalls.add(new Wall(endingX, bottom.y, bottom.width - (endingX - startingX), this.wallThickness));

        for (Wall wall : surroundingWalls) {
            CollisionManager.addCollidable(wall);
        }
    }
}