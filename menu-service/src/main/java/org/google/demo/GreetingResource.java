package org.google.demo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * GreetingResource class
 * This class is a simple REST endpoint for testing.
 */
@Path("/hello")
public class GreetingResource {

    /**
     * hello method
     * This method returns a simple text response.
     * @return "Hello RESTEasy"
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}