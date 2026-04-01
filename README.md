# Fiji Image Processor

An ImageJ/Fiji plugin for automated batch processing of microscopy images. The plugin runs a full pipeline — converting raw microscopy files, generating projections, cropping, applying LUTs, and exporting RGB images — through a simple guided interface.\
\
\
The point of this plugin is not to do anything radical. It's goal is two-fold: 
- Speed up routine processing of raw microscopy data to presentable data
- Make that processing repeatable

---

## Requirements

- [Fiji](https://fiji.sc/) (the ImageJ distribution with Bio-Formats pre-installed)
- Java 8 or later (bundled with Fiji)

---

## Installation

1. Download the latest `Fiji_Image_Processor.jar` from the [Releases](https://github.com/jak18015/Fiji_Image_Processor/releases) page.
2. Copy the JAR into your Fiji plugins folder:
   - **Mac:** `Fiji.app/plugins/`
   - **Windows:** `Fiji\plugins\`
   - **Linux:** `Fiji.app/plugins/`
3. Restart Fiji.
4. The plugin will appear under **Plugins → Fiji Image Processor**.

---

## Usage

### 1. Prepare your folder structure

Before running, organize your raw images into a working directory. The plugin expects a `raw/` subfolder containing your microscopy files:

```
my_experiment/
└── raw/
    ├── image1.nd2
    ├── image2.czi
    └── ...
```

The plugin will automatically create the remaining folders as it runs.

### 2. Run the plugin

Launch via **Plugins → Run Image Processor**. A dialog will appear prompting you to:

- Select your working directory
- Choose which pipeline steps to run

### 3. Pipeline steps

Each step can be enabled or disabled independently:

| Step | Description | Input | Output |
|---|---|---|---|
| **Batch Convert** | Converts raw microscopy files to TIFF | `raw/` | `tif/` |
| **Interactive Projection** | Generates Z-projections | `tif/` | `prj/` |
| **Crop** | Applies standard cropping | `prj/` | `crop/` |
| **Set LUTs** | Applies lookup tables to channels | `crop/` | `crop/` |
| **Split to RGB** | Exports RGB channel images | `crop/` | `rgb/` |

### 4. Expected folder structure after a full run

```
my_experiment/
├── raw/        # Your original microscopy files (untouched)
├── tif/        # Converted TIFFs
├── prj/        # Z-projections
├── crop/       # Cropped images
│   └── roi/    # ROIs used to create the crops
└── rgb/        # Final RGB exports
```

---

## Supported File Formats

The plugin supports common microscopy formats including:

- `.nd2` — Nikon
- `.czi` — Zeiss
- `.lif` — Leica
- `.oib` / `.oif` — Olympus
- `.vsi` — Olympus cellSens
- `.ims` — Imaris
- `.tif` / `.tiff` / `.ome.tif` / `.ome.tiff`

---

## Building from Source

If you'd prefer to build the JAR yourself rather than downloading a release:

**Prerequisites:** [Maven](https://maven.apache.org/) and Java 8+

```bash
# Clone the repository
git clone https://github.com/jak18015/Fiji_Image_Processor.git
cd Fiji_Image_Processor

# Build the JAR
mvn package

# The JAR will be in the target/ folder
# Copy it to your Fiji plugins directory
cp target/Fiji_Image_Processor-*.jar /path/to/Fiji.app/plugins/
```

Then restart Fiji and the plugin will appear under the Plugins menu.

---

## License

MIT License. See [LICENSE](LICENSE) for details.
