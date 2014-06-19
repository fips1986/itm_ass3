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

<graphml xmlns="http://graphml.graphdrawing.org/xmlns"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://graphml.graphdrawing.org/xmlns
     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd">
  <graph id="G" edgedefault="undirected">

    <key id="name" for="node" attr.name="name" attr.type="string" />
    <key id="url" for="node" attr.name="url" attr.type="string" />
    <key id="type" for="node" attr.name="type" attr.type="string" />

    <node id="root">
        <data key="type">concept</data>
        <data key="name">Tags</data>
        <data key="url">http://localhost:8080/itm/</data>
    </node>

<%
	// get all media objects
	ArrayList<AbstractMedia> media = MediaFactory.getMedia();

	Map<String, String> tagNodes = new HashMap<String, String>();
	List<String> mediaNodes = new ArrayList<String>();
	List<String> edges = new ArrayList<String>();

	// iterate over all available media objects
	for (int i = 0; i < media.size(); i++) {
		AbstractMedia medium = media.get(i);
		List<String> tags = medium.getTags();
		
		String url = "http://localhost:8080/itm/media"
			+ medium.getInstance().getPath().split("media", 2)[1];
		
		// add media node
		String mediaNode = "<node id='m" + i + "'>"
				+ "<data key='type'>node</data>"
				+ "<data key='name'>" + medium.getName() + "</data>"
				+ "<data key='url'>" + url + "</data>"
				+ "</node>";
		mediaNodes.add(mediaNode);
		
		for (String tag : tags) {
			// add tag nodes if new
			if (!tagNodes.containsKey(tag)) {
				String tagNode = "<node id='" + tag + "'>"
						+ "<data key='type'>concept</data>"
						+ "<data key='name'>" + tag + "</data>"
						+ "<data key='url'>http://localhost:8080/itm/tags.jsp?tag=" + tag + "</data>"
						+ "</node>";
				tagNodes.put(tag, tagNode);
				
				// add root-tag edge
				String edge = "<edge source='root' target='" + tag + "' />";
				edges.add(edge);
			}
			
			// add tag-media edge
			String edge = "<edge source='" + tag + "' target='m" + i + "' />";
			edges.add(edge);
		}
		
		if (tags.size() == 0) {
			// no tags: add root-media edge
			String edge = "<edge source='root' target='m" + i + "' />";
			edges.add(edge);
		}
	}
	
	// print output
	for (String node : tagNodes.values()) {
		out.print(node + '\n');
	}
	
	for (String node : mediaNodes) {
		out.print(node + '\n');
	}
	
	for (String edge : edges) {
		out.print(edge + '\n');
	}
%>

  </graph>
</graphml>