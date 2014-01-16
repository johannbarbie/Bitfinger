package org.bitfinger.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Path(WebfingerResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class WebfingerResource {
	public final static String PATH = "/.well-known/webfinger";
	
	final private ObjectMapper mapper;
	
	@Inject
	public WebfingerResource() {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); 
	}
	
	@GET
	public Response getWebfinger(@QueryParam("resource") String resource){
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			HttpGet someHttpGet = new HttpGet("http://webfist.org"+PATH);
			URI uri = new URIBuilder(someHttpGet.getURI())
				.addParameter("resource", resource)
				.build();
			HttpRequestBase request = new HttpGet(uri);
			response = client.execute(request);
			String string = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			return Response.ok(string, "application/jrd+json").build();
		} catch (URISyntaxException | IOException ex) {
			return null;
		}
	}

}
