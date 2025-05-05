package com.example.sigmacasino.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsManager {

    private static final String settings_file = "settings.json";

    public static Settings settings = new Settings();

    public static class Settings {
        public boolean isShowUIStats = true;
        public float volume = 1;
    }

    public static void saveSettings(Settings settings) {
        try (FileWriter writer = new FileWriter(settings_file)) {
            Gson gson = new Gson();
            gson.toJson(settings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Settings loadSettings() {
        try (FileReader reader = new FileReader(settings_file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Settings.class);
        } catch (IOException e) {
            System.out.println("No settings file found. Using default settings.");
            return new Settings();
        }
    }

}
