package itm.video;

/*******************************************************************************
 This file is part of the ITM course 2014
 (c) University of Vienna 2009-2014
 *******************************************************************************/

import itm.util.VideoUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;
import com.xuggle.xuggler.Utils;

/**
 * This class reads video files, extracts metadata for both the audio and the
 * video track, and writes these metadata to a file.
 * 
 * It can be called with 3 parameters, an input filename/directory, an output
 * directory and an "overwrite" flag. It will read the input video file(s),
 * retrieve the metadata and write it to a text file in the output directory.
 * The overwrite flag indicates whether the resulting output file should be
 * overwritten or not.
 * 
 * If the input file or the output directory do not exist, an exception is
 * thrown.
 */
public class VideoThumbnailGenerator {
	private final static IPixelFormat.Type DEFAULT_PIXEL_FORMAT_TYPE = IPixelFormat.Type.BGR24;

	/**
	 * Constructor.
	 */
	public VideoThumbnailGenerator() {
	}

	/**
	 * Processes a video file directory in a batch process.
	 * 
	 * @param input
	 *            a reference to the video file directory
	 * @param output
	 *            a reference to the output directory
	 * @param overwrite
	 *            indicates whether existing output files should be overwritten
	 *            or not
	 * @return a list of the created media objects (videos)
	 */
	public ArrayList<File> batchProcessVideoFiles(File input, File output, boolean overwrite) throws IOException {
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

				String ext = f.getName().substring(f.getName().lastIndexOf(".") + 1).toLowerCase();
				if (ext.equals("avi") || ext.equals("swf") || ext.equals("asf") || ext.equals("flv")
						|| ext.equals("mp4"))
					try {
						File result = processVideo(f, output, overwrite);
						System.out.println("processed file " + f + " to " + output);
						ret.add(result);
					} catch (Exception e0) {
						System.err.println("Error processing file " + input + " : " + e0.toString());
					}
			}
		} else {

			String ext = input.getName().substring(input.getName().lastIndexOf(".") + 1).toLowerCase();
			if (ext.equals("avi") || ext.equals("swf") || ext.equals("asf") || ext.equals("flv") || ext.equals("mp4"))
				try {
					File result = processVideo(input, output, overwrite);
					System.out.println("processed " + input + " to " + result);
					ret.add(result);
				} catch (Exception e0) {
					System.err.println("Error when creating processing file " + input + " : " + e0.toString());
				}

		}
		return ret;
	}

	/**
	 * Processes the passed input video file and stores a thumbnail of it to the
	 * output directory.
	 * 
	 * @param input
	 *            a reference to the input video file
	 * @param output
	 *            a reference to the output directory
	 * @param overwrite
	 *            indicates whether existing files should be overwritten or not
	 * @return the created video media object
	 */
	protected File processVideo(File input, File output, boolean overwrite) throws Exception {
		if (!input.exists())
			throw new IOException("Input file " + input + " was not found!");
		if (input.isDirectory())
			throw new IOException("Input file " + input + " is a directory!");
		if (!output.exists())
			throw new IOException("Output directory " + output + " not found!");
		if (!output.isDirectory())
			throw new IOException(output + " is not a directory!");

		// create output file and check whether it already exists.
		File outputFile = new File(output, input.getName() + "_thumb.swf");

		IContainer container = VideoUtil.openVideoContainer(input);
		IStream stream = VideoUtil.getFirstVideoStream(container);
		IStreamCoder coder = stream.getStreamCoder();
		int streamID = stream.getId();

		VideoUtil.openCoder(coder);

		// leave resampler null if it does not need resampling
		IVideoResampler resampler = null;
		if (coder.getPixelType() != DEFAULT_PIXEL_FORMAT_TYPE) {
			resampler = VideoUtil.getResampler(coder, DEFAULT_PIXEL_FORMAT_TYPE);
		}

		// extract frames from input video
		// save them in here
		List<BufferedImage> thumbPics = new ArrayList<>();

		// get interval between thumbnail frames in nanoseconds
		// pts = presentation time stamp
		long ptsBetweenFrames = container.getDuration() / 10;
		long ptsLastFrame = -ptsBetweenFrames;

		// iterate trough packets
		IPacket packet = IPacket.make();
		while(container.readNextPacket(packet) >= 0) {
			// only use packets from video stream
			if (packet.getStreamIndex() == streamID) {
				IVideoPicture picture = VideoUtil.getPicture(coder);

				int offset = 0;
				while(offset < packet.getSize()) {
					int bytesDecoded = VideoUtil.decodePicture(coder, picture, packet, offset);
					offset += bytesDecoded;

					if (picture.isComplete()) {
						// resample only if neccessary
						IVideoPicture newPic = picture;
						if (resampler != null) {
							newPic = VideoUtil.resample(picture, resampler);
						}

						if (newPic.getPts() - ptsLastFrame >= ptsBetweenFrames) {
							@SuppressWarnings("deprecation")
							BufferedImage image = Utils.videoPictureToImage(newPic);
							thumbPics.add(image);
							ptsLastFrame += ptsBetweenFrames;
						}
					}
				}
			}
		}

		// create a video writer
		IMediaWriter writer = ToolFactory.makeWriter(outputFile.getAbsolutePath());

		// add a stream with the proper width, height and frame rate
		// framerate 1/1
		writer.addVideoStream(0, 0, IRational.make(1, 1), coder.getWidth(), coder.getHeight());

		// loop: get the frame image, encode the image to the video stream
		int second = 0;
		for (BufferedImage image : thumbPics) {
			writer.encodeVideo(0, image, second, TimeUnit.SECONDS);
			second++;
		}

		// Close the writer
		writer.close();

		return outputFile;
	}

	/**
	 * Main method. Parses the commandline parameters and prints usage
	 * information if required.
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("usage: java itm.video.VideoThumbnailGenerator <input-video> <output-directory>");
			System.out.println("usage: java itm.video.VideoThumbnailGenerator <input-directory> <output-directory>");
			System.exit(1);
		}
		File fi = new File(args[0]);
		File fo = new File(args[1]);
		VideoThumbnailGenerator videoMd = new VideoThumbnailGenerator();
		videoMd.batchProcessVideoFiles(fi, fo, true);
	}
}
