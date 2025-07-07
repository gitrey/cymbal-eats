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

@Path("/menu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    @Inject
    MenuRepository menuRepository;

    @GET
    public List<Menu> getAll() throws Exception {

        return menuRepository.listAll(Sort.ascending("item_name"));
        
    }

    @GET
    @Path("{id}")
    public Menu get(@PathParam("id") Long id) throws Exception {
        return menuRepository.findById(id);
    }

    @GET
    @Path("/ready")
    public List<Menu> getAllReady() throws Exception {
        return menuRepository.list("status", Status.Ready);
    }

    @GET
    @Path("/failed")
    public List<Menu> getAllFailed() throws Exception {
        return menuRepository.list("status", Status.Failed);
    }

    @GET
    @Path("/processing")
    public List<Menu> getAllProcessing() throws Exception {
        return menuRepository.list("status", Status.Processing);
    }
    
    @POST
    @Transactional
    public Response create(Menu menu) {
        if (menu == null || menu.id != null) 
            throw new WebApplicationException("id != null");
            menu.status=Status.Processing;
        menuRepository.persist(menu);
        return Response.ok(menu).status(200).build();
    }

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
        if (menu.description != null) entity.description = menu.description;
        if (menu.rating != 0) entity.rating = menu.rating;
        if (menu.status != null) entity.status = menu.status;

        return entity;
    }

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
