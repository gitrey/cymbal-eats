package org.google.demo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * A simple resource to say hello.
 *
 * This class provides a single endpoint that returns a "Hello RESTEasy" message.
 * It's a good example of a simple JAX-RS resource.
 */
@Path("/hello")
public class GreetingResource {

    /**
     * Returns a "Hello RESTEasy" message.
     *
     * @return a "Hello RESTEasy" message
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}