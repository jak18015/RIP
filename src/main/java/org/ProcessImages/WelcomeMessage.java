package org.ProcessImages;

import ij.gui.GenericDialog;

public class WelcomeMessage {
    static UserSettings show() {
        GenericDialog gd = new GenericDialog("RIP (Routine Image Processor)");
        gd.addMessage(
                "<html>" +
                        "The source folder contains the subdirectories storing your folders.<br><br>" +
                        "You need a folder named <b>'raw'</b> containing all the raw image files, the other folders will be created if needed.<br>" +
                        "Example:<br>" +
                        "<blockquote><code>Images/</code> contains <code>nd2/</code></blockquote>" +
                        "Input the path for <code>Images/</code></html>"
        );
        gd.setInsets(10,0,0);
        gd.addDirectoryField("Source Folder", "");
        gd.setInsets(10,0,10);
        gd.addMessage(
                "<html>" +
                        "Check the boxes for the processes you want to run" +
                        "</html>"
        );
        gd.setInsets(0, 0, 0);
        gd.addCheckbox("Batch Convert Raw images -> TIF", false);
        gd.setInsets(0, 0, 0);
        gd.addCheckbox("Interactive Projection", false);
        gd.setInsets(0, 0, 0);
        gd.addCheckbox("Crop", false);
        gd.setInsets(0, 0, 0);
        gd.addCheckbox("Set LUTs", false);
        gd.setInsets(0, 0, 0);
        gd.addCheckbox("Split to RGB", false);

        // --- NEW: SCALE BAR GUI SECTION ---
        gd.setInsets(15, 0, 5);
        gd.addMessage("<html><b>---- Split to RGB Options ----</b></html>");

        gd.setInsets(0, 0, 0);
        gd.addCheckbox("Add Scale Bar to Merged Images", false);

        gd.setInsets(5, 0, 0);
        gd.addNumericField("Scale Bar Width (microns):", 20, 1);

        String[] locations = {"Lower Right", "Lower Left", "Upper Right", "Upper Left"};
        gd.setInsets(5, 0, 0);
        gd.addChoice("Scale Bar Location:", locations, "Lower Right");
        // ----------------------------------

        gd.showDialog();

        UserSettings settings = new UserSettings();
        if (gd.wasCanceled()) {
            settings.canceled = true;
            return settings;
        }

        // Harvesting core settings
        settings.dir = gd.getNextString();
        settings.isConverting = gd.getNextBoolean();
        settings.isProjecting = gd.getNextBoolean();
        settings.isCropping = gd.getNextBoolean();
        settings.isLutting = gd.getNextBoolean();
        settings.isSplitting = gd.getNextBoolean();

        // --- NEW: HARVEST SCALE BAR SETTINGS ---
        // Must strictly follow the exact order they were declared above!
        settings.addScaleBar = gd.getNextBoolean();
        settings.scaleBarWidth = gd.getNextNumber();
        settings.scaleBarLocation = gd.getNextChoice();
        // ----------------------------------------

        settings.canceled = false;

        return settings;
    }
}