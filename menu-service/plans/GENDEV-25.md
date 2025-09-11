# Feature Implementation Plan: GENDEV-25 - Menu Service Rating

## 📋 Todo Checklist
- [x] Update `Menu.java` entity with new fields
- [x] Update `import.sql` to include sample data for new fields
- [x] Update `MenuResource.java` to handle new fields in create and update operations
- [x] Update `MenuResourceTest.java` to include tests for the new fields
- [x] Final Review and Testing

## 🔍 Analysis & Investigation

### Codebase Structure
The `menu-service` is a standard Quarkus application.
- The core business entity is `Menu.java`.
- The REST endpoints are defined in `MenuResource.java`.
- Data persistence is handled by Hibernate Panache, with `MenuRepository.java` as the repository.
- Initial data is loaded from `src/main/resources/import.sql`.
- Tests are located in `src/test/java/org/google/demo/MenuResourceTest.java`.

### Current Architecture
The architecture is a simple RESTful service using the JAX-RS, CDI, and Hibernate Panache extensions from Quarkus. It follows the standard patterns for a CRUD service.

### Dependencies & Integration Points
The service is self-contained, but the `Menu` entity is likely consumed by other services in the Cymbal Eats application. The changes to the `Menu` entity will require changes in any downstream services that consume the `/menu` endpoint.

### Considerations & Challenges
- The `rating` field should probably have some validation (e.g., between 1 and 5). This can be implemented using Bean Validation annotations.
- The database schema will be updated automatically by Hibernate, but the `import.sql` needs to be updated manually.

## 📝 Implementation Plan

### Prerequisites
- A running instance of the `menu-service` for testing.
- The `mvnw` command should be available.

### Step-by-Step Implementation
1. **Step 1**: Update the `Menu` entity.
   - Files to modify: `src/main/java/org/google/demo/Menu.java`
   - Changes needed:
     - Add a `description` field of type `String`.
     - Add a `rating` field of type `int`.
     - Add `@Column` annotations for the new fields.

2. **Step 2**: Update the `import.sql` script.
   - Files to modify: `src/main/resources/import.sql`
   - Changes needed:
     - Add `description` and `rating` columns to the `insert` statements.
     - Provide sample values for the new columns for the existing menu items.

3. **Step 3**: Update the `MenuResource` class.
   - Files to modify: `src/main/java/org/google/demo/MenuResource.java`
   - Changes needed:
     - In the `update` method, add logic to update the `description` and `rating` fields of the `Menu` entity.

4. **Step 4**: Update the `MenuResourceTest` class.
   - Files to modify: `src/test/java/org/google/demo/MenuResourceTest.java`
   - Changes needed:
     - Update the `setup` method to include the new `description` and `rating` fields in the mock `Menu` object.
     - Update the `testCreateMenu` test to send the new fields in the request body and assert that they are correctly saved.
     - Add a new test method `testUpdateMenu` to verify that the `description` and `rating` fields can be updated.

### Testing Strategy
- Run the existing unit tests using `./mvnw test`.
- Manually test the new functionality by sending `POST` and `PUT` requests to the `/menu` endpoint with the new fields.
- Verify that the new fields are correctly persisted in the database.

## 🎯 Success Criteria
- The `Menu` entity has the new `description` and `rating` fields.
- The `/menu` endpoint accepts and returns the new fields.
- The new fields are correctly saved to and retrieved from the database.
- All existing and new unit tests pass.
