package org.google.demo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * A repository for {@link Menu} entities.
 * This class provides a set of methods to interact with the Menu data source
 * and leverages the Panache repository pattern for simplified data access.
 */
@ApplicationScoped
public class MenuRepository implements PanacheRepository<Menu> {
}
