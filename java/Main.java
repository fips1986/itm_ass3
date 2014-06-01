import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import itm.model.AbstractMedia;
import itm.model.AudioMedia;
import itm.model.MediaFactory;


public class Main {
	
	public static void main(String args[]) throws IOException {
		
		String[] dirs = new String[4];
		
		dirs[0] = "/home/philipp/Studium/2014_SS/ITM/assignment3/webapps/itm/media/img";
		dirs[1] = "/home/philipp/Studium/2014_SS/ITM/assignment3/webapps/itm/media/audio";
		dirs[2] = "/home/philipp/Studium/2014_SS/ITM/assignment3/webapps/itm/media/video";
		dirs[3] = "/home/philipp/Studium/2014_SS/ITM/assignment3/webapps/itm/media/md";
		
		File[] files = new File[4];
		
		files[0] = new File(dirs[0]);
		files[1] = new File(dirs[1]);
		files[2] = new File(dirs[2]);
		files[3] = new File(dirs[3]);
		
		MediaFactory.init(files[0], files[1], files[2], files[3]);
		
		ArrayList<AbstractMedia> media = new ArrayList<AbstractMedia>();
		try {
			media = MediaFactory.getMedia();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(AbstractMedia medium : media)
			if(medium instanceof AudioMedia)
				System.out.println(medium.getName());
		
		System.out.println(media.size());
	}
}
