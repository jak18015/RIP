package org.ProcessImages;

import ij.IJ;

import java.io.File;

public class DirChecker {
    static void check(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs(); // "mkdirs" creates the folder AND any missing parent folders
            IJ.log("Created missing directory: " + path);
        } else {IJ.log("Directory exists.");}
    }
}
