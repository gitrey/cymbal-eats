package org.google.demo;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.quarkus.panache.common.Sort;

import jakarta.inject.Inject;

/**
 * JAX-RS resource for managing {@link Menu} items.
 * Provides CRUD operations and specific finders for menu items.
 */
@Path("/menu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    @Inject
    MenuRepository menuRepository;

    /**
     * Retrieves all menu items, sorted by item name.
     * @return A list of all {@link Menu} items.
     * @throws Exception if an error occurs during retrieval.
     */
    @GET
    public List<Menu> getAll() throws Exception {

        return menuRepository.listAll(Sort.ascending("item_name"));
        
    }

    /**
     * Retrieves a specific menu item by its ID.
     * @param id The ID of the menu item to retrieve.
     * @return The {@link Menu} item with the specified ID.
     * @throws Exception if an error occurs during retrieval.
     */
    @GET
    @Path("{id}")
    public Menu get(@PathParam("id") Long id) throws Exception {
        return menuRepository.findById(id);
    }

    /**
     * Retrieves all menu items that are in the 'Ready' status.
     * @return A list of {@link Menu} items with status 'Ready'.
     * @throws Exception if an error occurs during retrieval.
     */
    @GET
    @Path("/ready")
    public List<Menu> getAllReady() throws Exception {
        return menuRepository.list("status", Status.Ready);
    }

    /**
     * Retrieves all menu items that are in the 'Failed' status.
     * @return A list of {@link Menu} items with status 'Failed'.
     * @throws Exception if an error occurs during retrieval.
     */
    @GET
    @Path("/failed")
    public List<Menu> getAllFailed() throws Exception {
        return menuRepository.list("status", Status.Failed);
    }

    /**
     * Retrieves all menu items that are in the 'Processing' status.
     * @return A list of {@link Menu} items with status 'Processing'.
     * @throws Exception if an error occurs during retrieval.
     */
    @GET
    @Path("/processing")
    public List<Menu> getAllProcessing() throws Exception {
        return menuRepository.list("status", Status.Processing);
    }
    
    /**
     * Creates a new menu item.
     * The new item will have its status set to 'Processing'.
     * @param menu The {@link Menu} item to create. The ID of this item must be null.
     * @return A {@link Response} object containing the created menu item and HTTP status 200 (OK).
     * @throws WebApplicationException if the provided menu is null or has a non-null ID.
     */
    @POST
    @Transactional
    public Response create(Menu menu) {
        if (menu == null || menu.id != null) 
            throw new WebApplicationException("id != null");
            menu.status=Status.Processing;
        menuRepository.persist(menu);
        return Response.ok(menu).status(200).build();
    }

    /**
     * Updates an existing menu item.
     * Only non-null fields in the provided `menu` object will be used to update the existing entity.
     * @param id The ID of the menu item to update.
     * @param menu A {@link Menu} object containing the fields to update.
     * @return The updated {@link Menu} entity.
     * @throws WebApplicationException if no menu item with the given ID exists (HTTP 404).
     */
    @PUT
    @Transactional
    @Path("{id}")
    public Menu update(@PathParam("id") Long id, Menu menu) {

        Menu entity = menuRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Menu item with id"+id+"does not exist", 404);
        }

        if (menu.itemName != null) entity.itemName=menu.itemName;
        if (menu.itemPrice != null) entity.itemPrice=menu.itemPrice;
        if (menu.tagLine != null) entity.tagLine=menu.tagLine;
        entity.spiceLevel=menu.spiceLevel; // Assuming spiceLevel should always be updated
        if (menu.itemImageURL != null) entity.itemImageURL = menu.itemImageURL;
        if (menu.itemThumbnailURL != null) entity.itemThumbnailURL = menu.itemThumbnailURL;
        if (menu.status != null) entity.status = menu.status;

        return entity;
    }

    /**
     * Deletes a menu item by its ID.
     * @param id The ID of the menu item to delete.
     * @return A {@link Response} object with HTTP status 204 (No Content) if successful.
     * @throws WebApplicationException if no menu item with the given ID exists (HTTP 404).
     */
    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Menu entity = menuRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Menu item with id"+id+"does not exist", 404);
        }
        menuRepository.delete(entity);
        return Response.status(204).build();
    }


}
