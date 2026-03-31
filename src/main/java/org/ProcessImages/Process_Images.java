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

        // Get the working directory
        String root = settings.dir;
        // Add a trailing forward slash if missing from the root
        if (!root.endsWith(File.separator)) root += File.separator;

        // If Batch conversion was checked
        if (settings.isConverting)
            new BatchConverter().processFiles(root + "raw/", root + "tif/");

        // If Interactive projection was checked
        if (settings.isProjecting)
            new InteractiveProjection().project(root + "tif/", root + "prj/");

        // If cropping was checked
        if (settings.isCropping) {
            DirChecker.check(root + "crop/roi/"); // Pre-check the nested folder
            new StandardCrop().crop(root + "prj/", root + "crop/");
        }

        // if setting the LUTs was checked
        if (settings.isLutting)
            new SetLuts().set(root + "crop/");

        // if splitting to RGB was checked
        if (settings.isSplitting)
            new SplitToRGB().split(root + "crop/", root + "rgb/");
    }
}
