package org.ProcessImages;

import ij.IJ;
import ij.ImagePlus;
import java.io.File;

public class SplitToRGB {
    /**
     * Takes multi-channel TIFFs with LUTs applied and "flattens" them
     * into standard RGB images for presentations/publications.
     */
    public void split(String inputDir, String outputDir) {
        // Ensure paths are formatted correctly
        if (!inputDir.endsWith(File.separator)) inputDir += File.separator;
        if (!outputDir.endsWith(File.separator)) outputDir += File.separator;

        // Make sure the output folder exists
        DirChecker.check(outputDir);

        File dir = new File(inputDir);
        String[] list = dir.list((d, name) -> name.toLowerCase().endsWith(".tif"));

        if (list == null || list.length == 0) {
            IJ.log("SplitToRGB: No TIFF files found in " + inputDir);
            return;
        }

        for (String fileName : list) {
            IJ.showStatus("Exporting RGB: " + fileName);
            ImagePlus imp = IJ.openImage(inputDir + fileName);

            if (imp == null) {
                IJ.log("Could not open: " + fileName);
                continue;
            }

            // 1. "Flatten" creates a new RGB ImagePlus
            // This captures the Magenta/Green/Cyan LUTs and Brightness/Contrast exactly
            ImagePlus rgb = imp.flatten();

            // 2. Save as a standard 24-bit RGB TIFF
            IJ.saveAs(rgb, "Tiff", outputDir + fileName);

            // 3. Clean up memory
            rgb.close();
            imp.close();
            IJ.log("Exported RGB: " + fileName);
        }
        IJ.log("RGB Export Complete.");
    }
}