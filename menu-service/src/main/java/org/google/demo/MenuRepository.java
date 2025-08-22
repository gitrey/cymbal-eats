package org.google.demo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for Menu entities.
 * This interface is used to interact with the database and perform CRUD operations on Menu entities.
 */
@ApplicationScoped
public class MenuRepository implements PanacheRepository<Menu> {
}
