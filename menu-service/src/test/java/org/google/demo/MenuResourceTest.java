package org.google.demo;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.WebApplicationException;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class MenuResourceTest {

    @InjectMock
    MenuRepository menuRepository;

    private MenuResource menuResource;

    @BeforeEach
    public void setup() {
        menuResource = new MenuResource();
        menuResource.menuRepository = menuRepository;
    }

    private Menu createSampleMenu(Long id, String name, String description, float rating) throws MalformedURLException {
        Menu menu = new Menu();
        menu.id = id;
        menu.itemName = name;
        menu.itemPrice = new BigDecimal("10.99");
        menu.spiceLevel = 2;
        menu.tagLine = "Delicious";
        menu.itemImageURL = new URL("http://example.com/image.jpg");
        menu.itemThumbnailURL = new URL("http://example.com/thumb.jpg");
        menu.status = Status.Ready;
        menu.description = description;
        menu.rating = rating;
        return menu;
    }

    @Test
    public void testGetAll() throws Exception {
        List<Menu> menus = new ArrayList<>();
        menus.add(createSampleMenu(1L, "Dish 1", "Desc 1", 4.0f));
        menus.add(createSampleMenu(2L, "Dish 2", "Desc 2", 4.5f));

        Mockito.when(menuRepository.listAll(Mockito.any())).thenReturn(menus);

        List<Menu> result = menuResource.getAll();
        assertEquals(2, result.size());
        assertEquals("Dish 1", result.get(0).itemName);
        assertEquals("Desc 1", result.get(0).description);
        assertEquals(4.0f, result.get(0).rating);
    }

    @Test
    public void testGet() throws Exception {
        Menu menu = createSampleMenu(1L, "Dish 1", "Desc 1", 4.0f);
        Mockito.when(menuRepository.findById(1L)).thenReturn(menu);

        Menu result = menuResource.get(1L);
        assertNotNull(result);
        assertEquals("Dish 1", result.itemName);
        assertEquals("Desc 1", result.description);
        assertEquals(4.0f, result.rating);
    }

    @Test
    public void testGetNotFound() throws Exception {
        Mockito.when(menuRepository.findById(1L)).thenReturn(null);
        Menu result = menuResource.get(1L);
        assertNull(result);
    }

    @Test
    public void testGetAllReady() throws Exception {
        List<Menu> menus = new ArrayList<>();
        menus.add(createSampleMenu(1L, "Dish 1", "Desc 1", 4.0f));
        Mockito.when(menuRepository.list("status", Status.Ready)).thenReturn(menus);

        List<Menu> result = menuResource.getAllReady();
        assertEquals(1, result.size());
        assertEquals(Status.Ready, result.get(0).status);
    }

    @Test
    public void testGetAllFailed() throws Exception {
        List<Menu> menus = new ArrayList<>();
        Menu failedMenu = createSampleMenu(1L, "Dish 1", "Desc 1", 4.0f);
        failedMenu.status = Status.Failed;
        menus.add(failedMenu);
        Mockito.when(menuRepository.list("status", Status.Failed)).thenReturn(menus);

        List<Menu> result = menuResource.getAllFailed();
        assertEquals(1, result.size());
        assertEquals(Status.Failed, result.get(0).status);
    }

    @Test
    public void testGetAllProcessing() throws Exception {
        List<Menu> menus = new ArrayList<>();
        Menu processingMenu = createSampleMenu(1L, "Dish 1", "Desc 1", 4.0f);
        processingMenu.status = Status.Processing;
        menus.add(processingMenu);
        Mockito.when(menuRepository.list("status", Status.Processing)).thenReturn(menus);

        List<Menu> result = menuResource.getAllProcessing();
        assertEquals(1, result.size());
        assertEquals(Status.Processing, result.get(0).status);
    }

    @Test
    public void testCreate() throws MalformedURLException {
        Menu menuToCreate = createSampleMenu(null, "New Dish", "New Desc", 3.5f); // ID is null for creation

        Response response = menuResource.create(menuToCreate);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Menu createdMenu = (Menu) response.getEntity();
        assertNotNull(createdMenu);
        assertEquals("New Dish", createdMenu.itemName);
        assertEquals("New Desc", createdMenu.description);
        assertEquals(3.5f, createdMenu.rating);
        assertEquals(Status.Processing, createdMenu.status); // Should be set by create method
        Mockito.verify(menuRepository, Mockito.times(1)).persist(menuToCreate);
    }

    @Test
    public void testCreateWithNonNullId() throws MalformedURLException {
        Menu menuToCreate = createSampleMenu(1L, "New Dish", "New Desc", 3.5f); // ID is not null

        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            menuResource.create(menuToCreate);
        });
        assertEquals("id != null", exception.getMessage());
    }

    @Test
    public void testCreateNullMenu() {
        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            menuResource.create(null);
        });
        assertEquals("id != null", exception.getMessage());
    }


    @Test
    public void testUpdate() throws MalformedURLException {
        Menu existingMenu = createSampleMenu(1L, "Old Dish", "Old Desc", 3.0f);
        Menu updatedMenuInfo = createSampleMenu(1L, "Updated Dish", "Updated Desc", 4.2f);
        updatedMenuInfo.itemPrice = new BigDecimal("12.99"); // different price
        updatedMenuInfo.status = Status.Failed; // different status


        Mockito.when(menuRepository.findById(1L)).thenReturn(existingMenu);

        Menu result = menuResource.update(1L, updatedMenuInfo);

        assertNotNull(result);
        assertEquals(1L, result.id); // ID should not change
        assertEquals("Updated Dish", result.itemName);
        assertEquals(new BigDecimal("12.99"), result.itemPrice);
        assertEquals("Updated Desc", result.description);
        assertEquals(4.2f, result.rating);
        assertEquals(Status.Failed, result.status);
    }

    @Test
    public void testUpdatePartial() throws MalformedURLException {
        Menu existingMenu = createSampleMenu(1L, "Old Dish", "Old Desc", 3.0f);
        existingMenu.itemPrice = new BigDecimal("10.99");
        existingMenu.status = Status.Ready;


        Menu partialUpdateInfo = new Menu(); // Only provide some fields
        partialUpdateInfo.itemName = "Partially Updated Dish";
        partialUpdateInfo.rating = 4.8f;


        Mockito.when(menuRepository.findById(1L)).thenReturn(existingMenu);

        Menu result = menuResource.update(1L, partialUpdateInfo);

        assertNotNull(result);
        assertEquals(1L, result.id);
        assertEquals("Partially Updated Dish", result.itemName); // Updated
        assertEquals(4.8f, result.rating); // Updated
        assertEquals(new BigDecimal("10.99"), result.itemPrice); // Should remain from existing
        assertEquals("Old Desc", result.description); // Should remain from existing
        assertEquals(Status.Ready, result.status); // Should remain from existing
    }


    @Test
    public void testUpdateNotFound() throws MalformedURLException {
        Menu updatedMenuInfo = createSampleMenu(1L, "Updated Dish", "Updated Desc", 4.2f);
        Mockito.when(menuRepository.findById(1L)).thenReturn(null);

        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            menuResource.update(1L, updatedMenuInfo);
        });
        assertEquals("Menu item with id 1 does not exist", exception.getMessage());
        assertEquals(404, exception.getResponse().getStatus());
    }

    @Test
    public void testDelete() {
        Menu menuToDelete = new Menu();
        menuToDelete.id = 1L;
        Mockito.when(menuRepository.findById(1L)).thenReturn(menuToDelete);

        Response response = menuResource.delete(1L);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Mockito.verify(menuRepository, Mockito.times(1)).delete(menuToDelete);
    }

    @Test
    public void testDeleteNotFound() {
        Mockito.when(menuRepository.findById(1L)).thenReturn(null);

        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> {
            menuResource.delete(1L);
        });
        assertEquals("Menu item with id1does not exist", exception.getMessage());
        assertEquals(404, exception.getResponse().getStatus());
    }
}
