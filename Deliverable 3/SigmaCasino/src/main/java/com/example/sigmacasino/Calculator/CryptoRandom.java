package com.example.sigmacasino.Calculator;
import java.security.SecureRandom;
import java.nio.ByteBuffer;

public class CryptoRandom {

    private static final SecureRandom secureRandom = new SecureRandom();

    //Generate random bytes cryptographically
    private static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    // Convert bytes to a float between min and max (inclusive)
    private static float bytesToFloatInRange(byte[] bytes, float min, float max) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int intValue = Math.abs(buffer.getInt()); // convert to int and keep it positive
        float normalized = intValue / (float) Integer.MAX_VALUE;
        return min + (normalized * (max - min));
    }

    // Convert bytes to an int between min and max (inclusive)
    private static int bytesToIntInRange(byte[] bytes, int min, int max) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int intValue = Math.abs(buffer.getInt()); // make sure it's positive
        return min + (intValue % (max - min + 1));
    }


    public static int GenerateRandomRangeInt(int min, int max)
    {
        byte[] bytes = generateRandomBytes(4); //4 bytes for int
        return bytesToIntInRange(bytes, min, max);
    }
    public static float GenerateRandomRangeFloat(float min, float max)
    {
        byte[] bytes = generateRandomBytes(4); //4 bytes for float, might change to 8 for more precision/double
        return bytesToFloatInRange(bytes, min, max);
    }
}
