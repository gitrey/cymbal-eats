# Feature Implementation Plan: menu-service-rating

## 📋 Todo Checklist
- [x] ~~Update `Menu.java` entity with new fields and validation.~~ ✅ Implemented
- [x] ~~Update `import.sql` to include sample data for the new fields.~~ ✅ Implemented
- [x] ~~Update `MenuResource.java` to handle the new fields in API requests and responses.~~ ✅ Implemented
- [x] ~~Add comprehensive unit tests for the new fields and validation logic.~~ ✅ Implemented
- [x] ~~Final Review and Testing~~ ✅ Implemented

## 🔍 Analysis & Investigation

### Codebase Structure
The `menu-service` is a standard Quarkus application. The key files are:
- `Menu.java`: The Panache entity representing a menu item.
- `MenuRepository.java`: The repository for the `Menu` entity.
- `MenuResource.java`: The JAX-RS resource that defines the REST API endpoints.
- `import.sql`: The SQL script for populating the database with initial data.
- `MenuResourceTest.java`: The JUnit 5 test for the `MenuResource`.

### Current Architecture
The architecture is a simple RESTful service using the Quarkus framework. It follows a standard three-tier architecture with a resource layer (`MenuResource`), a data access layer (`MenuRepository` and `Menu` entity), and a database.

### Dependencies & Integration Points
The `Menu` entity is the central component. It's used by the `MenuResource` to handle API requests and by the `MenuRepository` for database operations. The `import.sql` file is also tightly coupled to the `Menu` entity's schema.

### Considerations & Challenges
- **Validation:** The rating field has specific validation rules (integer, 1-5, not null). This should be implemented using Jakarta Bean Validation annotations in the `Menu` entity to ensure data integrity at the model level.
- **Testing:** The existing tests in `MenuResourceTest.java` use Mockito to mock the repository. New tests should be added to cover the new fields and validation logic, including tests for invalid rating values.

## 📝 Implementation Plan

### Prerequisites
- A working Java development environment with Maven and Quarkus CLI installed.
- Access to a PostgreSQL database for testing.

### Step-by-Step Implementation
1. **Update `Menu.java` Entity**
   - Files to modify: `menu-service/src/main/java/org/google/demo/Menu.java`
   - Changes needed:
     - Add a `description` field of type `String`.
     - Add a `rating` field of type `Integer`.
     - Add `@NotNull`, `@Min(1)`, and `@Max(5)` annotations to the `rating` field for validation.

2. **Update `import.sql`**
   - Files to modify: `menu-service/src/main/resources/import.sql`
   - Changes needed:
     - Add `description` and `rating` columns to the `insert` statements.
     - Provide sample values for the new columns for each of the existing menu items.

3. **Update `MenuResource.java`**
   - Files to modify: `menu-service/src/main/java/org/google/demo/MenuResource.java`
   - Changes needed:
     - In the `update` method, add logic to update the `description` and `rating` fields of the `Menu` entity.

4. **Add Unit Tests**
   - Files to modify: `menu-service/src/test/java/org/google/demo/MenuResourceTest.java`
   - Changes needed:
     - Add a new test method to verify that a `Menu` with a valid rating can be created successfully.
     - Add test methods to verify that creating a `Menu` with an invalid rating (e.g., null, 0, 6) results in a `400 Bad Request` response.
     - Update the existing `testCreateMenu` method to include the `description` and `rating` fields.

### Testing Strategy
- Run all existing and new unit tests using `mvn test`.
- Manually test the API endpoints using a tool like `curl` or Postman to ensure the new fields are handled correctly and the validation works as expected.

## 🎯 Success Criteria
- The `menu-service` API can successfully accept and return `description` and `rating` fields for menu items.
- The `rating` field is correctly validated, and requests with invalid ratings are rejected with a `400 Bad Request` status.
- All unit tests pass, and the code coverage for the new logic is adequate.
