package org.google.demo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * A simple JAX-RS resource that provides a greeting endpoint.
 */
@Path("/hello")
public class GreetingResource {

    /**
     * Responds with a "Hello RESTEasy" plain text message.
     * @return A plain text string "Hello RESTEasy".
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}