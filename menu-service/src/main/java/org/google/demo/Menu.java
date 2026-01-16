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
 * Represents a single menu item in the restaurant's catalog.
 *
 * This class is a Panache entity that maps to the "Menu" table in the database.
 * It includes details about the menu item, such as its name, price, and status.
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
     * The default spice level of the menu item, with a default value of 0.
     */
    @Column(name="default_spice_level", columnDefinition =  "integer default 0")
    public int spiceLevel;

    /**
     * A short, catchy description of the menu item (e.g., "sweet delight", "super spicy").
     */
    @Column(name="tag_line")
    public String tagLine;

    /**
     * The URL for the full-size image of the menu item.
     */
    @Column(name="item_image_url")
    public URL itemImageURL;

    /**
     * The URL for the thumbnail image of the menu item.
     */
    @Column(name="item_thumbnail_url")
    public URL itemThumbnailURL;

    /**
     * The current status of the menu item (e.g., Ready, Failed, Processing).
     */
    @Column(name="item_status")
    public Status status;

    /**
     * The timestamp when the menu item was first created.
     */
    @CreationTimestamp
    @Column(name="creation_timestamp")
    public LocalDateTime createDateTime;
 
    /**
     * The timestamp when the menu item was last updated.
     */
    @UpdateTimestamp
    @Column(name="update_timestamp")
    public LocalDateTime updateDateTime;

    public static List<Menu> findReady() {
        return list("status", Status.Ready);
    }
    
    public static List<Menu> findFailed() {
        return list("status", Status.Failed);
    }

    public static List<Menu> findProcessing() {
        return list("status", Status.Processing);
    }
}
