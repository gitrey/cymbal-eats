# Project Overview

This is the `menu-service` for the Cymbal Eats application. It is a Java application built with Quarkus, a cloud-native Java framework. The service provides a REST API for managing the restaurant's menu, including creating, reading, updating, and deleting menu items. It uses a PostgreSQL database to store the menu data and can be deployed as a lightweight, fast-starting native executable.

# Building and Running

## Prerequisites

*   Java 11+
*   Maven 3.8.1+
*   Docker (for building container images)
*   GraalVM (for building native images)

## Building the JVM-based Image

1.  **Package the application:**
    ```bash
    ./mvnw package -DskipTests
    ```

2.  **Build the Docker image:**
    ```bash
    docker build -f src/main/docker/Dockerfile.jvm -t menu-service .
    ```

## Building the Native Image

1.  **Package the application:**
    ```bash
    ./mvnw package -Pnative -DskipTests
    ```

2.  **Build the Docker image:**
    ```bash
    docker build -f src/main/docker/Dockerfile.native -t menu-service .
    ```

## Running the Service

The `README.md` file provides detailed instructions on how to deploy the service to Google Cloud Run.

## Running Tests

To run the tests, execute the following command:

```bash
./mvnw test
```

# Development Conventions

*   **Framework:** The project uses Quarkus, a cloud-native Java framework.
*   **API:** The service provides a REST API for managing menu items.
*   **Database:** The service uses a PostgreSQL database.
*   **Testing:** The project includes JUnit 5 tests.
*   **Deployment:** The service is designed to be deployed to Google Cloud Run.
