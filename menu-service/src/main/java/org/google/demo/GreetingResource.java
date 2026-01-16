package org.google.demo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * A simple JAX-RS resource that returns a greeting.
 */
@Path("/hello")
public class GreetingResource {

    /**
     * Returns a simple greeting.
     * @return a "Hello RESTEasy" string.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}