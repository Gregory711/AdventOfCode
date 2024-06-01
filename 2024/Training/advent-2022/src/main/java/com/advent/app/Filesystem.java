package com.advent.app;

import java.util.ArrayList;

public class Filesystem {
    
    public Filesystem(ArrayList<String> input) {

        String cmd, currDir;
        boolean inOutput = false;

        for (int i = 0; i < input.size(); i++) {
            // If not still listing output from ls cmd then intrepret the current cmd
            if (!inOutput) {
                cmd = input.get(i);
            }
        }
    }
}
