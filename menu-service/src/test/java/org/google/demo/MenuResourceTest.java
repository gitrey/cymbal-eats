package org.google.demo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.net.URL;

@QuarkusTest
public class MenuResourceTest {

    @Test
    public void testCreateMenu() throws Exception {
        Menu menu = new Menu();
        menu.itemName = "Test Item";
        menu.itemPrice = new BigDecimal("10.00");
        menu.spiceLevel = 2;
        menu.tagLine = "Test Tagline";
        menu.description = "Test Description";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(menu)
            .when().post("/menu")
            .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateMenu() throws Exception {
        // First create a menu item
        Menu createMenu = new Menu();
        createMenu.itemName = "Test Item";
        createMenu.itemPrice = new BigDecimal("10.00");
        createMenu.spiceLevel = 2;
        createMenu.tagLine = "Test Tagline";
        createMenu.description = "Test Description";

         Long id = given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(createMenu)
            .when().post("/menu")
            .then()
                .statusCode(200)
            .extract().body().jsonPath().getLong("id");

        //Now update the menu item
        Menu updateMenu = new Menu();
        updateMenu.itemName = "Updated Item";
        updateMenu.description = "Updated Description";

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(updateMenu)
            .when().put("/menu/" + id)
            .then()
                .statusCode(200)
                .body("itemName", is("Updated Item"))
                .body("description", is("Updated Description"));
    }
}
