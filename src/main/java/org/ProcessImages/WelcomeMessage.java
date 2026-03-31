package org.ProcessImages;

import ij.gui.GenericDialog;

public class WelcomeMessage {
    static UserSettings show() {
        GenericDialog gd = new GenericDialog("Process Images to RGB");
        gd.addMessage(
                "<html>" +
                        "Welcome to the great wide world of Java!<br>" +
                        "The source folder contains the subdirectories storing your folders.<br><br>" +
                        "You need a folder named <b>'nd2'</b> for your ND2 files, the other folders will be created if needed.<br>" +
                        "Example:<br>" +
                        "<blockquote><code>Images/</code> contains <code>nd2/</code></blockquote>" +
                        "Input the path for <code>Images/</code></html>"
        );
        gd.setInsets(10,0,0);
        gd.addDirectoryField("Source Folder", "/Users/jkellerm/Library/CloudStorage/OneDrive-MichiganMedicine/0-active-projects/plvac/2026-03-20_mfs1-halo-dual-labeling/images/");
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

        gd.showDialog();

        UserSettings settings = new UserSettings();
        if (gd.wasCanceled()) {
            settings.canceled = true;
            return settings;
        }

        settings.dir = gd.getNextString();
        settings.isConverting = gd.getNextBoolean();
        settings.isProjecting = gd.getNextBoolean();
        settings.isCropping = gd.getNextBoolean();
        settings.isLutting = gd.getNextBoolean();
        settings.isSplitting = gd.getNextBoolean();
        settings.canceled = false;

        return settings;
    }
}