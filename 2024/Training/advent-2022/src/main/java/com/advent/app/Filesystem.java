package com.advent.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Filesystem {

    private HashMap<String, Directory> directories; // names to directories

    public class Directory {

        private ArrayList<String> dirs; // contained directories (names)
        private HashSet<String> files; // files directly contained
        private int fileSizeSum; // sum of contained files excluding directories
        private int directorySizeSum; // sum of contained directories and their contained files

        public Directory() {
            this.dirs = new ArrayList<String>();
            this.files = new HashSet<String>();
            this.fileSizeSum = this.directorySizeSum = 0;
        }

        public boolean containsDirectory(Directory d) {
            return dirs.contains(d);
        }

        public void addDirectory(String d) {
            dirs.add(d);
        }

        public ArrayList<String> getDirectories() {
            return dirs;
        }

        public boolean containsFile(String name) {
            return files.contains(name);
        }

        public void addFile(String name, int size) {
            files.add(name);
            fileSizeSum += size;
        }

        public HashSet<String> getFiles() {
            return files;
        }

        public void addToDirectorySum(int size) {
            this.directorySizeSum += size;
        }

        public int getFileSum() {
            return this.fileSizeSum;
        }

        public int getDirectorySum() {
            return this.directorySizeSum;
        }
    }

    private static String getCDDirectory(String cdCMD) {
        return cdCMD.substring("$ cd ".length());
    }

    private int getSubdirectorySum(String dir) {
        // Calculate the total size of all of the subdirectories in dir
        int sum = 0;
        Directory d = directories.get(dir);
        ArrayList<String> subs = d.getDirectories();
        for (int i = 0; i < subs.size(); i++) {
            String sub = subs.get(i);
            Directory subdir = directories.get(sub);
            if (subdir.getDirectorySum() == 0) {
                subdir.addToDirectorySum(getSubdirectorySum(sub));
            }
            sum += subdir.getDirectorySum();
            sum += subdir.getFileSum();
        }
        return sum;
    }
    
    public Filesystem(ArrayList<String> input) {

        directories = new HashMap<String, Directory>();
        Directory root = new Directory();
        directories.put("/", root); // add root dir
        Stack<Directory> currDir = new Stack<Directory>();
        String cmd, dir;
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
                        currDir.add(root);
                    } else if (cmd.contains("..")) {
                        currDir.pop();
                    } else {
                        dir = getCDDirectory(cmd);
                        if (!directories.containsKey(cmd)) {
                            directories.put(cmd, new Directory());
                        }
                        currDir.add(directories.get(cmd));
                    }
                } else {
                    inOutput = true; // ls
                }
            } else {
                // Add ls output to current directory
                // \s is regex pattern for whitespae (space/tab/newline/etc)
                // + is regex for one or more
                // \ needs escape char which is why there are two
                // so \\s+ is one or more whitespace
                String[] splitOutput = (input.get(i)).split("\\s+");
                if (splitOutput[0].equals("dir")) {
                    // Create directory if it doesn't exist
                    if (!directories.containsKey(splitOutput[1])) {
                        directories.put(splitOutput[1], new Directory());
                    }
                    Directory temp = directories.get(splitOutput[1]);

                    // Add directory as subdirectory to current directory if not already
                    if (!currDir.peek().containsDirectory(temp)) {
                        currDir.peek().addDirectory(splitOutput[1]);
                    }
                } else {
                    int fileSize = Integer.parseInt(splitOutput[0]);
                    String fileName = splitOutput[1];
                    if (!currDir.peek().containsFile(fileName)) {
                        currDir.peek().addFile(fileName, fileSize);
                    }
                }
            }
        }

        // Recursively calculate size of subdirectories
        root.addToDirectorySum(getSubdirectorySum("/"));
    }

    public HashMap<String, Directory> getDirectories() {
        return directories;
    }

    public int getDirectorySize(String d) {
        Directory dir = directories.get(d);
        System.out.println(d + " has file sum: " + dir.getFileSum() + " and directory sum " + dir.getDirectorySum());
        System.out.println("--------------------------------");
        return dir.getFileSum() + dir.getDirectorySum();
    }
}
