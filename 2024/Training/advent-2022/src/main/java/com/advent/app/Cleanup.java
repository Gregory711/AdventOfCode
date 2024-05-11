package com.advent.app;

import java.util.ArrayList;

import org.apache.commons.lang3.Range;

public class Cleanup {

    int contained, overlapped;

    public Cleanup(ArrayList<String> input) {
        contained = overlapped = 0;
        Range<Integer> a, b;
        String tmp;
        int start, end, dash, comma;
        for (int i = 0; i < input.size(); i++) {
            // Parsing format #-#,#-#
            tmp = input.get(i);
            dash = tmp.indexOf("-");
            comma = tmp.indexOf(",");
            start = Integer.parseInt(tmp.substring(0, dash));
            end = Integer.parseInt(tmp.substring(dash+1, comma));
            a = Range.between(start, end);
            tmp = tmp.substring(comma+1);
            dash = tmp.indexOf("-");
            start = Integer.parseInt(tmp.substring(0, dash));
            end = Integer.parseInt(tmp.substring(dash+1));
            b = Range.between(start, end);

            // Increments if either range completely contains the other
            if (a.containsRange(b) || b.containsRange(a)) {
                contained++;
            }
            // Increments if the ranges overlap at all i.e. contain any of the same values
            if (a.isOverlappedBy(b)) {
                overlapped++;
            }
        }
    }

    public int getContained() {
        return contained;
    }

    public int getOverlapped() {
        return overlapped;
    }
}
