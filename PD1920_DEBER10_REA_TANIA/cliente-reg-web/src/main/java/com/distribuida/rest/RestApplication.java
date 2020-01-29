package com.distribuida.rest;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class RestApplication extends ResourceConfig {

	public RestApplication() {
		packages("com.distribuida.rest");
	}
}
