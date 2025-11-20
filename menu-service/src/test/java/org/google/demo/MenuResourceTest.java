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
        menu.description = "Test Description";
        menu.rating = 4;
        menu.itemImageURL = null; // Set to null or a valid URL
        menu.itemThumbnailURL = null; // Set to null or a valid URL
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
    public void testCreateMenu() {
        Menu menu = new Menu();
        menu.itemName = "Test Item";
        menu.itemPrice = java.math.BigDecimal.valueOf(10.0);
        menu.spiceLevel = 1;
        menu.tagLine = "Test Tagline";
        menu.description = "Test Description";
        menu.rating = 4;
        menu.itemImageURL = null; // Set to null or a valid URL
        menu.itemThumbnailURL = null; // Set to null or a valid URL
        menu.status = Status.Ready;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().post("/menu")
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("itemName", is("Test Item"));
    }

    @Test
    public void testCreateMenuWithRatingAndDescription() {
        Menu menu = new Menu();
        menu.itemName = "Test Item 2";
        menu.itemPrice = java.math.BigDecimal.valueOf(20.0);
        menu.spiceLevel = 2;
        menu.tagLine = "Test Tagline 2";
        menu.description = "Test Description 2";
        menu.rating = 5;
        menu.itemImageURL = null;
        menu.itemThumbnailURL = null;
        menu.status = Status.Ready;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().post("/menu")
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("itemName", is("Test Item 2"))
            .body("description", is("Test Description 2"))
            .body("rating", is(5));
    }

    @Test
    public void testUpdateMenuWithRatingAndDescription() {
        Menu menu = new Menu();
        menu.itemName = "Updated Item";
        menu.description = "Updated Description";
        menu.rating = 3;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().put("/menu/1")
            .then()
            .statusCode(200)
            .body("itemName", is("Updated Item"))
            .body("description", is("Updated Description"))
            .body("rating", is(3));
    }

    @Test
    public void testCreateMenuWithInvalidRating() {
        Menu menu = new Menu();
        menu.itemName = "Invalid Item";
        menu.itemPrice = java.math.BigDecimal.valueOf(30.0);
        menu.rating = 6; // Invalid rating
        menu.status = Status.Ready;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().post("/menu")
            .then()
            .statusCode(400);
    }
}
