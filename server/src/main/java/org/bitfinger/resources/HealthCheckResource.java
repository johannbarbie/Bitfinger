package org.bitfinger.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bitfinger.pojo.AddressResponse;


@Path(HealthCheckResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class HealthCheckResource {
	public final static String PATH = "/healthcheck";

	@GET
	public Map<String,String> healthcheck(){
		Map<String,String> rv = new HashMap<>(1);
		rv.put("status", "ok!");
		return rv;
	}
	
	
}
