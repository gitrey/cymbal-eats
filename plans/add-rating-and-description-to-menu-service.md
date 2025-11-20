# Feature Implementation Plan: add-rating-and-description-to-menu-service

## 📋 Todo Checklist
- [x] ~~Add `description` and `rating` fields to the `Menu` entity.~~ ✅ Implemented
- [x] ~~Update the `MenuResource` to handle the new fields.~~ ✅ Implemented
- [x] ~~Update `import.sql` with sample data for the new fields.~~ ✅ Implemented
- [x] ~~Add unit tests for the new fields and logic.~~ ✅ Implemented
- [ ] Final Review and Testing

## 🔍 Analysis & Investigation

### Codebase Structure
The `menu-service` is a standard Quarkus application. The key files for this feature are:
- `pom.xml`: Defines project dependencies. No changes are needed here.
- `src/main/java/org/google/demo/Menu.java`: The JPA entity for menu items. This file will be modified to add the new fields.
- `src/main/java/org/google/demo/MenuResource.java`: The JAX-RS resource that defines the REST API endpoints. This will be updated to handle the new fields in create and update operations.
- `src/main/resources/import.sql`: A SQL script to populate the database with initial data. This will be updated to include values for the new fields.
- `src/test/java/org/google/demo/MenuResourceTest.java`: The JUnit test for the `MenuResource`. This will be updated to test the new functionality.

### Current Architecture
The application follows a standard three-tier architecture:
1.  **Presentation Layer:** `MenuResource` exposes RESTful endpoints.
2.  **Business Layer:** Logic is contained within the `MenuResource` and `MenuRepository`.
3.  **Data Access Layer:** `MenuRepository` (using Hibernate Panache) and the `Menu` entity handle database interactions.

The architecture is straightforward and easy to extend.

### Dependencies & Integration Points
The service depends on a PostgreSQL database. The `quarkus-jdbc-postgresql` dependency in `pom.xml` manages the connection. No new dependencies are required for this feature.

### Considerations & Challenges
- **Database Schema:** The database schema will be automatically updated by Hibernate (`quarkus.hibernate-orm.database.generation=update` is the default in dev mode). For production, a proper database migration script would be needed, but for this exercise, we'll rely on the automatic update.
- **Validation:** The Confluence page specifies validation rules for the `rating` field (integer from 1 to 5, not null). This should be implemented in the `Menu` entity using Jakarta Bean Validation annotations (`@Min`, `@Max`, `@NotNull`).

## 📝 Implementation Plan

### Prerequisites
- A running PostgreSQL database instance.
- The project is set up and builds successfully.

### Step-by-Step Implementation
1. **Update the `Menu` entity:**
   - Files to modify: `src/main/java/org/google/demo/Menu.java`
   - Changes needed:
     - Add a `description` field of type `String`.
     - Add a `rating` field of type `Integer`.
     - Add `@NotNull`, `@Min(1)`, and `@Max(5)` annotations to the `rating` field for validation.
   - **Implementation Notes**: Added the `description` and `rating` fields to the `Menu` entity, along with the specified validation annotations.
   - **Status**: ✅ Completed

2. **Update the `MenuResource`:**
   - Files to modify: `src/main/java/org/google/demo/MenuResource.java`
   - Changes needed:
     - In the `update` method, add logic to update the `description` and `rating` fields of the `Menu` entity if they are provided in the request.
   - **Implementation Notes**: Updated the `update` method in `MenuResource.java` to handle the `description` and `rating` fields.
   - **Status**: ✅ Completed

3. **Update `import.sql`:**
   - Files to modify: `src/main/resources/import.sql`
   - Changes needed:
     - Add `description` and `rating` columns and values to the `insert` statements.
   - **Implementation Notes**: Updated the `import.sql` file to include `description` and `rating` values for the sample data.
   - **Status**: ✅ Completed

4. **Add Unit Tests:**
   - Files to modify: `src/test/java/org/google/demo/MenuResourceTest.java`
   - Changes needed:
     - Create a new test method `testCreateMenuWithRatingAndDescription` to verify that a menu item can be created with the new fields.
     - Create a new test method `testUpdateMenuWithRatingAndDescription` to verify that a menu item can be updated with the new fields.
     - Create a new test method `testCreateMenuWithInvalidRating` to verify that creating a menu item with an invalid rating returns a 400 Bad Request error.
   - **Implementation Notes**: Added the new test methods to `MenuResourceTest.java` and updated the `setup` method.
   - **Status**: ✅ Completed

### Testing Strategy
- **Unit Testing:** The new tests in `MenuResourceTest.java` will verify the core logic of the changes.
- **Integration Testing:** Once the service is running, you can use `curl` or a tool like Postman to manually test the API endpoints:
    - `POST /menu` with a request body containing `description` and `rating`.
    - `PUT /menu/{id}` with a request body containing `description` and `rating`.
    - `GET /menu` and `GET /menu/{id}` to verify that the new fields are returned.
    - `POST /menu` with an invalid `rating` to verify that a 400 error is returned.

## 🎯 Success Criteria
- The `menu-service` can successfully store and retrieve menu items with `description` and `rating` fields.
- The API validates the `rating` field.
- All existing and new unit tests pass.
