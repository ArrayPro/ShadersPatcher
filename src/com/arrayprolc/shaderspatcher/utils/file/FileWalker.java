package com.arrayprolc.shaderspatcher.utils.file;

import java.io.File;
import java.util.ArrayList;

/**
 * 
 * @author "stacker" and Kishan from StackOverflow with modifications by Justin
 *         Source:
 *         http://stackoverflow.com/questions/2056221/recursively-list-files
 *         -in-java
 *
 */
public class FileWalker {
    public ArrayList<File> walk(String path) {
        ArrayList<File> files = new ArrayList<File>();

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null)
            return null;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
                System.out.println("Found Directory: " + f.getAbsoluteFile());
            } else {
                files.add(f);
                System.out.println("Found Shader File: " + f.getAbsoluteFile());
            }
        }
        return files;
    }
}
