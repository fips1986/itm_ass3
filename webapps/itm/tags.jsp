<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.*"%>
<%@ page import="itm.image.*"%>
<%@ page import="itm.model.*"%>
<%@ page import="itm.util.*"%>
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

	<%
		String tag = null;

        // ***************************************************************
        //  Fill in your code here!
        // ***************************************************************

        // get "tag" parameter 
        tag = (String) request.getParameter("tag");
            
        // if no param was passed, forward to index.jsp (using jsp:forward)
        if (tag == null) {
           	%>
			<jsp:forward page="index.jsp"></jsp:forward>
		<% } %>

	<h1>Media that is tagged with <%= tag %></h1>
	<a href="index.jsp">back</a> 
	<br />

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
        
        for(AbstractMedia medium : media) {
        	ArrayList<String> tags = medium.getTags();
            
            for(String s : tags) {
            		
            	if(s.equals(tag)) {
            		c++;
            			
            	%>
                    <div style="width: 300px; height: 300px; padding: 10px; float: left;">
                <%
            			
            		if ( medium instanceof ImageMedia ) {
            	   		ImageMedia img = (ImageMedia) medium;
           	    %>
						<div style="width: 200px; height: 200px; padding: 10px;">
							<a id="image" href="media/img/<%= img.getInstance().getName()%>">
								<img src="media/md/<%= img.getInstance().getName() %>.thumb.png"
									border="0" />
							</a>
						</div>
						<div>
							Name:
							<%= img.getName() %><br /> Dimensions:
							<%= img.getWidth() %>x<%= img.getHeight() %>px<br /> Tags:
							<% for ( String t : img.getTags() ) { %><a href="tags.jsp?tag=<%= t %>"><%= t %></a>
							<% } %><br />
						</div>
				<%  
            		} else if( medium instanceof AudioMedia ) {
            	    	// display audio thumbnail and metadata
            	        AudioMedia audio = (AudioMedia) medium;
            	%>
						<div style="width: 200px; height: 200px; padding: 10px;">
							<br />
							<br />
							<br />
							<br />
							<embed src="media/md/<%= audio.getInstance().getName() %>.wav"
								autostart="false" width="150" height="30" />
							<br /> 
							<a href="media/audio/<%= audio.getInstance().getName()%>">
								Download <%= audio.getInstance().getName()%>
							</a>
						</div>
						<div>
							Name:
								<%= audio.getName() %><br /> Duration:
								<%= audio.getDuration() %><br /> Tags:
								<% for ( String t : audio.getTags() ) { %><a href="tags.jsp?tag=<%= t %>"><%= t %></a>
								<% } %><br />
						</div>
				<%  
            		} else if ( medium instanceof VideoMedia ) {
            	    	// handle videos thumbnail and metadata...
            	        VideoMedia video = (VideoMedia) medium;
            	%>
						<div style="width: 200px; height: 200px; padding: 10px;">
							<a href="media/video/<%= video.getInstance().getName()%>"> 
								<object width="200" height="200">
									<param name="movie"
										value="media/md/<%= video.getInstance().getName() %>_thumb.swf">
									<embed src="media/md/<%= video.getInstance().getName() %>_thumb.swf"
										width="200" height="200">
									</embed>
								</object>
							</a>
						</div>
						<div>
							Name: <a href="media/video/<%= video.getInstance().getName()%>"><%= video.getName() %></a><br />
							Tags:
							<% for ( String t : video.getTags() ) { %><a href="tags.jsp?tag=<%= t %>"><%= t %></a>
							<% } %><br />
						</div>

				<%  
            		} else {
            	        
            	    }
                
            	} // first if
				
            	if ( c % 4 == 0 ) {
				%>
					<div style="clear: left" /></div>
				<% } %>
            		</div>
            	
            	<%
            } // second for
            	
        } // first for
            
    %>

</body>
</html>
