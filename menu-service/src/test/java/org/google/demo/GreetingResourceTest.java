package org.google.demo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * A test class for the GreetingResource.
 * This class tests the "/hello" endpoint.
 */
@QuarkusTest
public class GreetingResourceTest {

    /**
     * Tests the "/hello" endpoint.
     * It sends a GET request to the endpoint and asserts that the response is "Hello RESTEasy" with a status code of 200.
     */
    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}