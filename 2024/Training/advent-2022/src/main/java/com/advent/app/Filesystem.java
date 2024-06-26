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

        public boolean containsDirectory(String d) {
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
        // Overlooked edge case of same directory name being reused with different parent
        // directory e.g. pizza folder as subfolder in breakfast, lunch, and dinner folders
        Stack<String> currDir = new Stack<String>();
        String currPath = "";
        Stack<Integer> currDirLengths = new Stack<Integer>(); // lengths of individual directory strs
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
                        currDirLengths.clear();
                        currDir.add("/");
                        currPath += "/";
                        currDirLengths.add(1);
                    } else if (cmd.contains("..")) {
                        int removed = currDirLengths.pop();
                        //System.out.println("cd .. at " + currPath);
                        currPath = currPath.substring(0, currPath.length() - removed);
                        //System.out.println("moved to " + currPath);
                    } else {
                        dir = getCDDirectory(cmd);
                        currDirLengths.add(dir.length());
                        currPath += dir;
                        dir = currPath;
                        if (!directories.containsKey(dir)) {
                            directories.put(dir, new Directory());
                        }
                        currDir.add(dir);
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
                Directory curr = directories.get(currDir.peek());
                if (splitOutput[0].equals("dir")) {
                    dir = currPath + splitOutput[1];
                    //System.out.println("ls revealed directory: " + dir);
                    // Create directory if it doesn't exist
                    if (!directories.containsKey(dir)) {
                        directories.put(dir, new Directory());
                    }

                    // Add directory as subdirectory to current directory if not already
                    if (!curr.containsDirectory(dir)) {
                        curr.addDirectory(dir);
                    }
                } else {
                    int fileSize = Integer.parseInt(splitOutput[0]);
                    String fileName = splitOutput[1];
                    if (!curr.containsFile(fileName)) {
                        curr.addFile(fileName, fileSize);
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
        return dir.getFileSum() + dir.getDirectorySum();
    }

    public long getSumOfSmallDirectories() {
        // Returns sum of directories with size <= 100,000 (double counts subs)
        long sum = 0;
        int size;
        for (String dirName : directories.keySet()) {
            size = getDirectorySize(dirName);
            if (size <= 100000) {
                sum += size;
            }
        }
        return sum;
    }

    public int getSizeOfDirectoryToDelete() {
        // returns the size of the smallest directory that leaves <= 40000000 filled in
        // on the 70000000 sized fs to get unused space of >= 30000000
        int minSize = 70000000, dirSize;
        int needToRemove = getDirectorySize("/") - 40000000;
        for (String dirName : directories.keySet()) {
            dirSize = getDirectorySize(dirName);
            if (dirSize >= needToRemove && dirSize < minSize) {
                minSize = dirSize;
            }
        }
        return minSize;
    }
}
