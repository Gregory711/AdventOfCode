package com.advent.app;

import java.util.ArrayList;
import java.util.Collections;

public class CalorieCounter {

    private ArrayList<String> input;

    public CalorieCounter(ArrayList<String> input) {
        this.input = input;
    }

    public int getMostCalories() {
        int max = 0, elf = 0;
        for (String line : input) {
            if (line.length() > 0) {
                elf += Integer.parseInt(line);
            } else {
                elf = 0;
            }
            max = Math.max(max, elf);
        }
        return max;
    }

    public int getTopThreesCalories() {
        ArrayList<Integer> elves = new ArrayList<>();
        int calories = 0;
        for (String line : input) {
            if (line.length() > 0) {
                calories += Integer.parseInt(line);
            } else {
                elves.add(calories);
                calories = 0;
            }
        }
        elves.add(calories);
        // Sort the list in descending order (greatest to least)
        Collections.sort(elves, Collections.reverseOrder());
        return elves.get(0) + elves.get(1) + elves.get(2);
    }
}
