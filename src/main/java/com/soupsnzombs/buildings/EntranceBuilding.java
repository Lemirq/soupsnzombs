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
    public ArrayList<Wall> surroundingWalls = new ArrayList<>();
    public EntranceBuilding(int x, int y, int width, int height, int entranceGap, int entranceDirection, int wallThickness) {
        super(x, y, width, height);

        left = new Wall(x-wallThickness, y, wallThickness, height);
        bottom = new Wall(x, y+height, width, wallThickness);
        right = new Wall(x+width, y, wallThickness, height);
        top = new Wall(x, y-wallThickness, width, wallThickness);
        switch (entranceDirection) {
            case 1: //create entrance on the left
                //two special walls for the entrance
                surroundingWalls.add(new Wall(left.x, left.y, left.width, left.height/2-entranceGap/2));
                surroundingWalls.add(new Wall(left.x, left.y+left.height/2+entranceGap/2, left.width, left.height/2-entranceGap/2));
                
                //rest of the normal walls
                surroundingWalls.add(bottom);
                
                surroundingWalls.add(right);
               
                surroundingWalls.add(top);
                break;
                
            case 2: //create entrance on the right
                surroundingWalls.add(new Wall(right.x, right.y, right.width, right.height/2-entranceGap/2));
                surroundingWalls.add(new Wall(right.x, right.y+right.height/2+entranceGap/2, right.width, right.height/2-entranceGap/2));

                surroundingWalls.add(bottom);
                surroundingWalls.add(top);
                surroundingWalls.add(left);

                break;
              
            case 3:
                surroundingWalls.add(new Wall(top.x, top.y, top.width/2-entranceGap/2, top.height));
                surroundingWalls.add(new Wall(top.x+top.width/2+entranceGap/2, top.y, top.width/2-entranceGap/2, top.height));

                surroundingWalls.add(bottom);
                surroundingWalls.add(right);
                surroundingWalls.add(left);
                break;
            case 4:
                surroundingWalls.add(new Wall(bottom.x, bottom.y, bottom.width/2-entranceGap/2, bottom.height));
                surroundingWalls.add(new Wall(bottom.x+bottom.width/2+entranceGap/2, bottom.y, bottom.width/2-entranceGap/2, bottom.height));

                surroundingWalls.add(top);
                surroundingWalls.add(right);
                surroundingWalls.add(left);
                break;
        }

        //fill in the 4 corners
        surroundingWalls.add(new Wall(left.x, left.y-wallThickness, wallThickness, wallThickness));
        surroundingWalls.add(new Wall(right.x, right.y-wallThickness, wallThickness, wallThickness));
        surroundingWalls.add(new Wall(left.x, left.y+left.height, wallThickness, wallThickness));
        surroundingWalls.add(new Wall(right.x, right.y+right.height, wallThickness, wallThickness));


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