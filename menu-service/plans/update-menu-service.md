# Feature Implementation Plan: Update Menu Service

## 📋 Todo Checklist
- [x] ~~Add `description` and `rating` fields to the `Menu` entity.~~ ✅ Implemented
- [x] ~~Update `import.sql` with sample data for the new fields.~~ ✅ Implemented
- [x] ~~Update `MenuResource` to handle the new fields in create and update operations.~~ ✅ Implemented
- [x] ~~Add validation for the `rating` field.~~ ✅ Implemented
- [x] ~~Update `MenuResourceTest` to include tests for the new fields and validation.~~ ✅ Implemented
- [x] ~~Final Review and Testing~~ ✅ Implemented

## 🔍 Analysis & Investigation

### Codebase Structure
The project is a standard Quarkus application.
- `src/main/java/org/google/demo/Menu.java`: The JPA entity for the menu item.
- `src/main/java/org/google/demo/MenuRepository.java`: The Panache repository for the `Menu` entity.
- `src/main/java/org/google/demo/MenuResource.java`: The JAX-RS resource that exposes the REST API for menu items.
- `src/main/resources/import.sql`: The SQL script to populate the database with initial data.
- `src/test/java/org/google/demo/MenuResourceTest.java`: The JUnit 5 test for the `MenuResource`.

### Current Architecture
The application follows a simple three-tier architecture:
1.  **Presentation Layer:** `MenuResource` (JAX-RS)
2.  **Business Logic/Data Access Layer:** `MenuRepository` (Panache)
3.  **Data Layer:** PostgreSQL database

The `Menu` entity is a simple JPA entity. The `MenuResource` exposes CRUD operations on the `Menu` entity.

### Dependencies & Integration Points
- **Quarkus:** The core framework.
- **Hibernate ORM with Panache:** For data persistence.
- **JAX-RS (RESTEasy):** For the REST API.
- **PostgreSQL:** The database.

### Considerations & Challenges
- The `rating` field has specific validation rules (integer between 1 and 5). This validation should be implemented in the `Menu` entity using Jakarta Bean Validation annotations.
- The existing tests in `MenuResourceTest` use Mockito to mock the repository. The new tests should follow the same pattern.

## 📝 Implementation Plan

### Prerequisites
- No new dependencies are required.

### Step-by-Step Implementation
1. **Update `Menu.java`**
   - Files to modify: `src/main/java/org/google/demo/Menu.java`
   - Changes needed:
     - Add a `description` field of type `String`.
     - Add a `rating` field of type `int`.
     - Add `@Column` annotations for the new fields.
     - Add Jakarta Bean Validation annotations to the `rating` field: `@Min(1)` and `@Max(5)`.

2. **Update `import.sql`**
   - Files to modify: `src/main/resources/import.sql`
   - Changes needed:
     - Add `description` and `rating` columns to the `insert` statements.
     - Provide sample values for the new columns.

3. **Update `MenuResource.java`**
   - Files to modify: `src/main/java/org/google/demo/MenuResource.java`
   - Changes needed:
     - In the `update` method, add logic to update the `description` and `rating` fields of the `Menu` entity.

4. **Update `MenuResourceTest.java`**
   - Files to modify: `src/test/java/org/google/demo/MenuResourceTest.java`
   - Changes needed:
     - In the `setup` method, set values for the `description` and `rating` fields of the mock `Menu` object.
     - In the `testCreateMenu` test, set values for the `description` and `rating` fields of the `Menu` object being created.
     - Add new tests to verify the validation of the `rating` field (e.g., test that a value of 0 or 6 is rejected).
     - Add a test for the update functionality to ensure the new fields are updated correctly.

### Testing Strategy
- Add the `quarkus-hibernate-validator` dependency to the `pom.xml` file.
- Create a `test-import.sql` file in `src/test/resources` to create the `hibernate_sequence` for the test environment.
- Add the `@Valid` annotation to the `create` method's `menu` parameter in `MenuResource.java` to enable validation.
- Run the existing and new unit tests using `./mvnw test`.
- Manually test the API using `curl` or a REST client to verify that the new fields are present in the responses and that the validation is working correctly.

## 🎯 Success Criteria
- The `description` and `rating` fields are successfully added to the `Menu` entity and the database schema.
- The REST API correctly handles the new fields for creating, reading, and updating menu items.
- The validation for the `rating` field is working as expected.
- All unit tests pass.
