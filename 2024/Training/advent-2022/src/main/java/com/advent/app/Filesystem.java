package com.advent.app;

import java.util.ArrayList;
import java.util.Stack;

public class Filesystem {

    private class Directory {

        private String name;
        private ArrayList<Directory> dirs; // contained directories
        private int fileSizeSum; // sum of contained files excluding directories

        public Directory(String name) {
            this.name = name;
            this.dirs = new ArrayList<Directory>();
            this.fileSizeSum = 0;
        }

        public String getName() {
            return this.name;
        }

        public void addDirectory(Directory d) {
            this.dirs.add(d);
        }

        public void addFile(int size) {
            this.fileSizeSum += size;
        }

        // Override equals and hashcode so can put in HashSet

        public boolean equals(Object obj) {
            if (!(obj instanceof Directory)) {
                return false;
            }
            return this.getName() == ((Directory) obj).getName();
        }

        public int hashCode() {
            return this.getName().hashCode();
        }
    }

    private static String getCDDirectory(String cdCMD) {
        return cdCMD.substring("$ cd ".length());
    }
    
    public Filesystem(ArrayList<String> input) {

        Stack<String> currDir = new Stack<String>();
        String cmd;
        boolean inOutput = false;

        for (int i = 0; i < input.size(); i++) {
            // If line begins with $ then it is a command and no longer ls output
            if (input.get(i).charAt(0) == '$') {
                inOutput = false;
            }
            // If not still listing output from ls cmd then intrepret the current cmd
            if (!inOutput) {
                cmd = input.get(i);
                // Parse if cmd is change directory or list
                if (cmd.contains("cd")) {
                    // Parse if cmd is switching to root /, backing out .. or moving in
                    if (cmd.contains("/")) {
                        currDir.clear();
                        currDir.add("/");
                    } else if (cmd.contains("..")) {
                        currDir.pop();
                    } else {
                        currDir.add(getCDDirectory(cmd));
                    }
                } else {
                    inOutput = true; // ls
                }
            } else {
                // Add ls output to current directory
            }
        }
    }
}
