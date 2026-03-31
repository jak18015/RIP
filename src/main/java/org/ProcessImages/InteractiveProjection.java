package org.ProcessImages;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.NonBlockingGenericDialog;
import ij.plugin.ZProjector;

import java.io.File;

public class InteractiveProjection {
    void project(String inputDir, String outputDir) {
        File dir = new File(inputDir);
        String[] list = dir.list();
        if (list == null) return;

        for (String fileName : list) {
            if (!fileName.toLowerCase().endsWith(".tif") && !fileName.toLowerCase().endsWith(".nd2")) continue;

            ImagePlus imp = IJ.openImage(inputDir + fileName);

            imp.show();
            if (imp.getNChannels() > 1) {
                imp.setDisplayMode(IJ.COMPOSITE);
            }
            // Set location: screenWidth/4, screenHeight/4
            imp.getWindow().setLocation(IJ.getScreenSize().width / 4, IJ.getScreenSize().height / 4);

            int slices = imp.getNSlices();

            // NonBlocking allows user to interact with the image while dialog is open
            NonBlockingGenericDialog gd = new NonBlockingGenericDialog("Interactive Projection");
            gd.addNumericField("Start Slice:", 0);
            gd.addNumericField("Stop Slice:", 0);
            gd.showDialog();

            if (gd.wasCanceled()) {
                imp.close();
                return;
            }

            int start = (int) gd.getNextNumber();
            int stop = (int) gd.getNextNumber();

            if (start == 0 || stop == 0) {
                int currentSlice = imp.getZ(); // Gets the current Z-position
                start = currentSlice;
                stop = currentSlice;
            }

            // Perform Z-Project
            ZProjector zp = new ZProjector(imp);
            zp.setStartSlice(start);
            zp.setStopSlice(stop);
            zp.setMethod(ZProjector.MAX_METHOD);

            ImagePlus proj;
            if (imp.getNChannels() > 1) {
                proj = ZProjector.run(imp, "max", start, stop);
            } else {
                zp.doProjection();
                proj = zp.getProjection();
            }

            if (proj == null) {
                IJ.log("Projection failed for " + fileName);
                continue;
            }

            // 2. Make the output composite so it shows all colors
            if (proj.getNChannels() > 1) {
                proj.setDisplayMode(IJ.COMPOSITE);
            }

            IJ.saveAs(proj, "Tiff", outputDir + fileName);

            proj.close();
            imp.close();
        }
    }
}