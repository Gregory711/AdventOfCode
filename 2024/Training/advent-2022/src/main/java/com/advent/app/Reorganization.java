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

        public char getErrorItem() {
            HashSet<Character> items = new HashSet<Character>();
            for (int i = 0; i < compartments[0].length(); i++) {
                items.add(compartments[0].charAt(i));
            }
            for (int i = 0; i < compartments[1].length(); i++) {
                if (items.contains(compartments[1].charAt(i))) {
                    return compartments[1].charAt(i);
                }
            }
            return ' ';
        }
    }

    public Reorganization(ArrayList<String> input) {

    }
}