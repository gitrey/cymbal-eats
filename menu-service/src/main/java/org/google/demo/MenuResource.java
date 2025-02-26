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

/**
 * MenuResource class
 * This class is a REST controller for the Menu entity.
 * It handles requests for all menu items, specific menu items by ID,
 * and menu items by status (ready, failed, processing).
 * It also handles creating, updating, and deleting menu items.
 */
@Path ("/menu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    /**
     * getAll method
     * This method returns all menu items.
     * @return list of Menu items
     * @throws Exception if there is an error.
     */
    @GET
    public List<Menu> getAll() throws Exception {

        return Menu.findAll(Sort.ascending("item_name")).list();
        
    }

    /**
     * get method
     * This method returns a single menu item by its ID.
     * @param id The ID of the menu item to retrieve.
     * @return Menu item with the matching id.
     * @throws Exception if there is an error.
     */
    @GET
    @Path("{id}")
    public Menu get(@PathParam("id") Long id) throws Exception {
        return Menu.findById(id);
    }

    /**
     * getAllReady method
     * This method returns all menu items with status ready.
     * @return list of Menu items with status ready.
     * @throws Exception if there is an error.
     */
    @GET
    @Path("/ready")
    public List<Menu> getAllReady() throws Exception {
        return Menu.findReady();
    }

    /**
     * getAllFailed method
     * This method returns all menu items with status Failed.
     * @return list of Menu items with status failed.
     * @throws Exception if there is an error.
     */
    @GET
    @Path("/failed")
    public List<Menu> getAllFailed() throws Exception {
        return Menu.findFailed();
    }

    /**
     * getAllProcessing method
     * This method returns all menu items with status Processing.
     * @return list of Menu items with status processing.
     * @throws Exception if there is an error.
     */
    @GET
    @Path("/processing")
    public List<Menu> getAllProcessing() throws Exception {
        return Menu.findProcessing();
    }
    
    /**
     * create method
     * This method creates a new menu item.
     * @param menu the menu to create.
     * @return Response with status 200 if the creation was successfull
     */
    @POST
    @Transactional
    public Response create(Menu menu) {
        if (menu == null || menu.id != null) 
            throw new WebApplicationException("id != null");
            menu.status=Status.Processing;
        menu.persist();
        return Response.ok(menu).status(200).build();
    }

    /**
     * update method
     * This method updates an existing menu item.
     * @param id the id of the menu item to update.
     * @param menu the menu item to update.
     * @return the updated Menu item.
     */
    @PUT
    @Transactional
    @Path("{id}")
    public Menu update(@PathParam("id") Long id, Menu menu) {

        Menu entity = Menu.findById(id);
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
     * delete method
     * This method deletes a menu item.
     * @param id the id of the menu item to delete.
     * @return Response with status 204 if the delete was successfull.
     */
    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Menu entity = Menu.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Menu item with id"+id+"does not exist", 404);
        }
        entity.delete();
        return Response.status(204).build();
    }


}
