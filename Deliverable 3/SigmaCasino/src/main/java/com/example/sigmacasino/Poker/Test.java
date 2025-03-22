package com.example.sigmacasino.Poker;
import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import java.util.ArrayList;
import java.util.Collections;


public class Test {

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("\n\nExecution time: " + duration / 1_000_000.0 + " ms");
    }
}

