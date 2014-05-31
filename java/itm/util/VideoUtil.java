package itm.util;

import java.io.File;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;

public class VideoUtil {
	public static IContainer openVideoContainer(File file) {
		IContainer container = IContainer.make();
		
		if (container.open(file.getAbsolutePath(), IContainer.Type.READ, null) < 0) {
			throw new IllegalArgumentException("Could not open video file");
		} else {
			return container;
		}
	}
	
	public static IStream getFirstVideoStream(IContainer container) {
		int numStreams = container.getNumStreams();
		for (int i = 0; i < numStreams; i++) {
			IStream stream = container.getStream(i);
			IStreamCoder coder = stream.getStreamCoder();

			if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				return stream;
			}
		}
		
		throw new RuntimeException("No video stream found");
	}
	
	@SuppressWarnings("deprecation")
	public static void openCoder(IStreamCoder coder) {
		if (coder.open() < 0) {
			throw new RuntimeException("Could not open coder");
		}
	}
	
	public static IVideoResampler getResampler(IStreamCoder coder, IPixelFormat.Type pft) {
		return IVideoResampler.make(coder.getWidth(), coder.getHeight(), pft, coder.getWidth(), coder.getHeight(), coder.getPixelType());
	}
	
	public static IVideoPicture getPicture(IStreamCoder coder) {
		return IVideoPicture.make(coder.getPixelType(), coder.getWidth(), coder.getHeight());
	}
	
	public static int decodePicture(IStreamCoder coder, IVideoPicture picture, IPacket packet, int offset) {
		int bytesDecoded = coder.decodeVideo(picture, packet, offset);
		
		if (bytesDecoded < 0) {
			throw new RuntimeException("Could not decode picture");
		} else {
			return bytesDecoded;
		}
	}
	
	public static IVideoPicture resample(IVideoPicture picture, IVideoResampler resampler) {
		IPixelFormat.Type outputPixelFormat = resampler.getOutputPixelFormat();
		IVideoPicture resampledPic = IVideoPicture.make(outputPixelFormat, picture.getWidth(), picture.getHeight());
		
		if ((resampler.resample(resampledPic, picture) < 0) || (resampledPic.getPixelType() != outputPixelFormat)) {
			throw new RuntimeException("Could not resample picture");
		} else {
			return resampledPic;
		}
	}
}
