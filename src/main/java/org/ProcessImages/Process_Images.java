package org.ProcessImages;

import ij.plugin.PlugIn;

import java.io.File;

public class Process_Images implements PlugIn {

    public static void main(String[] args) {
        // Bootstrap ImageJ headlessly so all ij.* classes work
        new ij.ImageJ();

        // Then just call run as normal
        new Process_Images().run("");
    }

    @Override
    public void run(String arg) {
        UserSettings settings = WelcomeMessage.show();

        // if canceled
        if (settings.canceled) return;

        // Get the working directory and ensure trailing separator
        String root = settings.dir;
        if (!root.endsWith(File.separator)) root += File.separator;

        // Track where the latest processed images are located
        String currentInputDir = root + "raw/";

        // 1. Batch Conversion
        if (settings.isConverting) {
            String nextOutputDir = root + "tif/";
            DirChecker.check(currentInputDir);
            DirChecker.check(nextOutputDir);
            new BatchConverter().processFiles(currentInputDir, nextOutputDir);
            currentInputDir = nextOutputDir;
        } else {
            // If skipped, assume the user already has TIFs ready in the tif/ folder
            currentInputDir = root + "tif/";
        }

        // 2. Interactive Projection
        if (settings.isProjecting) {
            String nextOutputDir = root + "prj/";
            DirChecker.check(currentInputDir);
            DirChecker.check(nextOutputDir);
            new InteractiveProjection().project(currentInputDir, nextOutputDir);
            currentInputDir = nextOutputDir;
        }

        // 3. Cropping
        if (settings.isCropping) {
            String nextOutputDir = root + "crop/";
            DirChecker.check(currentInputDir);
            DirChecker.check(nextOutputDir);
            DirChecker.check(nextOutputDir + "roi/"); // Pre-check the nested folder
            new StandardCrop().crop(currentInputDir, nextOutputDir);
            currentInputDir = nextOutputDir;
        }

        // 4. Set LUTs
        if (settings.isLutting) {
            DirChecker.check(currentInputDir);
            new SetLuts().set(currentInputDir);
        }

        // 5. Split to RGB
        if (settings.isSplitting) {
            String nextOutputDir = root + "rgb/";
            DirChecker.check(currentInputDir);
            DirChecker.check(nextOutputDir);
            new SplitToRGB().split(currentInputDir, nextOutputDir, settings);
        }
    }
}