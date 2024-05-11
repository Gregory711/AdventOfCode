package com.advent.app;

import java.util.ArrayList;
import java.util.HashSet;

public class Reorganization {

    private class Rucksack {
        private String compartments[];

        public Rucksack(String compartments) {
            this.compartments = new String[2];
            int midpoint = compartments.length() / 2;
            this.compartments[0] = compartments.substring(0, midpoint);
            this.compartments[1] = compartments.substring(midpoint, compartments.length());
        }
    }

    public Reorganization(ArrayList<String> input) {

    }
}