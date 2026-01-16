# Feature Implementation Plan: Update Menu Service

## 📋 Todo Checklist
- [x] ~~Update `Menu.java` entity with new fields~~ ✅ Implemented
- [x] ~~Update `import.sql` with sample data for new fields~~ ✅ Implemented
- [x] ~~Update `MenuResource.java` to handle new fields in API responses and requests~~ ✅ Implemented
- [x] ~~Add unit tests for the new fields in `MenuResourceTest.java`~~ ✅ Implemented
- [x] ~~Final Review and Testing~~ ✅ Implemented

## 🔍 Analysis & Investigation

### Codebase Structure
The `menu-service` is a standard Quarkus application. The key files are:
- `pom.xml`: Defines the project dependencies, including Quarkus, Panache, and PostgreSQL.
- `src/main/java/org/google/demo/Menu.java`: The JPA entity for the menu items.
- `src/main/java/org/google/demo/MenuRepository.java`: The Panache repository for the `Menu` entity.
- `src/main/java/org/google/demo/MenuResource.java`: The JAX-RS resource that exposes the API endpoints for the menu.
- `src/main/resources/import.sql`: Contains the initial data for the menu items.
- `src/test/java/org/google/demo/MenuResourceTest.java`: Contains unit tests for the `MenuResource`.

### Current Architecture
The service uses a simple three-tier architecture:
- **Presentation Layer**: `MenuResource.java` (JAX-RS)
- **Business Logic Layer**: Implicitly handled by the resource and repository.
- **Data Access Layer**: `MenuRepository.java` (Hibernate Panache) and `Menu.java` (JPA).

The application uses a PostgreSQL database, and the initial data is loaded from `import.sql`.

### Dependencies & Integration Points
- **Quarkus**: The core framework.
- **Hibernate ORM with Panache**: For data persistence.
- **RESTEasy**: For the RESTful API.
- **PostgreSQL Driver**: For database connectivity.

The service is likely consumed by a frontend service (e.g., `customer-ui` or `employee-ui`) that displays the menu.

### Considerations & Challenges
- The database schema will be updated automatically by Hibernate (`ddl-auto`), but the `import.sql` needs to be updated manually.
- The API contract will change, so any consumer of this service will need to be updated as well.
- The tests need to be updated to reflect the changes in the entity and API.

## 📝 Implementation Plan

### Prerequisites
- A running PostgreSQL database.
- Maven and JDK installed.

### Step-by-Step Implementation
1. **Update `Menu.java` entity**
   - Files to modify: `src/main/java/org/google/demo/Menu.java`
   - Changes needed: Add the following fields to the `Menu` class:
     ```java
     @Column(name="description")
     public String description;

     @Column(name="rating")
     public Double rating;
     ```

2. **Update `import.sql`**
   - Files to modify: `src/main/resources/import.sql`
   - Changes needed: Add `description` and `rating` columns to the `insert` statements.
     ```sql
     insert into menu(id, item_name, item_price, default_spice_level, tag_line, item_image_url, item_thumbnail_url, item_status, description, rating) values (nextval('hibernate_sequence'), 'Curry Plate', 12.5, 3, 'Spicy touch for your taste buds!!' , 'https://unsplash.com/photos/0wn-DdavPa4', 'https://unsplash.com/photos/0wn-DdavPa4', 1, 'A delicious plate of curry with rice.', 4.5);
     insert into menu(id, item_name, item_price, default_spice_level, tag_line,  item_image_url, item_thumbnail_url, item_status, description, rating) values (nextval('hibernate_sequence'), 'Full Meal in Banana Leaf', 20.25, 2, 'South Indian delight!!', 'https://unsplash.com/photos/yCIcDyKm440', 'https://unsplash.com/photos/yCIcDyKm440',1, 'A complete meal served on a banana leaf.', 4.8);
     insert into menu(id, item_name, item_price, default_spice_level, tag_line,  item_image_url, item_thumbnail_url, item_status, description, rating) values (nextval('hibernate_sequence'), 'Gulab Jamoon', 2.40, 0, 'Sweet cottage cheese dumplings', 'https://images.freeimages.com/images/large-previews/095/gulab-jamun-1637925.jpg','https://images.freeimages.com/images/large-previews/095/gulab-jamun-1637925.jpg', 2, 'A classic Indian sweet.', 4.9);
     ```

3. **Update `MenuResource.java`**
   - Files to modify: `src/main/java/org/google/demo/MenuResource.java`
   - Changes needed: Update the `update` method to handle the new fields.
     ```java
     @PUT
     @Transactional
     @Path("{id}")
     public Menu update(@PathParam("id") Long id, Menu menu) {

         Menu entity = menuRepository.findById(id);
         if (entity == null) {
             throw new WebApplicationException("Menu item with id"+id+"does not exist", 404);
         }

         if (menu.itemName != null) entity.itemName=menu.itemName;
         if (menu.itemPrice != null) entity.itemPrice=menu.itemPrice;
         if (menu.tagLine != null) entity.tagLine=menu.tagLine;
         entity.spiceLevel=menu.spiceLevel;
         if (menu.itemImageURL != null) entity.itemImageURL = menu.itemImageURL;
         if (menu.itemThumbnailURL != null) entity.itemThumbnailURL = menu.itemThumbnailURL;
         if (menu.status != null) entity.status = menu.status;
         if (menu.description != null) entity.description = menu.description;
         if (menu.rating != null) entity.rating = menu.rating;

         return entity;
     }
     ```

4. **Update `MenuResourceTest.java`**
   - Files to modify: `src/test/java/org/google/demo/MenuResourceTest.java`
   - Changes needed: Update the `setup` and `testCreateMenu` methods to include the new fields.
     ```java
     @BeforeEach
     public void setup() {
         Menu menu = new Menu();
         menu.id = 1L;
         menu.itemName = "Test Item";
         menu.itemPrice = BigDecimal.valueOf(10.0);
         menu.spiceLevel = 1;
         menu.tagLine = "Test Tagline";
         menu.itemImageURL = null; // Set to null or a valid URL
         menu.itemThumbnailURL = null; // Set to null or a valid URL
         menu.status = Status.Ready;
         menu.description = "Test Description";
         menu.rating = 4.5;

         Mockito.when(menuRepository.findById(1L)).thenReturn(menu);
         Mockito.when(menuRepository.listAll()).thenReturn(Collections.singletonList(menu));
         Mockito.doAnswer(invocation -> {
             Menu m = invocation.getArgument(0);
             m.id = 1L;
             return null;
         }).when(menuRepository).persist(any(Menu.class));
     }

     @Test
     public void testCreateMenu() {
         Menu menu = new Menu();
         menu.itemName = "Test Item";
         menu.itemPrice = java.math.BigDecimal.valueOf(10.0);
         menu.spiceLevel = 1;
         menu.tagLine = "Test Tagline";
         menu.itemImageURL = null; // Set to null or a valid URL
         menu.itemThumbnailURL = null; // Set to null or a valid URL
         menu.status = Status.Ready;
         menu.description = "Test Description";
         menu.rating = 4.5;

         given()
             .contentType(ContentType.JSON)
             .body(menu)
             .when().post("/menu")
             .then()
             .statusCode(200)
             .body("id", notNullValue())
             .body("itemName", is("Test Item"))
             .body("description", is("Test Description"))
             .body("rating", is(4.5f));
     }
     ```

### Testing Strategy
- Run the existing unit tests to ensure that the changes haven't broken anything.
- Add new assertions to the existing tests to verify that the new fields are handled correctly.
- Manually test the API endpoints using a tool like `curl` or Postman.

## 🎯 Success Criteria
- The `Menu` entity has the new `description` and `rating` fields.
- The `import.sql` script populates the new fields with sample data.
- The API endpoints handle the new fields correctly.
- The unit tests pass and cover the new fields.
