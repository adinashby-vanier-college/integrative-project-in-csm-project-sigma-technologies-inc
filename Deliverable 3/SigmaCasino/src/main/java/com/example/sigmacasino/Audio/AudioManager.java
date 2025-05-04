package com.example.sigmacasino.Audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.HashMap;

public class AudioManager {
    
    private static final HashMap<String, MediaPlayer> sounds = new HashMap<>();

    public static void loadSound(String name, String filePath) {
        System.out.println(new File(filePath).toURI().toString());
        Media media = new Media(new File(filePath).toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        sounds.put(name, player);
    }

    public static void playSound(String name) {
        MediaPlayer player = sounds.get(name);
        if (player == null) {
            System.err.println("AudioManager: No sound loaded with name: " + name);
            return;
        }

        player.stop(); // restart from beginning if already playing
        player.seek(javafx.util.Duration.ZERO);
        player.play();
    }

    public static void setVolume(String name, double volume) {
        MediaPlayer player = sounds.get(name);
        if (player != null) {
            player.setVolume(volume); // 0.0 to 1.0
        }
    }

    public static void stopSound(String name) {
        MediaPlayer player = sounds.get(name);
        if (player != null) {
            player.stop();
        }
    }
}
