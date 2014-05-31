package itm.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public class ImageUtil {
	
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
}
