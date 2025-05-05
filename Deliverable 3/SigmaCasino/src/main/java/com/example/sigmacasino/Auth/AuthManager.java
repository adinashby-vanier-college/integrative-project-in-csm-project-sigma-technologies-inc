package com.example.sigmacasino.Auth;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;

public class AuthManager {

    private static final String user_dir = "users";

    public static String current_username = "Guest";

    //Creates a new account by hashing the password and save it in files
    public static boolean register(String username, String password) {
        File dir = new File(user_dir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File userFile = new File(dir, username + ".txt");
        if (userFile.exists()) {
            current_username = "Guest";
            return false; // User already exists
        }

        try {
            String hashedPassword = hashPassword(password);
            Files.write(userFile.toPath(), hashedPassword.getBytes());
            current_username = username;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            current_username = "Guest";
            return false;
        }
    }

    //Login the user by searching the username and check if the hashes matches
    public static boolean login(String username, String password) {
        File userFile = new File(user_dir, username + ".txt");
        if (!userFile.exists()) {
            return false; // User not found
        }

        try {
            String storedHash = new String(Files.readAllBytes(userFile.toPath()));
            String inputHash = hashPassword(password);
            boolean result = storedHash.equals(inputHash);
            if(result)
            {
                current_username = username;
            }else
            {
                current_username = "Guest";
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            current_username = "Guest";
            return false;
        }
    }

    //Hash string using sha 256
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //convert bytes to hex for saving as string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}