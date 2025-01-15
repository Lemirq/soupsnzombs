package com.soupsnzombs.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static Map<String, Clip> clips = new HashMap<>();
    private static boolean disabled = true;

    public static void playSound(String filePath) {
        if (disabled)
            return;
        try {
            if (clips.containsKey(filePath)) {
                Clip clip = clips.get(filePath);
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } else {
                InputStream audioSrc = SoundManager.class.getResourceAsStream("/sfx/" + filePath);
                if (audioSrc == null) {
                    throw new IOException("File not found: " + filePath);
                }
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                clips.put(filePath, clip);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stopSound(String filePath) {
        Clip clip = clips.get(filePath);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void stopAllSounds() {
        for (Clip clip : clips.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
}