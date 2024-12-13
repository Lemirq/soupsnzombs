package com.soupsnzombs.buildings;

import com.soupsnzombs.GamePanel;
import com.soupsnzombs.utils.CollisionManager;
import java.awt.*;
import java.util.ArrayList;

public class AllTrees {
    public ArrayList<Tree> trees = new ArrayList<>();

    public void addTree(Tree t) {
        trees.add(t);
    }

    public AllTrees() {
        trees.add(new Tree(350, 100,50, 50));
        for (Tree t : trees) {
            CollisionManager.addCollidable(t);
        }

    }

    public void drawTree(Graphics2D g2d) {
        for (Tree t : trees) {
            t.draw(g2d);
            // draw rect x,y,w,h
            g2d.setColor(Color.RED);
            if (GamePanel.debugging) {
                g2d.drawString("X: " + t.x + " Y: " + t.y + " W: " + t.width + " H: " + t.height, 20,
                        trees.indexOf(t) * 20 + 40);
            }
        }

    }
}


