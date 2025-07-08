package org.google.demo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for {@link Menu} entities.
 * This interface uses PanacheRepository to provide common database operations.
 */
@ApplicationScoped
public class MenuRepository implements PanacheRepository<Menu> {
}
