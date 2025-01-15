package com.soupsnzombs.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    // enum
    public enum Sound {
        GUNFIRE("gunfire.wav"),
        ZOMBIE_DEATH("zombie_death.wav"),
        BGM("bgm.wav"),
        COIN_PICKUP("coin_pickup.wav"),
        DAMAGE("damage.wav");

        private final String filePath;

        Sound(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }

    private static Map<String, Clip> clips = new HashMap<>();
    private static boolean disabled = false;

    public static void init() {
        // Initialize all game sounds
        loadSound("gunfire.wav");
        loadSound("zombie_death.wav");
        loadSound("bgm.wav");
        loadSound("coin_pickup.wav");
        loadSound("damage.wav");
    }

    private static void loadSound(String filePath) {
        try {
            InputStream audioSrc = SoundManager.class.getResourceAsStream("/sfx/" + filePath);
            if (audioSrc == null) {
                throw new IOException("File not found: " + filePath);
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clips.put(filePath, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playSound(Sound sound, boolean loop) {
        if (disabled)
            return;
        try {
            if (clips.containsKey(sound.getFilePath())) {
                Clip clip = clips.get(sound.getFilePath());
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);
                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setEnabled(boolean enabled) {
        disabled = !enabled;
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