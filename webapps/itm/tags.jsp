<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="itm.image.*" %>
<%@ page import="itm.model.*" %>
<%@ page import="itm.util.*" %>
<!--
/*******************************************************************************
 This file is part of the WM.II.ITM course 2014
 (c) University of Vienna 2009-2014
 *******************************************************************************/
-->
<%
       
%>
<html>
	<head>
	</head>
    <body>

        
        fill in your code here :)
        <%
        
            String tag = null;

            // ***************************************************************
            //  Fill in your code here!
            // ***************************************************************

            // get "tag" parameter 
            tag = (String) request.getParameter("tag");
            
            // if no param was passed, forward to index.jsp (using jsp:forward)
            if (tag == null) {
            	%> <jsp:forward page="index.jsp"></jsp:forward> <%
            }
        		

        %>

        <h1>Media that is tagged with <%= tag %></h1>
        <a href="index.jsp">back</a>

        <%

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
            	c++;
            	ArrayList<String> tags = medium.getTags();
            	
            	for(String s : tags) {
            		
            		if(s.equals(tag)) {
            			
            			if ( medium instanceof ImageMedia ) {
            	   			ImageMedia img = (ImageMedia) medium;
           	    %>
            	    			<div style="width:200px;height:200px;padding:10px;">
            	                	<a id="image" href="media/img/<%= img.getInstance().getName()%>">
            	                		<img src="media/md/<%= img.getInstance().getName() %>.thumb.png" border="0"/>
            	                	</a>
            	                </div>
            	<%  
            			} else if( medium instanceof AudioMedia ) {
            	        	// display audio thumbnail and metadata
            	            AudioMedia audio = (AudioMedia) medium;
            	%>
            	        		<div style="width:200px;height:200px;padding:10px;">
            	                	<br/><br/><br/><br/>
            	                	<embed src="media/md/<%= audio.getInstance().getName() %>.wav" autostart="false" width="150" height="30" />
            	                	<br/>
            	                	<a href="media/audio/<%= audio.getInstance().getName()%>">
            	                            Download <%= audio.getInstance().getName()%>
            	                    </a>
            	            	</div>
          		<%  
            			} else if ( medium instanceof VideoMedia ) {
            	        	// handle videos thumbnail and metadata...
            	        	VideoMedia video = (VideoMedia) medium;
            	%>
            	            	<div style="width:200px;height:200px;padding:10px;">
            	                	<a href="media/video/<%= video.getInstance().getName()%>">
            	                            
            	                    	<object width="200" height="200">
            	                            <param name="movie" value="media/md/<%= video.getInstance().getName() %>_thumb.swf">
            	                            <embed src="media/md/<%= video.getInstance().getName() %>_thumb.swf" width="200" height="200">
            	                            </embed>
            	                        </object>

            	                    </a>
            	              	</div>
            	               
            		<%  
            	     	} else {
            	        
            	     	}
            		%>
            				
            	    <%
            	            if ( c % 4 == 0 ) {
            	    %>
            	            	<div style="clear:left"/></div>
            	    <%
            	            }
            	%>
            			
                <%
            		}
            	}
            }
                
        %>
        
    </body>
</html>
