package com.example.sigmacasino.Settings;
import java.io.*;
import java.util.Properties;

public class Settings {

    // Settings
    private static boolean showUIStats;
    private static String theme;
    private static int soundLevel;
    private static String easterEgg;

    private static final String CONFIG_FILE = "casino.settings";



    public static void loadSettings() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);

            // Load settings
            showUIStats = Boolean.parseBoolean(properties.getProperty("showUIStats", "false"));
            theme = properties.getProperty("theme", "Light");
            soundLevel = Integer.parseInt(properties.getProperty("soundLevel", "50"));
            easterEgg = properties.getProperty("easterEgg", "Easter Eggs");

        } catch (IOException e) {
            System.out.println("No existing settings file found. Using default settings.");
        }
    }


    public void saveSettings() {
        Properties properties = new Properties();

        // Set properties
        properties.setProperty("showUIStats", String.valueOf(showUIStats));
        properties.setProperty("theme", theme);
        properties.setProperty("soundLevel", String.valueOf(soundLevel));
        properties.setProperty("easterEgg", easterEgg);

        // Save properties to file
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Getters and Setters
    public static boolean isShowUIStats() {
         return showUIStats;
    }

    public static void setShowUIStats(boolean Stats) {
        showUIStats = Stats;
    }

    public static String getTheme() {
        return theme;
    }

    public static void setTheme(String t) {
        theme = t;
    }

    public static int getSoundLevel() {
        return soundLevel;
    }

    public static void setSoundLevel(int Level) {
        soundLevel = Level;
    }

    public static String getEasterOption() {
        return easterEgg;
    }

    public static void setEasterOption(String e) {
        easterEgg = e;
    }
}
