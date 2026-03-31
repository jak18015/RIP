package org.ProcessImages;

import ij.IJ;
import ij.ImagePlus;

import java.io.File;

public class SetLuts {
    void set(String inputDir) {
        if (!inputDir.endsWith(File.separator)) {
            inputDir += File.separator;
        }
        File dir = new File(inputDir);
        String[] list = dir.list((d, name) -> name.toLowerCase().endsWith(".tif"));
        if (list == null) return;
        for (String fileName : list) {
            ImagePlus imp = IJ.openImage(inputDir + fileName);
            int nChannels = imp.getNChannels();
            // Loop through each channel to set colors and contrast
            for (int c = 1; c <= nChannels; c++) {
                imp.setC(c); // Switch active channel (1-indexed)

                // 1. Set the LUT based on channel index
                if (c == 1) {
                    IJ.run(imp, "Magenta", "");
                } else if (c == 2) {
                    IJ.run(imp, "Green", "");
                } else if (c == 3) {
                    IJ.run(imp, "Cyan", "");
                } else {
                    IJ.run(imp, "Grays", "");
                }

                // 2. Reset Min and Max (equivalent to hitting 'Reset' in B&C)
                imp.getProcessor().resetMinAndMax();
            }

            // 3. Set display mode to Composite so all colors show
            if (nChannels > 1) {
                imp.setDisplayMode(IJ.COMPOSITE);
            }

            // 4. Overwrite and Close
            IJ.saveAsTiff(imp, inputDir + fileName);
            imp.close();
            IJ.log("Set LUTs for " + imp);
        }
    }
}
