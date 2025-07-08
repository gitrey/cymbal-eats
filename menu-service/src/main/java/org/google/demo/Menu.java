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
 * This class is a PanacheEntity, making it easy to persist and query.
 */
@Entity
public class Menu extends PanacheEntity {
    
    /** The name of the menu item. */
    @Column(name="item_name")
    public String itemName;
    
    /** The price of the menu item. */
    @Column(name="item_price")
    public BigDecimal itemPrice;

    /** The default spice level of the item, defaults to 0 (not spicy). */
    @Column(name="default_spice_level", columnDefinition =  "integer default 0")
    public int spiceLevel;

    /** A short, catchy tag line for the item (e.g., "sweet delight", "super spicy"). */
    @Column(name="tag_line")
    public String tagLine; //"sweets delight", "super spicy"

    /** The URL for the full-size image of the menu item. */
    @Column(name="item_image_url")
    public URL itemImageURL;

    /** The URL for the thumbnail image of the menu item. */
    @Column(name="item_thumbnail_url")
    public URL itemThumbnailURL;

    /** The current status of the menu item (e.g., Processing, Ready, Failed). */
    @Column(name="item_status")
    public Status status;

    /** Timestamp of when the menu item was created. */
    @CreationTimestamp
    @Column(name="creation_timestamp")
    public LocalDateTime createDateTime;
 
    /** Timestamp of when the menu item was last updated. */
    @UpdateTimestamp
    @Column(name="update_timestamp")
    public LocalDateTime updateDateTime;

    /**
     * Finds all menu items that are in the 'Ready' status.
     * @return A list of menu items with status 'Ready'.
     */
    public static List<Menu> findReady() {
        return list("status", Status.Ready);
    }
    
    /**
     * Finds all menu items that are in the 'Failed' status.
     * @return A list of menu items with status 'Failed'.
     */
    public static List<Menu> findFailed() {
        return list("status", Status.Failed);
    }

    /**
     * Finds all menu items that are in the 'Processing' status.
     * @return A list of menu items with status 'Processing'.
     */
    public static List<Menu> findProcessing() {
        return list("status", Status.Processing);
    }
}
