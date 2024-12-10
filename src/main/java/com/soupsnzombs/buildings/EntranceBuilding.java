package com.soupsnzombs.buildings;

import java.awt.*;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.CollisionManager;
import java.util.ArrayList;

public class EntranceBuilding extends Building {
    int entranceGap;
    int entranceDirection;
    int wallThickness;
    Wall left;
    Wall top;
    Wall right;
    Wall bottom;
    int displayX, displayY;
    public ArrayList<Wall> surroundingWalls = new ArrayList<>();
    public EntranceBuilding(int x, int y, int width, int height, int entranceGap, int entranceDirection, int wallThickness) {
        super(x, y, width, height);
        
        
        this.entranceGap = entranceGap;
        this.entranceDirection = entranceDirection;
        this.wallThickness = wallThickness;
        
        switch (entranceDirection) {
            case 1: //left
                left = new Wall(x-wallThickness, y, wallThickness, height);
                surroundingWalls.add(new Wall(left.x, left.y, wallThickness, left.height/2-entranceGap/2));
                surroundingWalls.add(new Wall(left.x, left.y+left.height/2+entranceGap/2, wallThickness, left.height/2-entranceGap/2));
                bottom = new Wall(x, y+height, width, wallThickness);
                surroundingWalls.add(bottom);
                right = new Wall(x+width, y, wallThickness, height);
                surroundingWalls.add(right);
                top = new Wall(x, y-wallThickness, width, wallThickness);
                surroundingWalls.add(top);
                break;
                
            case 2:
                
                break;
            case 3:
                break;
            case 4:
                break;
        }

        for (Wall wall:surroundingWalls) {
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

        // Draw the building
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



}