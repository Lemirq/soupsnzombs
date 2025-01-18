package com.soupsnzombs.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    public enum Sound {
        GUNFIRE("gunfire.wav"),
        ZOMBIE_DEATH("zombie_death.wav"),
        BGM("bgm2.wav"),
        COIN_PICKUP("coin_pickup.wav"),
        DAMAGE("damage.wav"),
        AMBIENCE("ambience.wav"),
        RUSTLE("rustle.wav"),
        WALKING("walking.wav"),
        OCEAN("ocean.wav");

        private final String filePath;

        Sound(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }

    private static Map<String, Clip> loopingClips = new HashMap<>();
    private static boolean disabled = false;

    public static void init() {
        // Initialize looping sounds only (like BGM)
        loadLoopingSound("ambience.wav");
    }

    private static void loadLoopingSound(String filePath) {
        try {
            InputStream audioSrc = SoundManager.class.getResourceAsStream("/sounds/" + filePath);
            if (audioSrc == null) {
                throw new IOException("File not found: " + filePath);
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            loopingClips.put(filePath, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void setVolume(Sound sound, float volume) {
        String filePath = sound.getFilePath();
        Clip clip = loopingClips.get(filePath);
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    private static void setVolume(Clip clip, float volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
    }

    private static Clip createClip(String filePath, float volume) {
        try {
            InputStream audioSrc = SoundManager.class.getResourceAsStream("/sounds/" + filePath);
            if (audioSrc == null) {
                throw new IOException("File not found: " + filePath);
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            setVolume(clip, volume);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void playSound(Sound sound, boolean loop) {
        if (disabled)
            return;

        String filePath = sound.getFilePath();
        try {
            if (loop) {
                // Play or restart looping sound
                Clip clip = loopingClips.get(filePath);
                if (clip != null) {
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                    clip.setFramePosition(0);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                }
            } else {
                // Create a new Clip instance for non-looping sounds
                Clip clip = createClip(filePath, 0.0f);
                if (clip != null) {
                    clip.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSound(Sound sound, boolean loop, float volume) {
        if (disabled)
            return;

        String filePath = sound.getFilePath();
        try {
            if (loop) {
                // Play or restart looping sound
                Clip clip = loopingClips.get(filePath);
                if (clip != null) {
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                    clip.setFramePosition(0);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                }
            } else {
                // Create a new Clip instance for non-looping sounds
                Clip clip = createClip(filePath, volume);
                if (clip != null) {
                    clip.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setEnabled(boolean enabled) {
        disabled = !enabled;
    }

    public static void stopSound(Sound sound) {
        if (disabled)
            return;

        String filePath = sound.getFilePath();
        Clip clip = loopingClips.get(filePath);
        for (Clip c : loopingClips.values()) {
            System.out.println(c);
        }
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void stopAllSounds() {
        for (Clip clip : loopingClips.values()) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        }
    }
}
