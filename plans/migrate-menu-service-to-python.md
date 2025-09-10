# Feature Implementation Plan: migrate-menu-service-to-python

## 📋 Todo Checklist
- [ ] Set up the new Python project structure and dependencies.
- [ ] Define the data models and schemas.
- [ ] Implement the database connection and initialization logic.
- [ ] Implement the REST API endpoints.
- [ ] Create a new Dockerfile for the Python service.
- [ ] Implement a testing strategy.
- [ ] Final Review and Testing.

## 🔍 Analysis & Investigation

### Current Codebase Structure
The existing `menu-service` is a Java application built with Quarkus and Maven. It uses Hibernate ORM with Panache for data persistence and JAX-RS for creating REST endpoints. The data model is defined in the `Menu.java` entity, and initial data is loaded from an `import.sql` script.

### Proposed Architecture (Python)
The new service will be built using Python with the following stack:
- **FastAPI**: A modern, high-performance web framework for building REST APIs. It provides automatic validation and API documentation (Swagger UI).
- **SQLAlchemy**: A powerful SQL toolkit and Object Relational Mapper (ORM) that will replace Hibernate/Panache.
- **Pydantic**: Used by FastAPI for data validation and defining schemas, which will serve as the data transfer objects.
- **Uvicorn**: An ASGI server to run the FastAPI application.
- **PostgreSQL**: The existing database will be used.

### Dependencies & Integration Points
The new service will integrate with the same PostgreSQL database as the old service. The REST API contract (endpoints, request/response structure) will be kept identical to ensure a seamless transition for any API consumers.

### Considerations & Challenges
- **Data Migration**: Since the same database is being used, no data migration is needed. However, the new SQLAlchemy model must be perfectly compatible with the existing table schema created by Hibernate.
- **Environment Variables**: Database connection details and other configurations will need to be managed, likely through environment variables.
- **Testing**: A new suite of tests will need to be written from scratch using Python's testing frameworks.

## 📝 Implementation Plan

### Prerequisites
- Python 3.9+ and `pip` installed.
- A virtual environment tool like `venv` is recommended.
- Access to the existing PostgreSQL database used by the Java service.

### Step-by-Step Implementation

1.  **Step 1: Create Project Structure and Install Dependencies**
    - **Action**: Create a new directory `menu-service-python` at the root of the project. Inside this directory, set up a Python virtual environment.
    - **Files to create**:
        - `menu-service-python/`
        - `menu-service-python/app/`
        - `menu-service-python/app/main.py`
        - `menu-service-python/requirements.txt`
    - **Changes needed**: Populate `requirements.txt` with the necessary libraries.
      ```
      fastapi
      uvicorn[standard]
      sqlalchemy
      psycopg2-binary
      pydantic
      ```
    - **Command**: `pip install -r requirements.txt`

2.  **Step 2: Define SQLAlchemy Model and Pydantic Schemas**
    - **Action**: Translate the `Menu.java` entity into a SQLAlchemy model and create Pydantic schemas for API validation.
    - **Files to create**:
        - `menu-service-python/app/models.py`
        - `menu-service-python/app/schemas.py`
    - **Changes needed**:
        - In `models.py`, define the `Menu` table structure using SQLAlchemy's ORM.
        - In `schemas.py`, define `MenuBase`, `MenuCreate`, and `Menu` Pydantic models to handle request input and response output.

3.  **Step 3: Implement Database Connection and Seeding**
    - **Action**: Set up the database connection and create a script to seed initial data, replacing the `import.sql` functionality.
    - **Files to create**:
        - `menu-service-python/app/database.py`
        - `menu-service-python/app/seed.py`
    - **Changes needed**:
        - In `database.py`, configure the SQLAlchemy engine and session management.
        - In `seed.py`, write a script to create the initial menu items in the database. This script should be run once manually.

4.  **Step 4: Implement the REST API Endpoints**
    - **Action**: Re-create the API endpoints from `MenuResource.java` using FastAPI.
    - **Files to modify**: `menu-service-python/app/main.py`
    - **Changes needed**: Define routes for `GET /menu`, `GET /menu/{id}`, `POST /menu`, `PUT /menu/{id}`, and `DELETE /menu/{id}`. Use the SQLAlchemy session for database operations and Pydantic schemas for data handling.

5.  **Step 5: Create a New Dockerfile**
    - **Action**: Create a `Dockerfile` to containerize the new Python application.
    - **Files to create**: `menu-service-python/Dockerfile`
    - **Changes needed**: Write a multi-stage Dockerfile that installs dependencies, copies the application code, and defines the command to run the Uvicorn server.

6.  **Step 6: Implement Unit and Integration Tests**
    - **Action**: Write tests for the new API endpoints.
    - **Files to create**:
        - `menu-service-python/tests/`
        - `menu-service-python/tests/test_menu_api.py`
    - **Changes needed**: Use `pytest` and FastAPI's `TestClient` to write tests for each API endpoint, covering success cases and error handling.

### Testing Strategy
- **Unit/Integration Testing**: Use `pytest` to run the tests in the `tests/` directory. The `TestClient` will allow for making requests to the API in a test environment without a running server.
- **Manual Testing**: Run the FastAPI application using `uvicorn app.main:app --reload` and interact with the auto-generated API documentation at `http://127.0.0.1:8000/docs`.

## 🎯 Success Criteria
- The new Python `menu-service-python` application is fully functional.
- It exposes the same REST API endpoints as the original Java service.
- It connects to and performs CRUD operations on the existing PostgreSQL database.
- All new unit and integration tests pass.
- The service can be successfully containerized using the new Dockerfile.
