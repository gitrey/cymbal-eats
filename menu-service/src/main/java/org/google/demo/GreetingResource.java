package org.google.demo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * A simple JAX-RS resource that provides a "hello" endpoint.
 */
@Path("/hello")
public class GreetingResource {

    /**
     * Returns a simple "Hello RESTEasy" string.
     * @return a greeting message
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}