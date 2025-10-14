# Feature Implementation Plan: menu-service-rating

## 📋 Todo Checklist
- [x] ~~Add `description` and `rating` fields to the `Menu` entity.~~ ✅ Implemented
- [x] ~~Update `import.sql` to include sample data for the new fields.~~ ✅ Implemented
- [x] ~~Update `MenuResource` to handle the new fields in `create` and `update` operations.~~ ✅ Implemented
- [x] ~~Add unit tests for the new fields.~~ ✅ Implemented
- [x] ~~Final Review and Testing~~ ✅ Implemented

## 🔍 Analysis & Investigation

### Codebase Structure
The `menu-service` is a standard Quarkus application. The key files for this feature are:
-   `menu-service/src/main/java/org/google/demo/Menu.java`: The JPA entity for the Menu.
-   `menu-service/src/main/resources/import.sql`: The file for seeding initial data.
-   `menu-service/src/main/java/org/google/demo/MenuResource.java`: The REST resource for managing menus.
-   `menu-service/src/test/java/org/google/demo/MenuResourceTest.java`: The unit tests for the MenuResource.

### Current Architecture
The service uses Quarkus with Panache for simplified Hibernate ORM. The `Menu` entity is a simple JPA entity, and the `MenuResource` provides CRUD operations over it.

### Dependencies & Integration Points
The `Menu` entity is used by the `MenuResource`, which is the main integration point. The changes will be contained within the `menu-service`.

### Considerations & Challenges
The rating should be validated to be between 1 and 5. This can be done at the entity level with validation annotations.

## 📝 Implementation Plan

### Prerequisites
No new dependencies are needed.

### Step-by-Step Implementation
1.  **Modify the `Menu` entity**:
    -   Files to modify: `menu-service/src/main/java/org/google/demo/Menu.java`
    -   Changes needed: Add the following fields to the `Menu` class:
        ```java
        @Column(name="description")
        public String description;

        @Column(name="rating")
        public int rating;
        ```

2.  **Update the `import.sql` file**:
    -   Files to modify: `menu-service/src/main/resources/import.sql`
    -   Changes needed: Add `description` and `rating` columns and values to the insert statements. For example:
        ```sql
        insert into menu(id, item_name, item_price, default_spice_level, tag_line, item_image_url, item_thumbnail_url, item_status, description, rating) values (nextval('hibernate_sequence'), 'Curry Plate', 12.5, 3, 'Spicy touch for your taste buds!!' , 'https://unsplash.com/photos/0wn-DdavPa4', 'https://unsplash.com/photos/0wn-DdavPa4', 1, 'A delicious curry plate', 4);
        ```

3.  **Update the `MenuResource` class**:
    -   Files to modify: `menu-service/src/main/java/org/google/demo/MenuResource.java`
    -   Changes needed: In the `update` method, add the following lines to update the new fields:
        ```java
        if (menu.description != null) entity.description = menu.description;
        entity.rating = menu.rating;
        ```

4.  **Add new unit tests**:
    -   Files to modify: `menu-service/src/test/java/org/google/demo/MenuResourceTest.java`
    -   Changes needed: Add a new test to verify that the `description` and `rating` fields are correctly handled in the `create` and `update` operations.
        ```java
        @Test
        public void testCreateMenuWithDescriptionAndRating() {
            Menu menu = new Menu();
            menu.itemName = "Test Item";
            menu.itemPrice = java.math.BigDecimal.valueOf(10.0);
            menu.spiceLevel = 1;
            menu.tagLine = "Test Tagline";
            menu.description = "Test Description";
            menu.rating = 5;
            menu.itemImageURL = null;
            menu.itemThumbnailURL = null;
            menu.status = Status.Ready;

            given()
                .contentType(ContentType.JSON)
                .body(menu)
                .when().post("/menu")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("itemName", is("Test Item"))
                .body("description", is("Test Description"))
                .body("rating", is(5));
        }
        ```

### Testing Strategy
-   Run the existing and new unit tests to ensure that the changes haven't broken any existing functionality.
-   Manually test the API endpoints to verify that the new fields are correctly handled.

## 🎯 Success Criteria
-   The `description` and `rating` fields are successfully added to the `Menu` entity.
-   The `import.sql` file is updated with sample data for the new fields.
-   The `MenuResource` correctly handles the new fields in `create` and `update` operations.
-   The new unit tests pass.
