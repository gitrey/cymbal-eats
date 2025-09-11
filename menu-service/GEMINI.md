# Gemini Code Assistant Context: Cymbal Eats - Menu Service

## Project Overview

This project is the `menu-service`, a Java-based RESTful microservice for the "Cymbal Eats" application. Its primary responsibility is to manage restaurant menu items.

It is built using the [Quarkus](https://quarkus.io/) framework, which allows for both standard JVM and native binary builds (using GraalVM). The service uses JAX-RS for defining REST endpoints, Hibernate ORM with the Panache pattern for database interaction, and Jackson for JSON serialization. The database is PostgreSQL.

The core data model is the `Menu` entity, which includes fields like item name, price, image URLs, and a status (`Processing`, `Ready`, `Failed`).

## Building and Running

The project uses Maven and the Maven Wrapper (`mvnw`).

### Running in Development Mode

To run the service locally for development with live reload enabled, use the Quarkus dev command:

```bash
./mvnw quarkus:dev
```

The application will start, and the database schema will be automatically created and populated from `src/main/resources/import.sql`.

### Building the Application

There are two ways to build the application, as detailed in the `README.md`.

**1. JVM-based Build:**

This creates a standard JAR file.

```bash
./mvnw package -DskipTests
```

**2. Native Build (GraalVM):**

This compiles the application into a native executable, resulting in a smaller container image and faster startup times. You must have GraalVM installed and configured.

```bash
./mvnw package -Pnative -DskipTests
```

### Running Tests

To run the unit and integration tests for the service:

```bash
./mvnw test
```

The tests use an in-memory H2 database and the REST Assured library for testing the API endpoints.

## API Endpoints

The service exposes a standard CRUD API for managing menu items at the `/menu` path.

| Method | Path                 | Description                               |
|--------|----------------------|-------------------------------------------|
| `GET`    | `/menu`              | Retrieves all menu items.                 |
| `GET`    | `/menu/{id}`         | Retrieves a single menu item by its ID.   |
| `GET`    | `/menu/{status}`     | Retrieves items by status (`ready`, `failed`, `processing`). |
| `POST`   | `/menu`              | Creates a new menu item.                  |
| `PUT`    | `/menu/{id}`         | Updates an existing menu item.            |
| `DELETE` | `/menu/{id}`         | Deletes a menu item.                      |

Refer to the `README.md` for detailed examples of API requests and responses.

## Development Conventions

*   **Database:** The application is configured to connect to a PostgreSQL database. Connection details are provided via environment variables (`DB_HOST`, `DB_DATABASE`, `DB_USER`, `DB_PASS`).
*   **Data Seeding:** For development, the database is seeded with data from the `src/main/resources/import.sql` file.
*   **API:** The REST API is defined in the `MenuResource.java` class.
*   **Data Model:** The core JPA entity is `Menu.java`, which uses the Panache entity pattern for simplified data access.
