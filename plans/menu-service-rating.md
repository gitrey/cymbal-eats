# Feature Implementation Plan: menu-service-rating

## 📋 Todo Checklist
- [x] ~~Update `Menu.java` entity with new fields~~ ✅ Implemented
- [x] ~~Add validation constraints to the `rating` field~~ ✅ Implemented
- [x] ~~Update `import.sql` with new fields and sample data~~ ✅ Implemented
- [x] ~~Update `MenuResource.java` to handle new fields in `update` method~~ ✅ Implemented
- [x] ~~Add unit tests for new fields and validation logic~~ ✅ Implemented
- [ ] Final Review and Testing

## 🔍 Analysis & Investigation

### Codebase Structure
The `menu-service` is a standard Quarkus project. The key files are:
-   `src/main/java/org/google/demo/Menu.java`: The JPA entity for menu items.
-   `src/main/java/org/google/demo/MenuResource.java`: The JAX-RS resource that exposes REST endpoints for the `Menu` entity.
-   `src/main/java/org/google/demo/MenuRepository.java`: The Panache repository for `Menu` entity.
-   `src/main/resources/import.sql`: A SQL script that pre-populates the database with sample data.
-   `src/test/java/org/google/demo/MenuResourceTest.java`: The test class for `MenuResource`.

### Current Architecture
The service uses a classic three-tier architecture:
-   **Presentation Layer:** `MenuResource.java` handles HTTP requests.
-   **Business Layer:** The business logic is simple and contained within the resource and repository.
-   **Data Access Layer:** `MenuRepository.java` and the `Menu` entity use Panache to interact with the database.

The architecture is straightforward, and the changes will fit in naturally.

### Dependencies & Integration Points
-   **Quarkus:** The core framework.
-   **Hibernate ORM with Panache:** For database access.
-   **JAX-RS (RESTEasy):** For the REST API.
-   **PostgreSQL:** The underlying database.

The new fields will be automatically handled by Hibernate and RESTEasy, so no major integration work is needed.

### Considerations & Challenges
-   **Validation:** The `rating` field has specific validation rules (integer, 1-5, not null). This needs to be implemented correctly using Jakarta Bean Validation annotations.
-   **Database Schema:** The `import.sql` needs to be updated to reflect the new schema.
-   **Testing:** The tests need to cover the new fields and the validation logic.

## 📝 Implementation Plan

### Prerequisites
-   A running PostgreSQL database.
-   Maven and JDK installed.

### Step-by-Step Implementation
1.  **Update `Menu.java` Entity:**
    -   Files to modify: `src/main/java/org/google/demo/Menu.java`
    -   Changes needed:
        -   Add a `public String description;` field with a `@Column` annotation.
        -   Add a `public Integer rating;` field.
        -   Add the following annotations to the `rating` field:
            ```java
            import jakarta.validation.constraints.Max;
            import jakarta.validation.constraints.Min;
            import jakarta.validation.constraints.NotNull;

            // ...

            @NotNull
            @Min(1)
            @Max(5)
            public Integer rating;
            ```
    -   **Implementation Notes**: Added the `description` and `rating` fields to the `Menu.java` entity, along with the specified validation annotations.
    -   **Status**: ✅ Completed

2.  **Update `import.sql`:**
    -   Files to modify: `src/main/resources/import.sql`
    -   Changes needed:
        -   Add `description` and `rating` columns to the `insert into menu` statements.
        -   Provide sample values for the new columns. For example:
            ```sql
            insert into menu(id, item_name, item_price, default_spice_level, tag_line, item_image_url, item_thumbnail_url, item_status, description, rating) values (nextval('hibernate_sequence'), 'Curry Plate', 12.5, 3, 'Spicy touch for your taste buds!!' , 'https://unsplash.com/photos/0wn-DdavPa4', 'https://unsplash.com/photos/0wn-DdavPa4', 1, 'A delicious curry plate.', 5);
            ```
    -   **Implementation Notes**: Added the `description` and `rating` columns to the `insert into menu` statements in `import.sql` and provided sample values.
    -   **Status**: ✅ Completed

3.  **Update `MenuResource.java`:**
    -   Files to modify: `src/main/java/org/google/demo/MenuResource.java`
    -   Changes needed:
        -   In the `update` method, add logic to update the new fields:
            ```java
            if (menu.description != null) entity.description = menu.description;
            if (menu.rating != null) entity.rating = menu.rating;
            ```
    -   **Implementation Notes**: Added logic to the `update` method in `MenuResource.java` to handle the new `description` and `rating` fields.
    -   **Status**: ✅ Completed

4.  **Update `MenuResourceTest.java`:**
    -   Files to modify: `src/test/java/org/google/demo/MenuResourceTest.java`
    -   Changes needed:
        -   Add a test to verify that the new fields are returned by the `GET` endpoints.
        -   Add a test for the `update` method to verify that the new fields are updated correctly.
        -   Add tests to verify the validation on the `rating` field. This will involve sending invalid values (e.g., 0, 6, null) and asserting that the correct error response is returned.
    -   **Implementation Notes**: Added tests for creating, getting, and updating menu items with the new fields. Also added tests to verify the validation constraints on the `rating` field.
    -   **Status**: ✅ Completed

### Testing Strategy
-   Run the existing tests to ensure no regressions have been introduced.
-   Add new unit tests to `MenuResourceTest.java` as described above.
-   Manually test the API using `curl` or a REST client to verify the changes.

## 🎯 Success Criteria
-   The `Menu` entity has the `description` and `rating` fields.
-   The `rating` field has the correct validation constraints.
-   The `import.sql` script correctly populates the new fields.
-   The REST API correctly handles the new fields.
-   All tests pass.
