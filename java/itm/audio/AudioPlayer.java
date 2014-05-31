package itm.audio;

/*******************************************************************************
 This file is part of the ITM course 2014
 (c) University of Vienna 2009-2014
 *******************************************************************************/

import itm.util.AudioUtil;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Plays an audio file using the system's default sound output device
 * 
 */
public class AudioPlayer {

	/**
	 * Constructor
	 */
	public AudioPlayer() {

	}

	/**
	 * Plays audio data from a given input file to the system's default sound
	 * output device
	 * 
	 * @param input
	 *            the audio file
	 * @throws IOException
	 *             general error when accessing audio file
	 */
	protected void playAudio(File input) throws IOException {

		if (!input.exists())
			throw new IOException("Input file " + input + " was not found!");

		AudioInputStream audio = null;
		try {
			audio = openAudioInputStream(input);
		} catch (UnsupportedAudioFileException e) {
			throw new IOException("could not open audio file " + input
					+ ". Encoding / file format not supported");
		}

		try {
			rawplay(audio);
		} catch (LineUnavailableException e) {
			throw new IOException("Error when playing sound from file "
					+ input.getName() + ". Sound output device unavailable");
		}

		audio.close();

	}

	/**
	 * Decodes an encoded audio file and returns a PCM input stream
	 * 
	 * Supported encodings: MP3, OGG (requires SPIs to be in the classpath)
	 * 
	 * @param input
	 *            a reference to the input audio file
	 * @return a PCM AudioInputStream
	 * @throws UnsupportedAudioFileException
	 *             an audio file's encoding is not supported
	 * @throws IOException
	 *             general error when accessing audio file
	 */
	private AudioInputStream openAudioInputStream(File input)
			throws UnsupportedAudioFileException, IOException {

		return AudioUtil.openDecodedAudioInputStream(input,
				AudioFormat.Encoding.PCM_SIGNED);
	}

	/**
	 * Writes audio data from an AudioInputStream to a SourceDataline
	 * 
	 * @param audio
	 *            the audio data
	 * @throws IOException
	 *             error when writing audio data to source data line
	 * @throws LineUnavailableException
	 *             system's default source data line is not available
	 */
	private void rawplay(AudioInputStream audio) throws IOException,
			LineUnavailableException {

		// ***************************************************************
		// Fill in your code here!
		// ***************************************************************

		AudioFormat format = null;
		DataLine.Info info = null;
		SourceDataLine sdl = null;

		// get audio format
		format = audio.getFormat();

		// get a source data line
		info = new DataLine.Info(SourceDataLine.class, format);
		sdl = (SourceDataLine) AudioSystem.getLine(info);

		// read samples from audio and write them to the data line
		sdl.open(format);
		sdl.start();

		// 100kB Buffer-Size
		byte[] buffer = new byte[102400];
		int bytes_read = 0;

		while (bytes_read != -1) {
			bytes_read = audio.read(buffer, 0, buffer.length);

			if (bytes_read != -1) {
				sdl.write(buffer, 0, bytes_read);
			}
		}

		// properly close the line!;
		sdl.close();
	}

	/**
	 * Main method. Parses the commandline parameters and prints usage
	 * information if required.
	 */
	public static void main(String[] args) throws Exception {

		if (args.length < 1) {
			System.out
					.println("usage: java itm.audio.AudioPlayer <input-audioFile>");
			System.exit(1);
		}
		File fi = new File(args[0]);
		AudioPlayer player = new AudioPlayer();
		player.playAudio(fi);
		System.exit(0);

	}

}
