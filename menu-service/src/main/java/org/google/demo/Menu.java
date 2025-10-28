package org.google.demo;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Represents a menu item in the restaurant.
 */
@Entity
public class Menu extends PanacheEntity {
    
    /**
     * The name of the menu item.
     */
    @Column(name="item_name")
    public String itemName;
    
    /**
     * The price of the menu item.
     */
    @Column(name="item_price")
    public BigDecimal itemPrice;

    /**
     * The default spice level of the menu item.
     */
    @Column(name="default_spice_level", columnDefinition =  "integer default 0")
    public int spiceLevel;

    /**
     * A short, catchy description of the menu item.
     */
    @Column(name="tag_line")
    public String tagLine; //"sweets delight", "super spicy"

    /**
     * The URL of the main image for the menu item.
     */
    @Column(name="item_image_url")
    public URL itemImageURL;

    /**
     * The URL of the thumbnail image for the menu item.
     */
    @Column(name="item_thumbnail_url")
    public URL itemThumbnailURL;

    /**
     * The current status of the menu item (e.g., Processing, Ready, Failed).
     */
    @Column(name="item_status")
    public Status status;

    /**
     * The timestamp of when the menu item was created.
     */
    @CreationTimestamp
    @Column(name="creation_timestamp")
    public LocalDateTime createDateTime;
 
    /**
     * The timestamp of when the menu item was last updated.
     */
    @UpdateTimestamp
    @Column(name="update_timestamp")
    public LocalDateTime updateDateTime;

    /**
     * Finds all menu items with the status 'Ready'.
     * @return a list of ready menu items.
     */
    public static List<Menu> findReady() {
        return list("status", Status.Ready);
    }
    
    /**
     * Finds all menu items with the status 'Failed'.
     * @return a list of failed menu items.
     */
    public static List<Menu> findFailed() {
        return list("status", Status.Failed);
    }

    /**
     * Finds all menu items with the status 'Processing'.
     * @return a list of processing menu items.
     */
    public static List<Menu> findProcessing() {
        return list("status", Status.Processing);
    }
}
