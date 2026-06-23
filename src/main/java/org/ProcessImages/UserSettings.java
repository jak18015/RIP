package org.ProcessImages;

import ij.IJ;

import java.io.File;

public class UserSettings {
    String dir;
    boolean isConverting, isProjecting, isCropping, isLutting, isSplitting, canceled;

    // --- NEW SCALE BAR CONFIGURATIONS ---
    boolean addScaleBar;
    double scaleBarWidth;      // e.g., 20.0 (in physical units like microns)
    String scaleBarLocation;   // e.g., "Lower Right"
}

