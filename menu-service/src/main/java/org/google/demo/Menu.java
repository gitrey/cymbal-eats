package org.google.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Menu class
 * This class represents a menu item.
 * It extends PanacheEntity which is provided by quarkus.
 */
@Entity
@Table(name = "menu")
@GeneratedValue
public class Menu extends PanacheEntity {
    /**
     * itemName
     * The name of the menu item.
     */
    @Column(name="item_name")
    public String itemName;

    /**
     * itemPrice
     * The price of the menu item.
     */
    @Column(name="item_price")
    public BigDecimal itemPrice;

    /**
     * spiceLevel
     * The spice level of the menu item.
     */
    @Column(name="default_spice_level", columnDefinition =  "integer default 0")
    public int spiceLevel;

    /**
     * tagLine
     * The tag line of the menu item.
     */
    @Column(name="tag_line")
    public String tagLine; //"sweets delight", "super spicy"

    /**
     * itemImageURL
     * The image URL of the menu item.
     */
    @Column(name="item_image_url")
    public URL itemImageURL;

    /**
     * itemThumbnailURL
     * The thumbnail URL of the menu item.
     */
    @Column(name="item_thumbnail_url")
    public URL itemThumbnailURL;

    /**
     * status
     * The status of the menu item.
     */
    @Column(name="item_status")
    public Status status;

    /**
     * createDateTime
     * The creation date and time of the menu item.
     */
    @CreationTimestamp
    @Column(name="creation_timestamp")
    public LocalDateTime createDateTime;

    /**
     * updateDateTime
     * The last update date and time of the menu item.
     */
    @UpdateTimestamp
    @Column(name="update_timestamp")
    public LocalDateTime updateDateTime;

    /**
     * findReady
     * @return all menu item with status ready.
     */
    public static List<Menu> findReady() {
        return list("status", Status.Ready);
    }
    
    /**
     * findFailed
     * @return all menu item with status Failed.
     */
    public static List<Menu> findFailed() {
        return list("status", Status.Failed);
    }

    /**
     * findProcessing
     * @return all menu items with status processing.
     */
    public static List<Menu> findProcessing() {
        return list("status", Status.Processing);
    }
}
