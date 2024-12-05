package com.soupsnzombs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

public class FontLoader {
    public static Font font12;
    public static Font font24;
    public static Font font36;
    public static Font font54;

    public static void loadFont() {
        // Load the font
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("f.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            font12 = font.deriveFont(12f);
            font24 = font.deriveFont(24f);
            font36 = font.deriveFont(36f);
            font54 = font.deriveFont(54f);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

}
