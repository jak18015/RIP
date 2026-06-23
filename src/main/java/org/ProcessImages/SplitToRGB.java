package org.ProcessImages;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.ChannelSplitter;
import java.io.File;

public class SplitToRGB {

    public void split(String inputDir, String outputDir, UserSettings settings) {
        if (!inputDir.endsWith(File.separator)) inputDir += File.separator;
        if (!outputDir.endsWith(File.separator)) outputDir += File.separator;

        DirChecker.check(outputDir);

        File dir = new File(inputDir);
        String[] list = dir.list((d, name) -> name.toLowerCase().endsWith(".tif"));

        if (list == null || list.length == 0) {
            IJ.log("SplitToRGB: No TIFF files found in " + inputDir);
            return;
        }

        for (String fileName : list) {
            ImagePlus imp = IJ.openImage(inputDir + fileName);

            if (imp == null) {
                IJ.log("Could not open: " + fileName);
                continue;
            }

            String baseName = fileName.substring(0, fileName.lastIndexOf("."));

            // --- STEP 1: SPLIT CHANNELS FIRST (WHILE IMP IS PRISTINE) ---
            // Extract the individual channels before the scale bar command ever touches 'imp'
            ImagePlus[] channels = ChannelSplitter.split(imp);

            // Save the clean single channels out
            for (int i = 0; i < channels.length; i++) {
                ImagePlus ch = channels[i];
                ImagePlus rgbCh = ch.flatten(); // This will now be 100% clean

                String savePath = outputDir + baseName + "_C" + (i + 1) + ".tif";
                IJ.saveAs(rgbCh, "Tiff", savePath);

                rgbCh.close();
                ch.close();
            }

            // --- STEP 2: ADD SCALE BAR AND CREATE MERGE ---
            // Now it is perfectly safe to apply the scale bar to 'imp'
            if (settings.addScaleBar) {
                imp.setSlice(1);
                String scaleBarArgs = "width=" + settings.scaleBarWidth +
                        " height=4 font=14 color=White background=None " +
                        "location=[" + settings.scaleBarLocation + "] bold";
                IJ.run(imp, "Scale Bar...", scaleBarArgs);
            }

            ImagePlus mergedRgb = imp.flatten();
            IJ.saveAs(mergedRgb, "Tiff", outputDir + baseName + "_merge.tif");

            // Clean up the merge and primary image resources
            mergedRgb.close();
            imp.close();

            IJ.log("Exported complete set for: " + fileName);
        }
        IJ.log("RGB Export Complete.");
    }
}