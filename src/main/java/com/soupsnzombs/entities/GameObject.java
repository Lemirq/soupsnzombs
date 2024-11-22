package com.soupsnzombs.entities;

import java.awt.Graphics2D;

import com.soupsnzombs.utils.CRectangle;

public interface GameObject {

    public void draw(Graphics2D g2d);

    public boolean collidesWith(CRectangle other);
}