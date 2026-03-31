package org.ProcessImages;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.NonBlockingGenericDialog;
import ij.gui.Roi;
import ij.plugin.frame.RoiManager;

import java.io.File;

public class StandardCrop {
    void crop(String inputDir, String outputDir) {
        RoiManager rm = RoiManager.getRoiManager(); // Singleton instance

        File dir = new File(inputDir);
        String[] list = dir.list((d, name) -> name.toLowerCase().endsWith(".tif"));
        if (list == null) return;

        for (String fileName : list) {
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            String roiPath = outputDir + "roi/" + title + ".roi";


            if (new File(roiPath).exists()) continue;

            // Clean ROI Manager
            rm.reset();

            ImagePlus imp = IJ.openImage(inputDir + fileName);
            imp.show();

            // Create initial rectangle
            int box = 100;

            int w = imp.getWidth();
            int h = imp.getHeight();

            int box_w = (w-box)/2;
            int box_h = (h-box)/2;

            imp.setRoi(new Roi(box_w, box_h, box, box));

            // Nonblocking dialog window
            NonBlockingGenericDialog gd = new NonBlockingGenericDialog("Cropping");
            gd.addMessage("Place box");
            gd.showDialog();

            if (gd.wasCanceled()) {
                imp.close();
                return;
            }

            // Add to manager and rename
            Roi roi = imp.getRoi();
            if (roi != null) {
                roi.setName(title);
                rm.addRoi(roi);
                rm.select(rm.getCount() - 1);

                // Save ROI
                rm.runCommand("Save", roiPath);

                // Crop and Save Image
                ImagePlus cropped = imp.crop();
                IJ.saveAs(cropped, "Tiff", outputDir + fileName);
                cropped.close();
            }

            imp.close();
        }
    }
}

