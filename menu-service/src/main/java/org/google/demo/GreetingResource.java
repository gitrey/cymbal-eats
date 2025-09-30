package org.google.demo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * A JAX-RS resource that provides a simple "hello" endpoint.
 * This class is a basic example of a RESTful web service using Jakarta RESTful Web Services.
 */
@Path("/hello")
public class GreetingResource {

    /**
     * Responds with a plain text "Hello RESTEasy" greeting.
     * This method handles HTTP GET requests to the /hello path.
     *
     * @return A {@link String} containing the greeting message.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}