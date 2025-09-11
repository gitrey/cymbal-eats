---
theme: default
paginate: true
---

# Cymbal Eats: Menu Servicec

A Technical Overview

---

# What is the Menu Service?

- A backend microservice for the **Cymbal Eats** application.
- **Primary Role:** Manages all restaurant menu items.
- Provides a complete REST API for creating, reading, updating, and deleting (CRUD) menu data.

---

# Technology Stack

- **Language:** Java
- **Framework:** Quarkus (for high performance and native builds)
- **Database:** PostgreSQL
- **API Layer:** JAX-RS (for creating REST endpoints)
- **Data Access:** Hibernate ORM with Panache (simplifies database operations)
- **Build Tool:** Maven

---

# Core Architecture

- **`MenuResource.java` (Controller):** Defines and implements all the REST API endpoints. This is the entry point for all external requests.
- **`MenuRepository.java` (Repository):** Handles the direct communication with the database.
- **`Menu.java` (JPA Entity):** The data model that represents a menu item in the database.
- **`application.properties`:** Configuration file for database connections, logging, and other settings.

---

# Key API Endpoints

The API is available under the `/menu` path.

- `GET /menu`: Get all menu items.
- `GET /menu/{id}`: Get a single item by its ID.
- `GET /menu/{status}`: Get items filtered by status (e.g., `ready`, `failed`).
- `POST /menu`: Create a new menu item.
- `PUT /menu/{id}`: Update an existing item.
- `DELETE /menu/{id}`: Remove an item.

---

# How to Build & Run

- **Run in Dev Mode:**
  `./mvnw quarkus:dev`
- **Run Tests:**
  `./mvnw test`
- **Build for JVM:**
  `./mvnw package -DskipTests`
- **Build a Native Executable:**
  `./mvnw package -Pnative -DskipTests`
