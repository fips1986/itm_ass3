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

public final class tags_jsp extends org.apache.jasper.runtime.HttpJspBase
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

       

      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("\t");

        
            String tag = null;

            // ***************************************************************
            //  Fill in your code here!
            // ***************************************************************

            // get "tag" parameter 
            tag = (String) request.getParameter("tag");
            
            // if no param was passed, forward to index.jsp (using jsp:forward)
            if (tag == null) {
            	
      out.write('\n');
      out.write('	');
      if (true) {
        _jspx_page_context.forward("index.jsp");
        return;
      }
      out.write('\n');
      out.write('	');

            }
        		

        
      out.write("\n");
      out.write("\n");
      out.write("\t<h1>\n");
      out.write("\t\tMedia that is tagged with\n");
      out.write("\t\t");
      out.print( tag );
      out.write("</h1>\n");
      out.write("\t<a href=\"index.jsp\">back</a>\n");
      out.write("\n");
      out.write("\t");


            // ***************************************************************
            //  Fill in your code here!
            // ***************************************************************
        
            // get all media objects that are tagged with the passed tag
            String basePath = getServletConfig().getServletContext().getRealPath( "media"  );
    		if ( basePath == null )
    			throw new NullPointerException( "could not determine base path of media directory! please set manually in JSP file!" );
    		File base = new File( basePath );
    		File imageDir = new File( basePath, "img");
    		File audioDir = new File( basePath, "audio");
    		File videoDir = new File( basePath, "video");
    		File metadataDir = new File( basePath, "md");
    		MediaFactory.init( imageDir, audioDir, videoDir, metadataDir );
    		
    		ArrayList<AbstractMedia> media = MediaFactory.getMedia();
            
        	int c = 0;
        
            // iterate over all available media objects and display them
            for(AbstractMedia medium : media) {
            	ArrayList<String> tags = medium.getTags();
            
            
            	for(String s : tags) {
            		
            		if(s.equals(tag)) {
            			c++;
            			
            	
      out.write("\n");
      out.write("                    <div style=\"width: 300px; height: 300px; padding: 10px; float: left;\">\n");
      out.write("                ");

            			
            			if ( medium instanceof ImageMedia ) {
            	   			ImageMedia img = (ImageMedia) medium;
           	    
      out.write("\n");
      out.write("\t\t\t\t\t\t<div style=\"width: 200px; height: 200px; padding: 10px;\">\n");
      out.write("\t\t\t\t\t\t\t<a id=\"image\" href=\"media/img/");
      out.print( img.getInstance().getName());
      out.write("\">\n");
      out.write("\t\t\t\t\t\t\t\t<img src=\"media/md/");
      out.print( img.getInstance().getName() );
      out.write(".thumb.png\"\n");
      out.write("\t\t\t\t\t\t\t\t\tborder=\"0\" />\n");
      out.write("\t\t\t\t\t\t\t</a>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t");
  
            			} else if( medium instanceof AudioMedia ) {
            	        	// display audio thumbnail and metadata
            	            AudioMedia audio = (AudioMedia) medium;
            	
      out.write("\n");
      out.write("\t\t\t\t\t\t<div style=\"width: 200px; height: 200px; padding: 10px;\">\n");
      out.write("\t\t\t\t\t\t\t<br />\n");
      out.write("\t\t\t\t\t\t\t<br />\n");
      out.write("\t\t\t\t\t\t\t<br />\n");
      out.write("\t\t\t\t\t\t\t<br />\n");
      out.write("\t\t\t\t\t\t\t<embed src=\"media/md/");
      out.print( audio.getInstance().getName() );
      out.write(".wav\"\n");
      out.write("\t\t\t\t\t\t\t\tautostart=\"false\" width=\"150\" height=\"30\" />\n");
      out.write("\t\t\t\t\t\t\t<br /> \n");
      out.write("\t\t\t\t\t\t\t<a href=\"media/audio/");
      out.print( audio.getInstance().getName());
      out.write("\">\n");
      out.write("\t\t\t\t\t\t\t\tDownload ");
      out.print( audio.getInstance().getName());
      out.write("\n");
      out.write("\t\t\t\t\t\t\t</a>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t");
  
            			} else if ( medium instanceof VideoMedia ) {
            	        	// handle videos thumbnail and metadata...
            	        	VideoMedia video = (VideoMedia) medium;
            	
      out.write("\n");
      out.write("\t\t\t\t\t\t<div style=\"width: 200px; height: 200px; padding: 10px;\">\n");
      out.write("\t\t\t\t\t\t\t<a href=\"media/video/");
      out.print( video.getInstance().getName());
      out.write("\"> \n");
      out.write("\t\t\t\t\t\t\t\t<object width=\"200\" height=\"200\">\n");
      out.write("\t\t\t\t\t\t\t\t\t<param name=\"movie\"\n");
      out.write("\t\t\t\t\t\t\t\t\t\tvalue=\"media/md/");
      out.print( video.getInstance().getName() );
      out.write("_thumb.swf\">\n");
      out.write("\t\t\t\t\t\t\t\t\t<embed src=\"media/md/");
      out.print( video.getInstance().getName() );
      out.write("_thumb.swf\"\n");
      out.write("\t\t\t\t\t\t\t\t\t\twidth=\"200\" height=\"200\">\n");
      out.write("\t\t\t\t\t\t\t\t\t</embed>\n");
      out.write("\t\t\t\t\t\t\t\t</object>\n");
      out.write("\t\t\t\t\t\t\t</a>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\n");
      out.write("\t\t\t\t");
  
            	     	} else {
            	        
            	     	}
                
                if ( c % 4 == 0 ) {
    				
      out.write("\n");
      out.write("    \t\t\t\t\t<div style=\"clear: left\" /></div>\n");
      out.write("    \t\t\t\t\t<p> ");
      out.print( c );
      out.write(" </p>\n");
      out.write("    \t\t\t\t");

    	            }
                
                
      out.write("\n");
      out.write("                \t</div>\n");
      out.write("                ");

            	
					} // first if
            	} // second for
            	
            	
	           
	
            } // first for
            
                
    
      out.write("\n");
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
