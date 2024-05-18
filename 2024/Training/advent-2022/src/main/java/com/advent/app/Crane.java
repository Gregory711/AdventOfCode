package com.advent.app;

import java.util.ArrayList;
import java.util.Stack;

public class Crane {

    ArrayList<Stack<Character>> stacks;
    
    public Crane(ArrayList<String> input, boolean part2) {
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

        // Read in initial crate stacks
        int layerIndex = emptyLine - 2;
        while (layerIndex >= 0) {
            String layer = input.get(layerIndex);
            for (int i = 1; i < layer.length(); i += 4) {
                if (layer.charAt(i) != ' ') {
                    stacks.get(i / 4).add(layer.charAt(i));
                }
            }
            layerIndex--;
        }

        // Execute the rearrangements
        for (int i = emptyLine + 1; i < input.size(); i++) {
            // "move 1 from 2 to 3" becomes [move, 1, from, 2, to, 3] then [1, 2, 3]
            String[] tmp = input.get(i).split(" ");
            int[] move = new int[3];
            for (int j = 1; j < tmp.length; j += 2) {
                move[j / 2] = Integer.parseInt(tmp[j]);
            }
            // execute the move
            if (!part2) {
                for (int j = 0; j < move[0]; j++) {
                    // minus 1 cause 1 indexing of stacks
                    stacks.get(move[2] - 1).add(stacks.get(move[1] - 1).pop());
                }
            } else {
                // TODO: Implement
            }
        }
    }

    public String getTopCrates() {
        String tops = "";
        for (int i = 0; i < stacks.size(); i++) {
            tops += stacks.get(i).peek();
        }
        return tops;
    }
}
