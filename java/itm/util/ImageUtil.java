package itm.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public class ImageUtil {
	
	public final static int RED = 0;
	public final static int GREEN = 1;
	public final static int BLUE = 2;
	public final static int GREY = 3;
	
	/**
	 * Loads an image from a file
	 */
	public static BufferedImage load(File input) throws IOException {
		try {
			BufferedImage image = ImageIO.read(input);
			
			if (image != null) {
				return image;
			} else {
				throw new IOException("Input is not a valid image file");
			}
		} catch (IOException e) {
			throw new IOException("Could not read image file", e);
		}
	}
	
	/**
	 * Saves the image as BMP
	 */
	public static void saveBMP(BufferedImage image, File output) throws IOException {
		ImageIO.write(image, "bmp", output);
	}
	
	/**
	 * Saves the image as PNG
	 */
	public static void savePNG(BufferedImage image, File output) throws IOException {
		ImageIO.write(image, "png", output);
	}
	
	/**
	 * Saves the image as JPEG with the specified quality
	 */
	public static void saveJPEG(BufferedImage image, float quality, File output) throws IOException {
		ImageWriter iw = ImageIO.getImageWritersByFormatName("jpeg").next();
		iw.setOutput(ImageIO.createImageOutputStream(output));
		
		ImageWriteParam iwp = iw.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(quality);
		
		iw.write(null, new IIOImage(image, null, null), iwp);
	}
	
	/**
	 * Rotates the image 90 degrees clockwise
	 */
	public static BufferedImage rotateLeft(BufferedImage image) {
		BufferedImage canvas = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
		AffineTransform tx = new AffineTransform();
		
		//Schritt 2: verschiebe um die neue Breite nach links
		tx.translate(image.getHeight(), 0);
		//Schritt 1: rotiere um Ursprung
		tx.rotate(Math.PI/2);

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		return op.filter(image, canvas);
	}
	
	/**
	 * Scales the image down to the specified maximum width
	 */
	public static BufferedImage resizeToWidth(BufferedImage image, int maxWidth) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		double imageRatio = 1.0 * imageWidth / imageHeight;
		
		double scalefactor = 1.0 * maxWidth / imageWidth;
		BufferedImage canvas = new BufferedImage(maxWidth, (int) (maxWidth / imageRatio), image.getType());
		
		AffineTransform tx = new AffineTransform();
		
		//Scale
		tx.scale(scalefactor, scalefactor);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		return op.filter(image, canvas);
	}
	
	/**
	 * Scales the image down to the specified maximum dimensions
	 */
	public static BufferedImage shrink(BufferedImage image, int maxWidth, int maxHeight) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		double targetRatio = 1.0 * maxWidth / maxHeight;
		double imageRatio = 1.0 * imageWidth / imageHeight;
		
		double scalefactor = 1.0;
		BufferedImage canvas = null;
		
		if (imageRatio < targetRatio) {
			//shrink height to maxHeight
			scalefactor = 1.0 * maxHeight / imageHeight;
			canvas = new BufferedImage((int) (maxHeight * imageRatio), maxHeight, image.getType());
		} else {
			//shrink width to maxWidth
			scalefactor = 1.0 * maxWidth / imageWidth;
			canvas = new BufferedImage(maxWidth, (int) (maxWidth / imageRatio), image.getType());
		}
		
		AffineTransform tx = new AffineTransform();
		
		//Scale
		tx.scale(scalefactor, scalefactor);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		return op.filter(image, canvas);
	}
	
	/**
	 * Centers the image on a blank canvas with the specified dimensions
	 */
	public static BufferedImage putOnCanvas(BufferedImage image, int width, int height) {
		BufferedImage canvas = new BufferedImage(width, height, image.getType());
		AffineTransform tx = new AffineTransform();
		
		//Zentriere am Canvas
		tx.translate(width/2-image.getWidth()/2, height/2-image.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		return op.filter(image, canvas);
	}
	
	/**
	 * returns the dominant Color of an Image
	 */
	public static int getDominantColor(BufferedImage image) {
		int red = 0;
		int green = 0;
		int blue = 0;
		
		ColorModel model = image.getColorModel();
		model = ColorModel.getRGBdefault();
		
		int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		
		for(int i = 0; i < pixels.length; i++) {
			int tmp_red = model.getRed(pixels[i]);
			int tmp_green = model.getGreen(pixels[i]);
			int tmp_blue = model.getBlue(pixels[i]);
			
			if(tmp_red > tmp_green && tmp_red > tmp_blue)
				red++;
			else if(tmp_green > tmp_red && tmp_green > tmp_blue)
				green++;
			else if(tmp_blue > tmp_red && tmp_blue > tmp_green)
				blue++;
		}
			
		if (red >= green && red >= blue) {
			return RED;
		
		} else if (green >= red && green >= blue) {
			return GREEN;
			
		} else if(blue >= red && blue >= green) {
			return BLUE;
		
		} else 
			return GREY;
	}
}