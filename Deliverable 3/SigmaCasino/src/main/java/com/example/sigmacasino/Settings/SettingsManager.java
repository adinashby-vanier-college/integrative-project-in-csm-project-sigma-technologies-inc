package com.example.sigmacasino.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsManager {
    private static final String SETTINGS_FILE = "settings.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static class Settings {
        public String username = "Player1";
        public int volume = 75;
        public boolean fullscreen = true;

        // Add more settings as needed
    }

    public static void saveSettings(Settings settings) {
        try (FileWriter writer = new FileWriter(SETTINGS_FILE)) {
            gson.toJson(settings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Settings loadSettings() {
        try (FileReader reader = new FileReader(SETTINGS_FILE)) {
            return gson.fromJson(reader, Settings.class);
        } catch (IOException e) {
            System.out.println("No settings file found. Using default settings.");
            return new Settings();
        }
    }

    public static void main(String[] args) {
        // Load existing or default settings
        Settings settings = loadSettings();

        // Modify settings
        settings.volume = 50;

        // Save them back to file
        saveSettings(settings);

        System.out.println("Settings saved.");
    }
}
