package com.soupsnzombs.entities;

import java.awt.Graphics2D;

public interface GameObject {

    /**
     * @param g2d the graphics object
     * @return the bounds
     */
    public void draw(Graphics2D g2d);

}