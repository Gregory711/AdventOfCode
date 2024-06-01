package com.advent.app;

import java.util.ArrayList;
import java.util.Stack;

public class Filesystem {
    
    public Filesystem(ArrayList<String> input) {

        Stack<String> currDir = new Stack<String>();
        String cmd;
        boolean inOutput = false;

        for (int i = 0; i < input.size(); i++) {
            // If not still listing output from ls cmd then intrepret the current cmd
            if (!inOutput) {
                cmd = input.get(i);
                // Parse if cmd is change directory or list
                if (cmd.contains("cd")) {
                    // Parse if cmd is switching to root /, backing out .. or moving in
                    if (cmd.contains("/")) {
                        //
                    } else if (cmd.contains("..")) {
                        //
                    } else {
                        //
                    }
                }
            }
        }
    }
}
