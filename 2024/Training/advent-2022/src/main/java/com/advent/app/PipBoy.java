package com.advent.app;

import java.util.ArrayList;
import java.util.HashSet;

public class PipBoy {

    int indexEndOfFirstMarker;

    private static boolean allUnique(String str) {
        HashSet<Character> chars = new HashSet<Character>();
        for (Character c : str.toCharArray()) {
            if (chars.contains(c)) {
                return false;
            }
            chars.add(c);
        }
        return true;
    }
    
    public PipBoy(ArrayList<String> input) {
        String buffer = input.get(0);

        for (int i = 3; i < buffer.length(); i++) {
            // If this is the end of the first marker then set the
            // corresponding index and break out of the loop
            if (allUnique(buffer.substring(i - 3, i + 1))) {
                indexEndOfFirstMarker = i;
                break;
            }
        }
    }

    public int getIndexEndOfFirstMarker() {
        return indexEndOfFirstMarker;
    }

}
