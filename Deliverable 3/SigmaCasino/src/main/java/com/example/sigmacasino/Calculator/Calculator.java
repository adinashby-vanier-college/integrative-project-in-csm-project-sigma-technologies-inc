package com.example.sigmacasino.Calculator;

public class Calculator {

    //Returns true or false based on given odd %
    public static boolean GetOdds(int percentage)
    {
        int token = CryptoRandom.GenerateRandomRangeInt(1, 100);
        return token<percentage;
    }
    //Returns true or false based on given odd (0-1f)
    public static boolean GetOdds(float percentage)
    {
        float token = CryptoRandom.GenerateRandomRangeFloat(0, percentage);
        return token<percentage;
    }
}
