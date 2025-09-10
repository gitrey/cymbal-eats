# Feature Implementation Plan: update-menu-service-rating-feature

## 📋 Todo Checklist
- [x] ~~Update `Menu.java` entity with new fields~~ ✅ Implemented
- [x] ~~Update `import.sql` to include sample data for new fields~~ ✅ Implemented
- [x] ~~Update `MenuResource.java` to handle new fields in create/update operations~~ ✅ Implemented
- [x] ~~Add unit tests for new fields in `MenuResourceTest.java`~~ ✅ Implemented
- [x] ~~Final Review and Testing~~ ✅ Implemented

## 🔍 Analysis & Investigation

### Codebase Structure
The `menu-service` is a standard Quarkus application built with Maven.
- Java source files are located in `src/main/java/org/google/demo/`.
- Database initialization scripts are in `src/main/resources/`.
- Unit tests are in `src/test/java/org/google/demo/`.

### Current Architecture
The application follows a typical three-tier architecture:
- **Presentation Layer**: A JAX-RS resource (`MenuResource.java`) defines the REST API endpoints.
- **Business Logic/Data Access Layer**: The application uses the Panache ORM pattern, where the `Menu.java` class is a Panache entity that includes data access methods. A `MenuRepository` is also used for data access.
- **Persistence Layer**: A relational database is used for data storage, configured via `application.properties` and initialized with `import.sql`.

### Dependencies & Integration Points
- **Quarkus**: The core framework.
- **Hibernate ORM with Panache**: For database interaction.
- **JAX-RS (RESTEasy)**: For creating REST endpoints.
- **PostgreSQL**: The likely database, based on the `import.sql` syntax.

### Considerations & Challenges
- The database schema will be altered by adding new columns. `quarkus.hibernate-orm.database.generation=drop-and-create` in `application.properties` will handle this automatically in dev environments.
- The REST API contract will change. Consumers of the API may need to be updated to handle the new `description` and `rating` fields.
- Ensuring that the new fields are properly validated and handled in all CRUD operations is important.

## 📝 Implementation Plan

### Prerequisites
- A running PostgreSQL database instance accessible to the `menu-service`.
- Maven and JDK installed to build and run the application.

### Step-by-Step Implementation
1. **Step 1: Update the Menu Entity**
   - **Files to modify**: `menu-service/src/main/java/org/google/demo/Menu.java`
   - **Changes needed**: Add `description` (String) and `rating` (int) fields to the `Menu` class. Annotate them with `@Column` to map them to database columns.

   ```java
   // ... existing code ...
   @Entity
   public class Menu extends PanacheEntity {
       // ... existing fields ...

       @Column(name="description")
       public String description;

       @Column(name="rating", columnDefinition = "integer default 0")
       public int rating;

       @CreationTimestamp
   // ... existing code ...
   }
   ```

2. **Step 2: Update the Database Initialization Script**
   - **Files to modify**: `menu-service/src/main/resources/import.sql`
   - **Changes needed**: Update the `INSERT` statements to include values for the new `description` and `rating` columns.

   ```sql
   -- Example update for the first insert statement
   insert into menu(id, item_name, item_price, default_spice_level, tag_line, item_image_url, item_thumbnail_url, item_status, description, rating) values (nextval('hibernate_sequence'), 'Curry Plate', 12.5, 3, 'Spicy touch for your taste buds!!' , 'https://unsplash.com/photos/0wn-DdavPa4', 'https://unsplash.com/photos/0wn-DdavPa4', 1, 'A delicious curry plate.', 4);
   ```

3. **Step 3: Update the Menu Resource (Controller)**
   - **Files to modify**: `menu-service/src/main/java/org/google/demo/MenuResource.java`
   - **Changes needed**: In the `update` method, add logic to update the `description` and `rating` fields of the `Menu` entity.

   ```java
   // ... existing code ...
   @PUT
   @Transactional
   @Path("{id}")
   public Menu update(@PathParam("id") Long id, Menu menu) {

       Menu entity = menuRepository.findById(id);
       if (entity == null) {
           throw new WebApplicationException("Menu item with id"+id+"does not exist", 404);
       }

       // ... existing updates ...
       if (menu.status != null) entity.status = menu.status;
       if (menu.description != null) entity.description = menu.description;
       entity.rating = menu.rating;

       return entity;
   }
   // ... existing code ...
   ```

4. **Step 4: Update the Unit Tests**
   - **Files to modify**: `menu-service/src/test/java/org/google/demo/MenuResourceTest.java`
   - **Changes needed**: Add a new test or update an existing one to verify that the `description` and `rating` fields can be created and updated via the `/menu` endpoint.

   ```java
    @Test
    public void testUpdateMenu() {
        Menu updatedMenu = new Menu();
        updatedMenu.description = "New Description";
        updatedMenu.rating = 5;

        given()
            .contentType(ContentType.JSON)
            .body(updatedMenu)
            .when().put("/menu/1")
            .then()
            .statusCode(200)
            .body("description", is("New Description"))
            .body("rating", is(5));
    }
   ```

### Testing Strategy
- **Unit Testing**: Run the existing and new unit tests using `mvn test`. The `MenuResourceTest` should cover the API endpoints.
- **Integration Testing**: Start the Quarkus application and use a tool like `curl` or Postman to manually test the CRUD operations on the `/menu` endpoint.
  - Verify that `GET /menu` returns items with the new `description` and `rating` fields.
  - Verify that `POST /menu` can create a new item with the new fields.
  - Verify that `PUT /menu/{id}` can update the `description` and `rating` fields.

## 🎯 Success Criteria
- The `Menu` entity in the Java code contains `description` and `rating` fields.
- The database schema for the `menu` table has `description` and `rating` columns.
- The `import.sql` script populates the new columns with sample data.
- The REST API endpoints for creating and updating menu items correctly handle the new fields.
- All unit tests pass.
