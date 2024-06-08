package com.advent.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.advent.app.Filesystem.Directory;

public class FilesystemTests {

    private static Filesystem fs;
    private static Filesystem fs2;

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
        /**
         * /
         *   a
         *     b
         *       c.txt size 678
         *     c
         *       d
         *     b.txt size 345
         *   a.txt size 123
         * 
         * So / has size 123+345+678=1146, a=678+345=1023, b=678, c=d=0
         */

         ArrayList<String> input2 = new ArrayList<String>();
         input2.add("$ cd /");
         input2.add("$ ls");
         input2.add("dir a");
         input2.add("dir b");
         input2.add("$ cd a");
         input2.add("$ ls");
         input2.add("dir a");
         input2.add("dir b");
         fs2 = new Filesystem(input2);
         /**
          * /
              a
                a
                b
              b

          So five directories: /, /a, /a/a, /a/b/, /b
          */
    }

    /**
     * Verifies fs has the visited directories and only them
     */
    @Test
    public void hasCorrectDirectories() {
        HashMap<String, Directory> dirs = fs.getDirectories();

        assertEquals(4*2, dirs.size());
        assertTrue(dirs.containsKey("/"));
        assertTrue(dirs.containsKey("a"));
        assertTrue(dirs.containsKey("b"));
        assertTrue(dirs.containsKey("c"));
    }

    /**
     * Verifies fs has correct directory sizes
     */
    @Test
    public void hasCorrectDirectorySizes() {
        assertEquals(1146, fs.getDirectorySize("/"));
        assertEquals(1023, fs.getDirectorySize("a"));
        assertEquals(678, fs.getDirectorySize("b"));
        assertEquals(0, fs.getDirectorySize("c"));
        assertEquals(0, fs.getDirectorySize("d"));
    }

    /*
     * Verifies fs2 has the correct directories
     */
    @Test
    public void hasCorrectDirectories2() {
        HashMap<String, Directory> dirs = fs2.getDirectories();

        assertEquals(5*2, dirs.size());
        // TODO: assert individual directories included
    }
}
