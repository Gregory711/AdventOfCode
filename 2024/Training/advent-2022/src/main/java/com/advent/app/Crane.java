package com.advent.app;

import java.util.ArrayList;
import java.util.Stack;

public class Crane {
    
    public Crane(ArrayList<String> input) {
        // Find the empty line in between the stacks and the rearrangement procedure
        int emptyLine = 0;
        while (input.get(emptyLine).length() != 0) {
            emptyLine++;
        }
        
        // Create the stacks
        String base = input.get(emptyLine - 1);
        ArrayList<Stack<Character>> stacks = new ArrayList<Stack<Character>>();
        for (int i = 0; i < base.length(); i++) {
            if (base.charAt(i) != ' ') {
                stacks.add(new Stack<Character>());
            }
        }
    }
}
