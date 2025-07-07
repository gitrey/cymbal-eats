package org.google.demo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MenuRepository implements PanacheRepository<Menu> {
}
