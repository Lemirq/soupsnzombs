package com.soupsnzombs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

public class FontLoader {
    public static Font font10;
    public static Font font20;
    public static Font font30;
    public static Font font40;
    public static Font font50;
    public static Font font60;
    public static Font font70;
    public static Font font80;
    public static Font font100;
    public static Font font200;

    /**
     * loads all of the fonts for the game
     * for custom fonts
     */
    public static void loadFont() {
        // Load the font
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("f.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            font10 = font.deriveFont(10f);
            font20 = font.deriveFont(20f);
            font30 = font.deriveFont(30f);
            font40 = font.deriveFont(40f);
            font40 = font.deriveFont(45f);
            font50 = font.deriveFont(50f);
            font60 = font.deriveFont(60f);
            font70 = font.deriveFont(70f);
            font80 = font.deriveFont(80f);
            font100 = font.deriveFont(100f);
            font200 = font.deriveFont(200f);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

}
