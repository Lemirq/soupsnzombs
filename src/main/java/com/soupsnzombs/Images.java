package com.soupsnzombs;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Images {
    // public static ArrayList<BufferedImage> player_running = new ArrayList<>();
    // public static ArrayList<BufferedImage> gunfire = new ArrayList<>();
    public static BufferedImage player_idle, circle, gun, bullet, tree, shop, gameMenu, background,
            playButton, creditsButton;

    public static void loadImages() {
        try {
            background = ImageIO.read(Images.class.getResource("/bg.png"));
            playButton = ImageIO.read(Images.class.getResource("/playButton.png"));
            creditsButton = ImageIO.read(Images.class.getResource("/creditsButton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
