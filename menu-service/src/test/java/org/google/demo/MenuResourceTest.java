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
