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
 * A JAX-RS resource for managing menu items.
 * This class provides a RESTful API for creating, retrieving, updating, and deleting menu items.
 */
@Path("/menu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    @Inject
    MenuRepository menuRepository;

    /**
     * Returns all menu items, sorted by name.
     * @return a list of all menu items
     * @throws Exception if an error occurs
     */
    @GET
    public List<Menu> getAll() throws Exception {

        return menuRepository.listAll(Sort.ascending("item_name"));
        
    }

    /**
     * Returns a specific menu item by its ID.
     * @param id the ID of the menu item
     * @return the menu item with the given ID
     * @throws Exception if an error occurs
     */
    @GET
    @Path("{id}")
    public Menu get(@PathParam("id") Long id) throws Exception {
        return menuRepository.findById(id);
    }

    /**
     * Returns all menu items with the status "Ready".
     * @return a list of ready menu items
     * @throws Exception if an error occurs
     */
    @GET
    @Path("/ready")
    public List<Menu> getAllReady() throws Exception {
        return menuRepository.list("status", Status.Ready);
    }

    /**
     * Returns all menu items with the status "Failed".
     * @return a list of failed menu items
     * @throws Exception if an error occurs
     */
    @GET
    @Path("/failed")
    public List<Menu> getAllFailed() throws Exception {
        return menuRepository.list("status", Status.Failed);
    }

    /**
     * Returns all menu items with the status "Processing".
     * @return a list of processing menu items
     * @throws Exception if an error occurs
     */
    @GET
    @Path("/processing")
    public List<Menu> getAllProcessing() throws Exception {
        return menuRepository.list("status", Status.Processing);
    }
    
    /**
     * Creates a new menu item.
     * @param menu the menu item to create
     * @return a response with the created menu item
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
     * @param id the ID of the menu item to update
     * @param menu the new data for the menu item
     * @return the updated menu item
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
        entity.spiceLevel=menu.spiceLevel;
        if (menu.itemImageURL != null) entity.itemImageURL = menu.itemImageURL;
        if (menu.itemThumbnailURL != null) entity.itemThumbnailURL = menu.itemThumbnailURL;
        if (menu.status != null) entity.status = menu.status;

        return entity;
    }

    /**
     * Deletes a menu item by its ID.
     * @param id the ID of the menu item to delete
     * @return a response with the status 204 (No Content)
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
