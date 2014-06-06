package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.io.*;
import java.net.*;
import itm.image.*;
import itm.model.*;
import itm.util.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!--\n");
      out.write("/*******************************************************************************\n");
      out.write(" This file is part of the WM.II.ITM course 2014\n");
      out.write(" (c) University of Vienna 2009-2014\n");
      out.write(" *******************************************************************************/\n");
      out.write("-->\n");
      out.write("<html>\n");
      out.write("\n");
      out.write("<head>\n");
      out.write("\n");
      out.write("<script\n");
      out.write("\tsrc=\"//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js\"></script>\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("          \t\t\t\n");
      out.write("    $(document).ready(function() {\n");
      out.write("    \t$(\"#image\").mouseover(function() {\n");
      out.write("    \t\t$(\"#hist\").show();\n");
      out.write("    \t});\n");
      out.write("    \t\n");
      out.write("    \t$(\"#image\").mouseout(function(){\n");
      out.write("    \t\t$(\"#hist\").hide();\n");
      out.write("    \t});\n");
      out.write("    });\n");
      out.write("\n");
      out.write("    </script>\n");
      out.write("\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("\t<h1>Welcome to the ITM media library</h1>\n");
      out.write("\t<a href=\"infovis.jsp\">infovis</a>\n");
      out.write("\n");
      out.write("\t");

    // get the file paths - this is NOT good style (resources should be loaded via inputstreams...)
    // we use it here for the sake of simplicity.
    String basePath = getServletConfig().getServletContext().getRealPath( "media"  );
    if ( basePath == null )
    	throw new NullPointerException( "could not determine base path of media directory! please set manually in JSP file!" );
    File base = new File( basePath );
    File imageDir = new File( basePath, "img");
    File audioDir = new File( basePath, "audio");
    File videoDir = new File( basePath, "video");
    File metadataDir = new File( basePath, "md");
    MediaFactory.init( imageDir, audioDir, videoDir, metadataDir );
            
   	ImageHistogramGenerator histogramGenerator = new ImageHistogramGenerator();
   	histogramGenerator.batchProcessImages(imageDir, metadataDir, 256); 
   	
   	// get all media objects
    ArrayList<AbstractMedia> media = MediaFactory.getMedia();
            
    int c=0; // counter for rowbreak after 4 thumbnails.
	// iterate over all available media objects
    for ( AbstractMedia medium : media ) {
    	c++;
    
      out.write("\n");
      out.write("\t<div style=\"width: 300px; height: 300px; padding: 10px; float: left;\">\n");
      out.write("\t\t");

            
    	// handle images
    	if ( medium instanceof ImageMedia ) {
   			// ***************************************************************
    		//  Fill in your code here!
    		// ***************************************************************
                    
    		// display image thumbnail and metadata
    		ImageMedia img = (ImageMedia) medium;
					
    		// show the histogram of the image on mouse-over      
    		
    
      out.write("\n");
      out.write("\n");
      out.write("\t\t<img src=\"media/md/");
      out.print( img.getInstance().getName() );
      out.write(".hist.png\"\n");
      out.write("\t\t\tid=\"hist\" style=\"display: none;\" />\n");
      out.write("\n");
      out.write("\n");
      out.write("\t\t<div style=\"width: 200px; height: 200px; padding: 10px;\">\n");
      out.write("\t\t\t<a id=\"image\" href=\"media/img/");
      out.print( img.getInstance().getName());
      out.write("\">\n");
      out.write("\t\t\t\t<img src=\"media/md/");
      out.print( img.getInstance().getName() );
      out.write(".thumb.png\"\n");
      out.write("\t\t\t\tborder=\"0\" />\n");
      out.write("\t\t\t</a>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div>\n");
      out.write("\t\t\tName:\n");
      out.write("\t\t\t");
      out.print( img.getName() );
      out.write("<br /> Dimensions:\n");
      out.write("\t\t\t");
      out.print( img.getWidth() );
      out.write('x');
      out.print( img.getHeight() );
      out.write("px<br /> Tags:\n");
      out.write("\t\t\t");
 for ( String t : img.getTags() ) { 
      out.write("<a href=\"tags.jsp?tag=");
      out.print( t );
      out.write('"');
      out.write('>');
      out.print( t );
      out.write("</a>\n");
      out.write("\t\t\t");
 } 
      out.write("<br />\n");
      out.write("\t\t</div>\n");
      out.write("\n");
      out.write("\t\t");
  
		} else if( medium instanceof AudioMedia ) {
        	// display audio thumbnail and metadata
            AudioMedia audio = (AudioMedia) medium;
	
      out.write("\n");
      out.write("\t\t<div style=\"width: 200px; height: 200px; padding: 10px;\">\n");
      out.write("\t\t\t<br />\n");
      out.write("\t\t\t<br />\n");
      out.write("\t\t\t<br />\n");
      out.write("\t\t\t<br />\n");
      out.write("\t\t\t<embed src=\"media/md/");
      out.print( audio.getInstance().getName() );
      out.write(".wav\"\n");
      out.write("\t\t\t\tautostart=\"false\" width=\"150\" height=\"30\" />\n");
      out.write("\t\t\t<br /> <a href=\"media/audio/");
      out.print( audio.getInstance().getName());
      out.write("\">\n");
      out.write("\t\t\t\tDownload ");
      out.print( audio.getInstance().getName());
      out.write("\n");
      out.write("\t\t\t</a>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div>\n");
      out.write("\t\t\tName:\n");
      out.write("\t\t\t");
      out.print( audio.getName() );
      out.write("<br /> Duration:\n");
      out.write("\t\t\t");
      out.print( audio.getDuration() );
      out.write("<br /> Tags:\n");
      out.write("\t\t\t");
 for ( String t : audio.getTags() ) { 
      out.write("<a\n");
      out.write("\t\t\t\thref=\"tags.jsp?tag=");
      out.print( t );
      out.write('"');
      out.write('>');
      out.print( t );
      out.write("</a>\n");
      out.write("\t\t\t");
 } 
      out.write("<br />\n");
      out.write("\t\t</div>\n");
      out.write("\t\t");
  
		} else if ( medium instanceof VideoMedia ) {
        	// handle videos thumbnail and metadata...
        	VideoMedia video = (VideoMedia) medium;
    
      out.write("\n");
      out.write("\t\t<div style=\"width: 200px; height: 200px; padding: 10px;\">\n");
      out.write("\t\t\t<a href=\"media/video/");
      out.print( video.getInstance().getName());
      out.write("\"> <object\n");
      out.write("\t\t\t\t\twidth=\"200\" height=\"200\">\n");
      out.write("\t\t\t\t\t<param name=\"movie\"\n");
      out.write("\t\t\t\t\t\tvalue=\"media/md/");
      out.print( video.getInstance().getName() );
      out.write("_thumb.swf\">\n");
      out.write("\t\t\t\t\t<embed\n");
      out.write("\t\t\t\t\t\tsrc=\"media/md/");
      out.print( video.getInstance().getName() );
      out.write("_thumb.swf\"\n");
      out.write("\t\t\t\t\t\twidth=\"200\" height=\"200\">\n");
      out.write("\t\t\t\t\t</embed>\n");
      out.write("\t\t\t\t</object>\n");
      out.write("\n");
      out.write("\t\t\t</a>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div>\n");
      out.write("\t\t\tName: <a href=\"media/video/");
      out.print( video.getInstance().getName());
      out.write('"');
      out.write('>');
      out.print( video.getName() );
      out.write("</a><br />\n");
      out.write("\t\t\tTags:\n");
      out.write("\t\t\t");
 for ( String t : video.getTags() ) { 
      out.write("<a\n");
      out.write("\t\t\t\thref=\"tags.jsp?tag=");
      out.print( t );
      out.write('"');
      out.write('>');
      out.print( t );
      out.write("</a>\n");
      out.write("\t\t\t");
 } 
      out.write("<br />\n");
      out.write("\t\t</div>\n");
      out.write("\t\t");
  
     	} else {
        
     	}
	
      out.write("\n");
      out.write("\t</div>\n");
      out.write("\t");

            if ( c % 4 == 0 ) {
    
      out.write("\n");
      out.write("\t<div style=\"clear: left\" /></div>\n");
      out.write("\t");

            }

	} // for 
                
    
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
