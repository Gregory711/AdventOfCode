package com.advent.app;

import java.util.ArrayList;
import java.util.Stack;

public class Crane {

    ArrayList<Stack<Character>> stacks;
    
    public Crane(ArrayList<String> input) {
        // Find the empty line in between the stacks and the rearrangement procedure
        int emptyLine = 0;
        while (input.get(emptyLine).length() != 0) {
            emptyLine++;
        }
        
        // Create the stacks
        String base = input.get(emptyLine - 1);
        stacks = new ArrayList<Stack<Character>>();
        for (int i = 0; i < base.length(); i++) {
            if (base.charAt(i) != ' ') {
                stacks.add(new Stack<Character>());
            }
        }

        // TODO: Execute the rearrangements
    }

    public String getTopCrates() {
        String tops = "";
        for (int i = 0; i < stacks.size(); i++) {
            tops += stacks.get(i).peek();
        }
        return tops;
    }
}
