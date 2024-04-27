package com.advent.app;

import java.util.ArrayList;

public class Problem {

    private final int day;
    private final boolean isTest;
    private ArrayList<String> input;

    public Problem(int day, boolean isTest, ArrayList<String> input) {
        this.day = day;
        this.isTest = isTest;
        this.input = input;
    }
    
}
