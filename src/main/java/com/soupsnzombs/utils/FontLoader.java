package com.soupsnzombs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

public class FontLoader {
    public static Font font;

    public static void loadFont() {
        // Load the font
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("f.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

}
