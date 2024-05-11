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

    private static int getPriority(char item) {
        if (Character.isLowerCase(item)) {
            return item - 'a' + 1; // 1 to 26 inclusive
        } else {
            return item - 'A' + 1 + ('z' - 'a' + 1); // 27 to 52 inclusive
        }
    }

    int prioritySum;

    public Reorganization(ArrayList<String> input, boolean part2) {
        prioritySum = 0;
        if (!part2) {
            ArrayList<Rucksack> rucksacks = new ArrayList<Rucksack>();
            for (int i = 0; i < input.size(); i++) {
                rucksacks.add(new Rucksack(input.get(i)));
            }
            ArrayList<Character> errors = new ArrayList<Character>();
            for (int i = 0; i < rucksacks.size(); i++) {
                errors.add(rucksacks.get(i).getErrorItem());
            }
            for (int i = 0; i < errors.size(); i++) {
                prioritySum += getPriority(errors.get(i));
            }
        } else {
            char tmp;
            for (int i = 0; i < input.size(); i += 3) {
                for (int j = 0; j < input.get(i).length(); j++) {
                    tmp = input.get(i).charAt(j);
                    if (input.get(i+1).indexOf(tmp) != -1 &&
                        input.get(i+2).indexOf(tmp) != -1) {
                        prioritySum += getPriority(tmp);
                        break;
                    }
                }
            }
        }
    }

    public int getPrioritySum() {
        return prioritySum;
    }
}