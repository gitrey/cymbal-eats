package org.google.demo;

/**
 * Represents the status of a menu item.
 */
public enum Status {
    /**
     * The menu item is currently being processed.
     */
    Processing,
    /**
     * The menu item is ready to be served.
     */
    Ready,
    /**
     * The menu item processing has failed.
     */
    Failed
}