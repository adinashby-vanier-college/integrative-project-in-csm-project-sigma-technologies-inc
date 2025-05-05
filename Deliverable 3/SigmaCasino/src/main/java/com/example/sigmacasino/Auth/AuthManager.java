package com.example.sigmacasino.Auth;
import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;

public class AuthManager {

    private static final String user_dir = "users";

    public static String current_username = "Guest";

    public static boolean register(String username, String password) {
        File dir = new File(user_dir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File userFile = new File(dir, username + ".txt");
        if (userFile.exists()) {
            return false; // User already exists
        }

        try {
            String hashedPassword = hashPassword(password);
            Files.write(userFile.toPath(), hashedPassword.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean login(String username, String password) {
        File userFile = new File(user_dir, username + ".txt");
        if (!userFile.exists()) {
            return false; // User not found
        }

        try {
            String storedHash = new String(Files.readAllBytes(userFile.toPath()));
            String inputHash = hashPassword(password);
            return storedHash.equals(inputHash);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}