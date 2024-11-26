package com.soupsnzombs.utils;

public class SpriteImage {
    String name;
    int x;
    int y;
    int width;
    int height;
    int frameX;
    int frameY;
    int frameWidth;
    int frameHeight;

    public SpriteImage(String name, int x, int y, int width, int height, int frameX, int frameY, int frameWidth,
            int frameHeight) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.frameX = frameX;
        this.frameY = frameY;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }
}
