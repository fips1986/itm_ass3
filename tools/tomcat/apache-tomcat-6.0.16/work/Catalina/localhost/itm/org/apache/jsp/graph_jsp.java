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

public final class graph_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"  \n");
      out.write("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
      out.write("    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n");
      out.write("     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n");
      out.write("  <graph id=\"G\" edgedefault=\"undirected\">\n");
      out.write("\n");
      out.write("    <key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\" />\n");
      out.write("    <key id=\"url\" for=\"node\" attr.name=\"url\" attr.type=\"string\" />\n");
      out.write("    <key id=\"type\" for=\"node\" attr.name=\"type\" attr.type=\"string\" />\n");
      out.write("\n");
      out.write("    <node id=\"root\">\n");
      out.write("        <data key=\"type\">concept</data>\n");
      out.write("        <data key=\"name\">Tags</data>\n");
      out.write("        <data key=\"url\">http://localhost:8080/itm/</data>\n");
      out.write("    </node>\n");
      out.write("\n");

	// get all media objects
	ArrayList<AbstractMedia> media = MediaFactory.getMedia();

	Map<String, String> tagNodes = new HashMap<String, String>();
	List<String> mediaNodes = new ArrayList<String>();
	List<String> edges = new ArrayList<String>();

	// iterate over all available media objects
	for (int i = 0; i < media.size(); i++) {
		AbstractMedia medium = media.get(i);
		List<String> tags = medium.getTags();
		
		// could make problems on Windows
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

      out.write("\n");
      out.write("\n");
      out.write("  </graph>\n");
      out.write("</graphml>");
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
