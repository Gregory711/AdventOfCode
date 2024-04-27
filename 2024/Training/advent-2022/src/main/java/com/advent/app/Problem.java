package com.advent.app;

import java.util.ArrayList;
import java.util.Map;

public class Problem {

    private final int day;
    private ArrayList<String> input;
    // null if not a test
    private final String expectedAnswer;

    public Problem(int day, ArrayList<String> input, String expectedAnswer) {
        this.day = day;
        this.input = input;
        this.expectedAnswer = expectedAnswer;
    }
    
}
