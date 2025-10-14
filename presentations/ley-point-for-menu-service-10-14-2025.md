---
theme: default
paginate: true

# Cymbal Eats: Menu Service

A deep dive into the microservice managing our menu catalog.

---

# Service Overview

The **Menu Service** is a core component of the Cymbal Eats backend.

- **Purpose:** Manages the restaurant's menu items.
- **Functionality:** Provides a RESTful API for all CRUD (Create, Read, Update, Delete) operations.
- **Architecture:** Designed as a lightweight, containerized microservice.

---

# Core Technologies

Built with modern, cloud-native Java technologies.

- **Framework:** Quarkus for fast startup and low memory consumption.
- **Language:** Java
- **API:** JAX-RS (RESTEasy) for building RESTful web services.
- **Database:** Hibernate ORM with Panache for simplified data access to a PostgreSQL database.
- **Build:** Maven

---

# Key API Features

The service offers a comprehensive set of endpoints for menu management.

- **`GET /menu`**: Retrieve all menu items.
- **`GET /menu/{status}`**: Filter items by status (e.g., `ready`, `processing`, `failed`).
- **`GET /menu/{id}`**: Fetch a single item by its unique ID.
- **`POST /menu`**: Add a new item to the menu.
- **`PUT /menu/{id}`**: Update an existing menu item.
- **`DELETE /menu/{id}`**: Remove an item from the menu.

---

# Deployment Architecture

Deployed for scalability and reliability on Google Cloud.

- **Containerization:** Packaged as a Docker image using Jib.
- **Hosting:** Runs on **Google Cloud Run**, a serverless platform.
- **Database:** Connects to a **Cloud SQL for PostgreSQL** instance.
- **Networking:** Uses a **Serverless VPC Connector** to securely access the database.
- **Build Options:** Can be built as a standard JVM image or a highly optimized GraalVM native image.

