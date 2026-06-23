package org.ProcessImages;

import ij.IJ;
import ij.ImagePlus;
import loci.plugins.BF;
import loci.plugins.in.ImporterOptions;

import java.io.File;

public class BatchConverter {
    // Removed 'static' and added the 'boolean testing' parameter to match the call
    void processFiles(String inputDir, String outputDir) {
        // Fix path separators immediately
        if (!inputDir.endsWith(File.separator)) inputDir += File.separator;
        if (!outputDir.endsWith(File.separator)) outputDir += File.separator;

        File dir = new File(inputDir);
        File[] files = dir.listFiles((d, name) -> {
            String lower = name.toLowerCase();
            return lower.endsWith(".nd2")    // Nikon
                    || lower.endsWith(".czi")    // Zeiss
                    || lower.endsWith(".lif")    // Leica
                    || lower.endsWith(".oib")    // Olympus
                    || lower.endsWith(".oif")    // Olympus
                    || lower.endsWith(".vsi")    // Olympus cellSens
                    || lower.endsWith(".ims")    // Imaris
                    || lower.endsWith(".tif")    // TIFF
                    || lower.endsWith(".tiff")
                    || lower.endsWith(".ome.tif")  // OME-TIFF
                    || lower.endsWith(".ome.tiff");
        });

        if (files == null || files.length == 0) {
            IJ.error("No compatible image files found in: " + inputDir);
            return;
        }
        for (File file : files) {
            try {
                IJ.showStatus("Converting: " + file.getName());
                ImporterOptions options = new ImporterOptions();
                options.setId(file.getAbsolutePath());
                options.setAutoscale(true);

                ImagePlus[] imps = BF.openImagePlus(options);
                String fileName = file.getName(); // Extracted for readability

                for (ImagePlus imp : imps) {
                    // Dynamically strip whatever extension exists, then append .tif
                    String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf("."));
                    String savePath = outputDir + nameWithoutExt + ".tif";

                    IJ.saveAs(imp, "Tiff", savePath);
                    imp.close();
                }
            } catch (Exception e) {
                IJ.log("Error on " + file.getName() + ": " + e.getMessage());
            }
        }
        IJ.log("Batch Complete.");
    }
}
