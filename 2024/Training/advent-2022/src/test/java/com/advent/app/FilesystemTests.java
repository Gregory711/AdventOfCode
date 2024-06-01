package com.advent.app;

import java.util.ArrayList;

import org.junit.Test;

public class FilesystemTests {
    /**
     * Verifies basic filesystem functionality
     */
    @Test
    public void shouldFunction() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("cd /");
        input.add("ls");
        input.add("dir a");
        input.add("123 a.txt");
        input.add("cd a");
        input.add("ls");
        input.add("dir b");
        input.add("dir c");
        input.add("345 b.txt");
        input.add("cd b");
        input.add("ls");
        input.add("678 c.txt");
        input.add("cd ..");
        input.add("cd c");
        input.add("ls");
        input.add("dir d");

        Filesystem fs = new Filesystem(input);
    }
}
