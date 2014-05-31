package itm.image;

/*******************************************************************************
    This file is part of the ITM course 2014
    (c) University of Vienna 2009-2014
 *******************************************************************************/

import itm.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
    This class converts images of various formats to PNG thumbnails files.
    It can be called with 3 parameters, an input filename/directory, an output directory and a compression quality parameter.
    It will read the input image(s), grayscale and scale it/them and convert it/them to a PNG file(s) that is/are written to the output directory.

    If the input file or the output directory do not exist, an exception is thrown.
 */
public class ImageThumbnailGenerator {

	/**
        Constructor.
	 */
	public ImageThumbnailGenerator() {
		
	}

	/**
	 * Processes an image directory in a batch process.
	 * @param input a reference to the input image file
	 * @param output a reference to the output directory
	 * @param dimx the width of the resulting thumbnail
	 * @param dimy the height of the resulting thumbnail
	 * @param overwrite indicates whether existing thumbnails should be overwritten or not
	 * @return a list of the created files
	 */
	public ArrayList<File> batchProcessImages(File input, File output, int dimx, int dimy, boolean overwrite) throws IOException {
		if (!input.exists()) 
			throw new IOException("Input file " + input + " was not found!");
		if (!output.exists()) 
			throw new IOException("Output directory " + output + " not found!");
		if (!output.isDirectory()) 
			throw new IOException(output + " is not a directory!");

		ArrayList<File> ret = new ArrayList<File>();

		if (input.isDirectory()) {
			File[] files = input.listFiles();
			for (File f : files) {
				try {
					File result = processImage(f, output, dimx, dimy, overwrite);
					System.out.println("converted " + f + " to " + result);
					ret.add(result);
				} catch (Exception e0) {
					System.err.println("Error converting " + input + " : " + e0.toString());
				}
			}
		} else {
			try {
				File result = processImage(input, output, dimx, dimy, overwrite);
				System.out.println("converted " + input + " to " + result);
				ret.add(result);
			} catch (Exception e0) {
				System.err.println("Error converting " + input + " : " + e0.toString());
			}
		} 
		return ret;
	}  

	/**
	 * Processes the passed input image and stores it to the output directory.
	 * This function should not do anything if the outputfile already exists and if the overwrite flag is set to false.
	 * @param input a reference to the input image file
	 * @param output a reference to the output directory
	 * @param dimx the width of the resulting thumbnail
	 * @param dimy the height of the resulting thumbnail
	 * @param overwrite indicates whether existing thumbnails should be overwritten or not
	 */
	protected File processImage(File input, File output, int dimx, int dimy, boolean overwrite) throws IOException, IllegalArgumentException
	{
		if (!input.exists()) 
			throw new IOException("Input file " + input + " was not found!");
		if (input.isDirectory()) 
			throw new IOException("Input file " + input + " is a directory!");
		if (!output.exists()) 
			throw new IOException("Output directory " + output + " not found!");
		if (!output.isDirectory()) 
			throw new IOException(output + " is not a directory!");

		// create outputfilename and check whether thumb already exists
		File outputFile = new File(output, input.getName() + ".thumb.png");
		if (outputFile.exists()) {
			if (!overwrite) {
				return outputFile;
			}
		}

		// load the input image
		BufferedImage image = ImageUtil.load(input);
		
		// convert to RGB
		BufferedImage thumb = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		thumb.getGraphics().drawImage(image, 0, 0, null);
		
		// rotate the image if required - do not crop image parts!
		if (thumb.getHeight() > thumb.getWidth()) {
			thumb = ImageUtil.rotateLeft(thumb);
		}

		// scale the image to a maximum of [ dimx X dimy ] pixels - do not distort!
		if (thumb.getWidth() > dimx) {
			thumb = ImageUtil.resizeToWidth(thumb, dimx);
		}
			
		// if the image is smaller than [ dimx X dimy ] - print it on a [ dim X dim ] canvas!
		if (thumb.getWidth() < dimx || thumb.getHeight() < dimy) {
			thumb = ImageUtil.putOnCanvas(thumb, dimx, dimy);
		}

		// encode and save the image
		ImageUtil.savePNG(thumb, outputFile);

		return outputFile;
	}

	/**
        Main method. Parses the commandline parameters and prints usage information if required.
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("usage: java itm.image.ImageThumbnailGenerator <input-image> <output-directory>");
			System.out.println("usage: java itm.image.ImageThumbnailGenerator <input-directory> <output-directory>");
			System.exit(1);
		}
		File fi = new File(args[0]);
		File fo = new File(args[1]);

		ImageThumbnailGenerator itg = new ImageThumbnailGenerator();
		itg.batchProcessImages(fi, fo, 200, 100, true);
	}
}
