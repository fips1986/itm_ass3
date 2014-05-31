package itm.audio;

/*******************************************************************************
 This file is part of the ITM course 2014
 (c) University of Vienna 2009-2014
 *******************************************************************************/

import itm.util.AudioUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * This class creates acoustic thumbnails from various types of audio files. It
 * can be called with 3 parameters, an input filename/directory and an output
 * directory, and the desired length of the thumbnail in seconds. It will read
 * MP3 or OGG encoded input audio files(s), cut the contained audio data to a
 * given length (in seconds) and saves the acoustic thumbnails to a certain
 * length.
 * 
 * If the input file or the output directory do not exist, an exception is
 * thrown.
 */

public class AudioThumbGenerator {

	private int thumbNailLength = 10; // 10 sec default

	/**
	 * Constructor.
	 */
	public AudioThumbGenerator(int thumbNailLength) {
		this.thumbNailLength = thumbNailLength;
	}

	/**
	 * Processes the passed input audio file / audio file directory and stores
	 * the processed files to the output directory.
	 * 
	 * @param input
	 *            a reference to the input audio file / input directory
	 * @param output
	 *            a reference to the output directory
	 */
	public ArrayList<File> batchProcessAudioFiles(File input, File output)
			throws IOException {
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

				String ext = f.getName()
						.substring(f.getName().lastIndexOf(".") + 1)
						.toLowerCase();
				if (ext.equals("wav") || ext.equals("mp3") || ext.equals("ogg")) {
					try {
						File result = processAudio(f, output);
						System.out.println("converted " + f + " to " + result);
						ret.add(result);
					} catch (Exception e0) {
						System.err.println("Error converting " + f + " : "
								+ e0.toString());
					}

				}

			}
		} else {
			String ext = input.getName()
					.substring(input.getName().lastIndexOf(".") + 1)
					.toLowerCase();
			if (ext.equals("wav") || ext.equals("mp3") || ext.equals("ogg")) {
				try {
					File result = processAudio(input, output);
					System.out.println("converted " + input + " to " + result);
					ret.add(result);
				} catch (Exception e0) {
					System.err.println("Error converting " + input + " : "
							+ e0.toString());
				}

			}

		}
		return ret;
	}

	/**
	 * Processes the passed audio file and stores the processed file to the
	 * output directory.
	 * 
	 * @param input
	 *            a reference to the input audio File
	 * @param output
	 *            a reference to the output directory
	 * @throws UnsupportedAudioFileException
	 */
	protected File processAudio(File input, File output) throws IOException,
			IllegalArgumentException, UnsupportedAudioFileException {
		if (!input.exists())
			throw new IOException("Input file " + input + " was not found!");
		if (input.isDirectory())
			throw new IOException("Input file " + input + " is a directory!");
		if (!output.exists())
			throw new IOException("Output directory " + output + " not found!");
		if (!output.isDirectory())
			throw new IOException(output + " is not a directory!");

		File outputFile = new File(output, input.getName() + ".wav");
		
		// load the input audio file
		AudioInputStream audio = AudioUtil.openDecodedAudioInputStream(input, AudioFormat.Encoding.PCM_SIGNED);
		
		// cut the audio data in the stream to a given length
		AudioInputStream newAudio = AudioUtil.cutAudio(audio, this.thumbNailLength);
		
		// write audio to temp file: workaround for ogg files
		File tempFile = new File(output, input.getName() + ".tmp");
        FileOutputStream tempFOS = new FileOutputStream(tempFile); 
        
        byte[] buffer = new byte[4096];
        int n; // number of bytes read
        
        while ((n = newAudio.read(buffer)) != -1) {
            tempFOS.write(buffer, 0, n);
        }
        
        tempFOS.close();
        
        AudioInputStream tempAIS = new AudioInputStream(new FileInputStream(tempFile), newAudio.getFormat(), newAudio.getFrameLength());
        tempFile.delete();
		
		// save the acoustic thumbnail as WAV file
		AudioSystem.write(tempAIS, AudioFileFormat.Type.WAVE, outputFile);

		return outputFile;
	}

	/**
	 * Main method. Parses the commandline parameters and prints usage
	 * information if required.
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out
					.println("usage: java itm.audio.AudioThumbGenerator <input-audioFile> <output-directory> <length>");
			System.out
					.println("usage: java itm.audio.AudioThumbGenerator <input-directory> <output-directory> <length>");
			System.exit(1);
		}
		
		File fi = new File(args[0]);
		File fo = new File(args[1]);
		Integer length = new Integer(args[2]);
		AudioThumbGenerator audioThumb = new AudioThumbGenerator(
				length.intValue());
		audioThumb.batchProcessAudioFiles(fi, fo);
	}

}
