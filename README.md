![Routine-Image-Processor Banner](assets/my-banner.png)

# Routine-Image-Processor (RIP)

An ImageJ/Fiji plugin for automated batch processing of microscopy images. The plugin runs a full pipeline — converting raw microscopy files, generating projections, cropping, applying LUTs, and exporting RGB images — through a simple guided interface.

The point of this plugin is not to do anything radical. The goal is two-fold: 
- Speed up routine processing of raw microscopy data to presentable data
- Make that processing repeatable

---

## Requirements

- [Fiji](https://fiji.sc/) (the ImageJ distribution with Bio-Formats pre-installed)
- Java 8 or later (bundled with Fiji)

---

## Installation

1. Download the latest `Routine_Image_Processor.jar` from the [Releases](https://github.com/jak18015/Routine-Image-Processor/releases) page.
2. Copy the JAR into your Fiji plugins folder:
   - **Mac:** `Fiji.app/plugins/`
   - **Windows:** `Fiji\plugins\`
   - **Linux:** `Fiji.app/plugins/`
3. Restart Fiji.
4. The plugin will appear under **Plugins → Routine Image Processor**.

---

## Usage

### 1. Prepare your folder structure

Before running, organize your raw images into a working directory. The plugin expects a `raw/` subfolder containing your microscopy files:

```text
my_experiment/
└── raw/
    ├── image1.nd2
    ├── image2.czi
    └── ...
```

### 2. Run the plugin

Launch via **Plugins → Routine Image Processor**. A dialog will appear prompting you to:

- Select your working directory
- Choose which pipeline steps to run

### 3. Pipeline steps

| Step | Description | Input | Output |
|---|---|---|---|
| **Batch Convert** | Converts raw microscopy files to TIFF | `raw/` | `tif/` |
| **Interactive Projection** | Generates Z-projections | `tif/` | `prj/` |
| **Crop** | Applies standard cropping | `prj/` | `crop/` |
| **Set LUTs** | Applies lookup tables to channels | `crop/` | `crop/` |
| **Split to RGB** | Exports RGB channel images | `crop/` | `rgb/` |

---

## Building from Source

**Prerequisites:** [Maven](https://maven.apache.org/) and Java 8+

```bash
# Clone the repository
git clone [https://github.com/jak18015/Routine-Image-Processor.git](https://github.com/jak18015/Routine-Image-Processor.git)
cd Routine-Image-Processor

# Build the JAR
mvn package

# The JAR will be in the target/ folder
cp target/Routine_Image_Processor-*.jar /path/to/Fiji.app/plugins/
```
License
MIT License. See LICENSE for details.
