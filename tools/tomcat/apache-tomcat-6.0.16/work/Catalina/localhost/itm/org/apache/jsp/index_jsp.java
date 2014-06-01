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
      out.write("<!--\n");
      out.write("/*******************************************************************************\n");
      out.write(" This file is part of the WM.II.ITM course 2014\n");
      out.write(" (c) University of Vienna 2009-2014\n");
      out.write(" *******************************************************************************/\n");
      out.write("-->\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Welcome to the ITM media library</h1>\n");
      out.write("        <a href=\"infovis.jsp\">infovis</a>\n");
      out.write("         \n");
      out.write("        \n");
      out.write("        ");

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
            
            // get all media objects
            ArrayList<AbstractMedia> media = MediaFactory.getMedia();
            
			int test = 0;
            
            int c=0; // counter for rowbreak after 3 thumbnails.
            // iterate over all available media objects
            for ( AbstractMedia medium : media ) {
                c++;
                
      out.write("\n");
      out.write("                    <div style=\"width:300px;height:300px;padding:10px;float:left;\">\n");
      out.write("                ");

            
                // handle images
                if ( medium instanceof ImageMedia ) {
                	// ***************************************************************
                    //  Fill in your code here!
                    // ***************************************************************
                    
                    // show the histogram of the image on mouse-over
                    
                    // display image thumbnail and metadata
                    ImageMedia img = (ImageMedia) medium;
                    
      out.write("\n");
      out.write("                    <div style=\"width:200px;height:200px;padding:10px;\">\n");
      out.write("                        <a href=\"media/img/");
      out.print( img.getInstance().getName());
      out.write("\">\n");
      out.write("                        <img src=\"media/md/");
      out.print( img.getInstance().getName() );
      out.write(".thumb.png\" border=\"0\"/>\n");
      out.write("                        </a>\n");
      out.write("                    </div>\n");
      out.write("                    <div>\n");
      out.write("                        Name: ");
      out.print( img.getName() );
      out.write("<br/>\n");
      out.write("                        Dimensions: ");
      out.print( img.getWidth() );
      out.write('x');
      out.print( img.getHeight() );
      out.write("px<br/>\n");
      out.write("                        Tags: ");
 for ( String t : img.getTags() ) { 
      out.write("<a href=\"tags.jsp?tag=");
      out.print( t );
      out.write('"');
      out.write('>');
      out.print( t );
      out.write("</a> ");
 } 
      out.write("<br/>\n");
      out.write("                    </div>\n");
      out.write("                    ");
  
                    } else 
                if ( medium instanceof AudioMedia ) {
                	test++;
                    // display audio thumbnail and metadata
                    AudioMedia audio = (AudioMedia) medium;
                    
      out.write("\n");
      out.write("                    <div style=\"width:200px;height:200px;padding:10px;\">\n");
      out.write("                        <br/><br/><br/><br/>\n");
      out.write("                        <embed src=\"media/md/");
      out.print( audio.getInstance().getName() );
      out.write(".wav\" autostart=\"false\" width=\"150\" height=\"30\" />\n");
      out.write("                        <br/>\n");
      out.write("                        <a href=\"media/audio/");
      out.print( audio.getInstance().getName());
      out.write("\">\n");
      out.write("                            Download ");
      out.print( audio.getInstance().getName());
      out.write("\n");
      out.write("                        </a>\n");
      out.write("                    </div>\n");
      out.write("                    <div>\n");
      out.write("                        Name: ");
      out.print( audio.getName() );
      out.write("<br/>\n");
      out.write("                        Duration: ");
      out.print( audio.getDuration() );
      out.write("<br/>\n");
      out.write("                        Tags: ");
 for ( String t : audio.getTags() ) { 
      out.write("<a href=\"tags.jsp?tag=");
      out.print( t );
      out.write('"');
      out.write('>');
      out.print( t );
      out.write("</a> ");
 } 
      out.write("<br/>\n");
      out.write("                    </div>\n");
      out.write("                    ");
  
                    } else
                if ( medium instanceof VideoMedia ) {
                    // handle videos thumbnail and metadata...
                    VideoMedia video = (VideoMedia) medium;
                    
      out.write("\n");
      out.write("                    <div style=\"width:200px;height:200px;padding:10px;\">\n");
      out.write("                        <a href=\"media/video/");
      out.print( video.getInstance().getName());
      out.write("\">\n");
      out.write("                            \n");
      out.write("                        <object width=\"200\" height=\"200\">\n");
      out.write("                            <param name=\"movie\" value=\"media/md/");
      out.print( video.getInstance().getName() );
      out.write("_thumb.swf\">\n");
      out.write("                            <embed src=\"media/md/");
      out.print( video.getInstance().getName() );
      out.write("_thumb.swf\" width=\"200\" height=\"200\">\n");
      out.write("                            </embed>\n");
      out.write("                        </object>\n");
      out.write("\n");
      out.write("                        </a>\n");
      out.write("                    </div>\n");
      out.write("                    <div>\n");
      out.write("                        Name: <a href=\"media/video/");
      out.print( video.getInstance().getName());
      out.write('"');
      out.write('>');
      out.print( video.getName() );
      out.write("</a><br/>\n");
      out.write("                        Tags: ");
 for ( String t : video.getTags() ) { 
      out.write("<a href=\"tags.jsp?tag=");
      out.print( t );
      out.write('"');
      out.write('>');
      out.print( t );
      out.write("</a> ");
 } 
      out.write("<br/>\n");
      out.write("                    </div>\n");
      out.write("                    ");
  
                    } else {
                        }

                
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                ");

                    if ( c % 4 == 0 ) {
                
      out.write("\n");
      out.write("                    <div style=\"clear:left\"/>\n");
      out.write("                ");

                        }

                } // for 
                
        
      out.write("\n");
      out.write("       \t<p> ");
      out.print(test );
      out.write(" </p>\n");
      out.write("   \t\t<p> ");
      out.print(media.size() );
      out.write(" </p>\n");
      out.write("       \n");
      out.write("       \n");
      out.write("\t</body>\n");
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
