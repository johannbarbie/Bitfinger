package org.bitfinger.resources;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bitfinger.BitfingerServletConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Path(IndexResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class IndexResource {
	public final static String PATH = "/";
	// where to find the templates when running in web container
	public static final String RESOURCE_PATH = "/WEB-INF/templates/";
	// where to find the templates when outside web container
	public static final String LOCAL_RESOURCE_PATH = "src/main/webapp/WEB-INF/templates/";

	final private ServletContext servletContext;
	final private HttpServletRequest httpReq;
	private final Configuration cfg;
	
	@Inject public IndexResource(ServletRequest request,
			ServletContext servletContext) {
		this.httpReq = (HttpServletRequest)request;
		this.servletContext = servletContext;
		cfg = new Configuration();
		if (servletContext == null) {
			try {
				cfg.setDirectoryForTemplateLoading(new File(LOCAL_RESOURCE_PATH));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			cfg.setServletContextForTemplateLoading(servletContext,
					RESOURCE_PATH);
		}
	}

	@GET
	public Response index(@HeaderParam("Accept-Language") String lng){
		Map<String,String> data = new HashMap<>();
		data.put("resPath", BitfingerServletConfig.resPath);
		data.put("basePath", BitfingerServletConfig.basePath);
		data.put("lng", (lng!=null)?lng.split(",")[0]:"en-US");
		String rsp;
		try {
			Template template = cfg.getTemplate("index.html");

			Writer stringWriter = null;

			// create html mail part
			stringWriter = new StringWriter();
			template.process(data, stringWriter);

			stringWriter.flush();
			rsp = stringWriter.toString();
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
			throw new WebApplicationException("template not loaded",
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}
		return Response.ok(rsp, MediaType.TEXT_HTML_TYPE).build();
	}
	
	@GET
	@Path("index.html")
	public Response fullindex(@HeaderParam("Accept-Language") String lng){
		return index(lng);
	}


}
