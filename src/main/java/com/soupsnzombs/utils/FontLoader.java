package com.soupsnzombs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

public class FontLoader {
    public static void loadFont() {
        // Load the font
        try {
            InputStream is = Font.class.getResource("/fonts/8bitOperatorPlus8-Regular.ttf").openStream();
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

}
