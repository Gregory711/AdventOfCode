package com.advent.app;

import java.util.ArrayList;

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
}
