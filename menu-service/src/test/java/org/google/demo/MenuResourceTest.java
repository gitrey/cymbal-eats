package org.google.demo;

import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    public static void setupAll() {
        // The test database is created from scratch, so we need to create the sequence
        // that is used in import.sql.
        given()
            .when().post("/q/dev/execute-sql?sql=CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1");
    }

    @BeforeEach
    public void setup() {
        Menu menu = new Menu();
        menu.id = 1L;
        menu.itemName = "Test Item";
        menu.itemPrice = BigDecimal.valueOf(10.0);
        menu.spiceLevel = 1;
        menu.tagLine = "Test Tagline";
        menu.description = "Test Description";
        menu.rating = 5;
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
    public void testUpdateMenuRating() {
        Menu menu = new Menu();
        menu.rating = 4;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().put("/menu/1")
            .then()
            .statusCode(200)
            .body("rating", is(4));
    }

    @Test
    public void testUpdateMenuDescription() {
        Menu menu = new Menu();
        menu.description = "New Description";

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().put("/menu/1")
            .then()
            .statusCode(200)
            .body("description", is("New Description"));
    }

    @Test
    public void testCreateMenu() {
        Menu menu = new Menu();
        menu.itemName = "Test Item";
        menu.itemPrice = java.math.BigDecimal.valueOf(10.0);
        menu.spiceLevel = 1;
        menu.tagLine = "Test Tagline";
        menu.description = "Test Description";
        menu.rating = 5;
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
    public void testUpdateMenuWithDescriptionAndRating() {
        Menu menu = new Menu();
        menu.description = "New Description";
        menu.rating = 5;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().put("/menu/1")
            .then()
            .statusCode(200)
            .body("description", is("New Description"))
            .body("rating", is(5));
    }

    @Test
    public void testUpdateMenuWithNullRating() {
        Menu menu = new Menu();
        menu.rating = null;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().put("/menu/1")
            .then()
            .statusCode(200);
    }

    @Test
    public void testUpdateMenuWithZeroRating() {
        Menu menu = new Menu();
        menu.rating = 0;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().put("/menu/1")
            .then()
            .statusCode(200);
    }

    @Test
    public void testUpdateMenuWithInvalidRating() {
        Menu menu = new Menu();
        menu.rating = 6;

        given()
            .contentType(ContentType.JSON)
            .body(menu)
            .when().put("/menu/1")
            .then()
            .statusCode(200);
    }
}
