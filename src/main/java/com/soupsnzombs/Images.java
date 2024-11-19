package com.soupsnzombs;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Images {
    // public static ArrayList<BufferedImage> player_running = new ArrayList<>();
    // public static ArrayList<BufferedImage> gunfire = new ArrayList<>();
    public static BufferedImage player_idle, circle, gun, bullet, tree, shop, gameMenu;

    public static void loadImages() {
        try {
            gameMenu = ImageIO.read(Images.class.getResource("/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
