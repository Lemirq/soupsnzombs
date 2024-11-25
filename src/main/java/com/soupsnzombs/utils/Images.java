package com.soupsnzombs.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Images {
    // public static ArrayList<BufferedImage> player_running = new ArrayList<>();
    // public static ArrayList<BufferedImage> gunfire = new ArrayList<>();
    public static BufferedImage player_idle, circle, gun, bullet, tree, shop, gameMenu, background,
            playButton, creditsButton, scoresButton, arrowImage;

    public static void loadImages() {
        try {
            background = ImageIO.read(Images.class.getResource("/bg.jpeg"));
            playButton = ImageIO.read(Images.class.getResource("/buttons/play.png"));
            scoresButton = ImageIO.read(Images.class.getResource("/buttons/scores.png"));
            creditsButton = ImageIO.read(Images.class.getResource("/buttons/credits.png"));
            arrowImage = ImageIO.read(Images.class.getResource("/arrow.png"));

            playButton = scaleImage(playButton, 150, 50);
            scoresButton = scaleImage(scoresButton, 150, 50);
            creditsButton = scaleImage(creditsButton, 150, 50);
            arrowImage = scaleImage(arrowImage, 51, 130 / 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedScaledImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        return bufferedScaledImage;
    }
}
