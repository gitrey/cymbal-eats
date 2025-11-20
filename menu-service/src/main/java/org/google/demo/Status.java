package org.google.demo;

/**
 * Represents the status of a {@link Menu} item.
 */
public enum Status {
    /** Indicates that the menu item is currently being processed or prepared. */
    Processing,
    /** Indicates that the menu item is ready to be served or displayed. */
    Ready,
    /** Indicates that processing or preparation of the menu item has failed. */
    Failed
}