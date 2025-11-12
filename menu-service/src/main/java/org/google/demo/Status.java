package org.google.demo;

/**
 * Represents the status of a menu item.
 * This enum defines the possible states a menu item can be in.
 */
public enum Status {
    /**
     * The menu item is currently being processed.
     */
    Processing,

    /**
     * The menu item is ready and available.
     */
    Ready,

    /**
     * The processing of the menu item has failed.
     */
    Failed
}