package itm.image;

/*******************************************************************************
    This file is part of the ITM course 2014
    (c) University of Vienna 2009-2014
 *******************************************************************************/


import itm.util.Histogram;
import itm.util.ImageUtil;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class creates color and grayscale histograms for various images.
 * It can be called with 3 parameters, an input filename/directory, an output directory and a various bin/interval size.
 * It will read the input image(s), count distinct pixel values and then plot the histogram.
 * 
 * If the input file or the output directory do not exist, an exception is thrown.
 */
public class ImageHistogramGenerator {

	/**
	 *  Constructor.
	 */
	public ImageHistogramGenerator() {
	}

	/**
	 * Processes an image directory in a batch process.
	 * @param input a reference to the input image file
	 * @param output a reference to the output directory
	 * @param bins the histogram interval
	 * @return a list of the created files
	 */
	public ArrayList<File> batchProcessImages(File input, File output, int bins) throws IOException {
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
					File result = processImage(f, output, bins);
					System.out.println("converted " + f + " to " + result);
					ret.add(result);
				} catch (Exception e0) {
					System.err.println("Error converting " + input + " : " + e0.toString());
				}
			}
		} else {
			try {
				File result = processImage(input, output, bins);
				System.out.println("created " + input + " for " + result);
				ret.add(result);
			} catch (Exception e0) { System.err.println("Error creating histogram from " + input + " : " + e0.toString()); }
		} 
		return ret;
	}  

	/**
	 * Processes the passed input image and stores it to the output directory.
	 * @param input a reference to the input image file
	 * @param output a reference to the output directory
	 * @param bins the histogram interval
	 * already existing files are overwritten automatically
	 */   
	protected File processImage(File input, File output, int bins) throws IOException, IllegalArgumentException {
		if (!input.exists()) 
			throw new IOException("Input file " + input + " was not found!");
		if (input.isDirectory()) 
			throw new IOException("Input file " + input + " is a directory!");
		if (!output.exists()) 
			throw new IOException("Output directory " + output + " not found!");
		if (!output.isDirectory()) 
			throw new IOException(output + " is not a directory!");

		// compose the output file name from the absolute path, a path separator and the original filename
		File outputFile = new File(output, input.getName() + ".hist.png");

		// load the input image
		BufferedImage image = ImageUtil.load(input);

		// get the color model of the image and the amount of color components
		int numColorComponents = image.getColorModel().getNumColorComponents();

		// initiate a Histogram[color components] [bins]
		Histogram hist = new Histogram(numColorComponents, bins);

		// read the pixel values and extract the color information
		int[][] histArray = calculateHistArray(image, bins);

		// fill the array setHistogram(histArray)
		hist.setHistogram(histArray);

		// plot the histogram, try different dimensions for better visualization
		BufferedImage histImage = hist.plotHistogram(512, 256);

		// encode and save the image as png 
		ImageUtil.savePNG(histImage, outputFile);
		
		return outputFile;
	}


	/**
        Main method. Parses the commandline parameters and prints usage information if required.
	 */
	public static void main(String[] args) throws Exception
	{
		if (args.length < 3) {
			System.out.println("usage: java itm.image.ImageHistogramGenerator <input-image> <output-directory> <bins>");
			System.out.println("usage: java itm.image.ImageHistogramGenerator <input-directory> <output-directory> <bins>");
			System.out.println("");
			System.out.println("bins:default 256");
			System.exit(1);
		}
		// read params
		File fi = new File(args[0]);
		File fo = new File(args[1]);
		int bins = Integer.parseInt(args[2]);
		ImageHistogramGenerator histogramGenerator = new ImageHistogramGenerator();
		histogramGenerator.batchProcessImages(fi, fo, bins);        
	}
	
	private int[][] calculateHistArray(BufferedImage image, int bins) {
		int numColorComponents = image.getColorModel().getNumColorComponents();
		
		// create a histogram array histArray[color components][bins]
		int[][] histArray = new int[numColorComponents][bins];
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				if (numColorComponents == 1) {
					//treat as greyscale
					int grey = image.getRGB(j, i) & 0xFF;
					histArray[0][grey]++;
					
				} else if (numColorComponents >= 3) {
					//treat as RGB
					Color pixel = new Color(image.getRGB(j, i));
					
					int red = pixel.getRed();
					int green = pixel.getGreen();
					int blue = pixel.getBlue();
					
					histArray[0][red]++;
					histArray[1][green]++;
					histArray[2][blue]++;
					
				} else {
					throw new IllegalArgumentException("Invalid number of color components");
				}
			}
		}
		
		return histArray;
	}
}
