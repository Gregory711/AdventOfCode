package com.advent.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Problem {

    private final String day;
    private ArrayList<String> input;
    // null if not a test
    private final String expectedAnswer;

    public Problem(String day, ArrayList<String> input, String expectedAnswer) {
        this.day = day;
        this.input = input;
        this.expectedAnswer = expectedAnswer;
    }
    
    public Map<String, String> getResults() {
        long startTime = System.nanoTime();
        String output;
        switch(day) {
            case "1":
                output = Integer.toString(new CalorieCounter(input).getMostCalories());
                break;
            case "1b":
                output = Integer.toString(new CalorieCounter(input).getTopThreesCalories());
                break;
            case "2":
                output = Integer.toString(new RockPaperScissors(input, false).getTotalScore());
                break;
            case "2b":
                output = Integer.toString(new RockPaperScissors(input, true).getTotalScore());
                break;
            case "3":
                output = Integer.toString(new Reorganization(input, false).getPrioritySum());
                break;
            case "3b":
                output = Integer.toString(new Reorganization(input, true).getPrioritySum());
                break;
            case "4":
                output = Integer.toString(new Cleanup(input).getContained());
                break;
            case "4b":
                output = Integer.toString(new Cleanup(input).getOverlapped());
                break;
            case "5":
                output = new Crane(input, false).getTopCrates();
                break;
            case "5b":
                output = new Crane(input, true).getTopCrates();
                break;
            case "6":
                output = Integer.toString(new PipBoy(input).getIndexEndOfFirstMarker());
                break;
            case "6b":
                output = Integer.toString(new PipBoy(input).getIndexEndOfFirstMessage());
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
