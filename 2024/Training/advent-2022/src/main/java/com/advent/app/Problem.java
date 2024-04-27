package com.advent.app;

import java.util.ArrayList;
import java.util.HashMap;
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
    
    public Map<String, String> getResults() {
        long startTime = System.nanoTime();
        String output;
        switch(day) {
            case 1:
                output = Integer.toString(new CalorieCounter(input).getMostCalories());
                break;
            default:
                output = "ToDo: Implement me!";
        }
        long endTime = System.nanoTime();
        long timeTaken = endTime - startTime;
        double timeTakenSeconds = (double) timeTaken / 1_000_000_000;
        
        Map<String, String> results = new HashMap<>();
        if (expectedAnswer != null) {
            results.put("expected", expectedAnswer);
            results.put("actual", output);
            if (expectedAnswer.equals(output)) {
                results.put("result", "PASS");
            } else {
                results.put("result", "FAIL");
            }
        } else {
            results.put("output", output);
        }
        results.put("time (seconds)", Double.toString(timeTakenSeconds));
        return results;
    }

}
