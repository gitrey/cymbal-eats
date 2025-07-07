package org.google.demo;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class MenuResourceTest {

    @InjectMock
    MenuRepository menuRepository;

    @BeforeEach
    public void setup() {
        Menu menu = new Menu();
        menu.id = 1L;
        menu.itemName = "Test Item";
        menu.itemPrice = BigDecimal.valueOf(10.0);
        menu.spiceLevel = 1;
        menu.tagLine = "Test Tagline";
        menu.itemImageURL = null; // Set to null or a valid URL
        menu.itemThumbnailURL = null; // Set to null or a valid URL
        menu.description = "This is a test description.";
        menu.rating = 5;
        menu.status = Status.Ready;

        Mockito.when(menuRepository.findById(1L)).thenReturn(menu);
        Mockito.when(menuRepository.listAll()).thenReturn(Collections.singletonList(menu));
        Mockito.doAnswer(invocation -> {
            Menu m = invocation.getArgument(0);
            m.id = 1L;
            return null;
        }).when(menuRepository).persist(any(Menu.class));
    }

    @Test
    public void testCreateMenuWithDescriptionAndRating() {
        Menu menu = new Menu();
        menu.itemName = "Test Item";
        menu.itemPrice = java.math.BigDecimal.valueOf(10.0);
        menu.spiceLevel = 1;
        menu.tagLine = "Test Tagline";
        menu.itemImageURL = null; // Set to null or a valid URL
        menu.itemThumbnailURL = null; // Set to null or a valid URL
        menu.description = "This is a test description.";
        menu.rating = 5;
        menu.status = Status.Ready;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().post("/menu")
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("itemName", is("Test Item"))
            .body("description", is("This is a test description."))
            .body("rating", is(5));
    }

    @Test
    public void testUpdateMenuWithDescriptionAndRating() {
        // First, create a menu item to update
        Menu menu = new Menu();
        menu.itemName = "Original Item";
        menu.itemPrice = java.math.BigDecimal.valueOf(5.0);
        menu.spiceLevel = 0;
        menu.tagLine = "Original Tagline";
        menu.itemImageURL = null;
        menu.itemThumbnailURL = null;
        menu.description = "Original description.";
        menu.rating = 3;
        menu.status = Status.Ready;

        Menu createdMenu = given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().post("/menu")
            .as(Menu.class);

        // Now, update the menu item
        createdMenu.itemName = "Updated Item";
        createdMenu.description = "Updated description.";
        createdMenu.rating = 4;

        given()
            .contentType(ContentType.JSON)
            .body(createdMenu)
            .when().put("/menu/" + createdMenu.id)
            .then()
            .statusCode(200)
            .body("itemName", is("Updated Item"))
            .body("description", is("Updated description."))
            .body("rating", is(4));
    }
}
