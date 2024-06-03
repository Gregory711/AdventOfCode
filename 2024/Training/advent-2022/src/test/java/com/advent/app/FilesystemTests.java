package com.advent.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;

import com.advent.app.Filesystem.Directory;

public class FilesystemTests {

    private static Filesystem fs;

    @BeforeClass
    public static void init() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("$ cd /");
        input.add("$ ls");
        input.add("dir a");
        input.add("123 a.txt");
        input.add("$ cd a");
        input.add("$ ls");
        input.add("dir b");
        input.add("dir c");
        input.add("345 b.txt");
        input.add("$ cd b");
        input.add("$ ls");
        input.add("678 c.txt");
        input.add("$ cd ..");
        input.add("$ cd c");
        input.add("$ ls");
        input.add("dir d");

        fs = new Filesystem(input);
    }

    /**
     * Verifies fs has the visited directories and only them
     */
    @Test
    public void hasCorrectDirectories() {
        HashSet<Directory> dirs = fs.getDirectories();

        assertEquals(dirs.size(), 4);
        assertTrue(fs.fsContainsDirectory("/"));
        assertTrue(fs.fsContainsDirectory("a"));
        assertTrue(fs.fsContainsDirectory("b"));
        assertTrue(fs.fsContainsDirectory("c"));
    }
}
