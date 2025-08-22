package org.google.demo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * A repository for the Menu entity.
 * This class provides a set of methods to interact with the Menu data in the database.
 * It uses the Panache repository pattern, so it gets all the basic CRUD operations for free.
 */
@ApplicationScoped
public class MenuRepository implements PanacheRepository<Menu> {
}
