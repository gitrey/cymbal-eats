# Menu Service

## Overview

The Menu Service is a RESTful web service for managing a restaurant's menu. It provides endpoints to perform CRUD (Create, Read, Update, Delete) operations on menu items. The service is built using Java with the Quarkus framework.

## Prerequisites

Before you begin, ensure you have the following installed:
- Java 11 or later
- Maven 3.8.1 or later
- Docker (for building container images)

## Local Development

### Running the Application

You can run the application in development mode, which enables live coding.
1.  Navigate to the `menu-service` directory.
2.  Run the following command:

    ```bash
    ./mvnw quarkus:dev
    ```
The application will be available at `http://localhost:8080`.

### Running Tests

To run the unit and integration tests, use the following command:

```bash
./mvnw test
```

## API Endpoints

The service exposes the following REST endpoints under the `/menu` path.

| Method   | Path                  | Description                               |
|----------|-----------------------|-------------------------------------------|
| `GET`    | `/`                   | Get all menu items, sorted by name.       |
| `GET`    | `/{id}`               | Get a specific menu item by its ID.       |
| `GET`    | `/ready`              | Get all menu items with "Ready" status.   |
| `GET`    | `/failed`             | Get all menu items with "Failed" status.  |
| `GET`    | `/processing`         | Get all menu items with "Processing" status.|
| `POST`   | `/`                   | Create a new menu item.                   |
| `PUT`    | `/{id}`               | Update an existing menu item.             |
| `DELETE` | `/{id}`               | Delete a menu item by its ID.             |

### Example Usage (with `httpie`)

*   **Get all items:** `http GET :8080/menu`
*   **Create an item:** `http POST :8080/menu itemName=Pizza itemPrice=15.99`
*   **Update an item:** `http PUT :8080/menu/1 status=Ready`
*   **Delete an item:** `http DELETE :8080/menu/1`

## Building for Production

You can build the application as either a standard JVM-based package or a native executable.

### JVM Build

1.  **Package the application:**
    ```bash
    ./mvnw package -DskipTests
    ```
2.  **Build the Docker image:**
    ```bash
    docker build -f src/main/docker/Dockerfile.jvm -t gcr.io/YOUR_PROJECT_ID/menu-service .
    ```

### Native Build (GraalVM)

1.  **Package the application as a native executable:**
    ```bash
    ./mvnw package -Pnative -DskipTests
    ```
2.  **Build the native Docker image:**
    ```bash
    docker build -f src/main/docker/Dockerfile.native -t gcr.io/YOUR_PROJECT_ID/menu-service .
    ```

## Deployment on Google Cloud

This guide assumes you are deploying the service to Google Cloud Run with a Cloud SQL for PostgreSQL backend.

### 1. Set Up Environment Variables

First, set up the necessary environment variables in your shell.

```bash
export PROJECT_ID=$(gcloud config get-value project)
export PROJECT_NUMBER=$(gcloud projects describe $PROJECT_ID --format="value(projectNumber)")
export REGION="us-central1" # Change to your preferred region
```

### 2. Configure Cloud SQL

#### a. Enable APIs
```bash
gcloud services enable sqladmin.googleapis.com
```

#### b. Create a Cloud SQL Instance
Create a PostgreSQL instance with a private IP. This may take several minutes.

```bash
DB_INSTANCE_NAME=menu-catalog
gcloud sql instances create $DB_INSTANCE_NAME --database-version=POSTGRES_13 --region=$REGION --network=default
```

Once created, get the private IP address.
```bash
export DB_HOST=$(gcloud sql instances describe $DB_INSTANCE_NAME --format='value(ipAddresses.ipAddress)')
```

#### c. Create a Database
```bash
DB_DATABASE=menu-db
gcloud sql databases create $DB_DATABASE --instance=$DB_INSTANCE_NAME
```

#### d. Create a Database User
Replace `[CHANGEME]` with a secure password.
```bash
export DB_USER="menu-user"
export DB_PASSWORD="[CHANGEME]"
gcloud sql users create $DB_USER --instance=$DB_INSTANCE_NAME --password=$DB_PASSWORD
```

### 3. Configure Service Access

#### a. Grant Cloud SQL Client Role
Allow the Cloud Run service account to connect to the Cloud SQL instance.

```bash
gcloud projects add-iam-policy-binding $PROJECT_ID \
    --member="serviceAccount:$PROJECT_NUMBER-compute@developer.gserviceaccount.com" \
    --role="roles/cloudsql.client"
```

#### b. Set Up Serverless VPC Connector
Create a Serverless VPC Connector to allow Cloud Run to access the private IP of the Cloud SQL instance.

```bash
gcloud services enable vpcaccess.googleapis.com

SERVERLESS_VPC_CONNECTOR=cymbal-connector
gcloud compute networks vpc-access connectors create $SERVERLESS_VPC_CONNECTOR \
    --region=$REGION \
    --range=10.8.0.0/28
```

### 4. Build and Push the Docker Image

Build the container image (choose either JVM or native from the "Building for Production" section) and push it to Google Container Registry (GCR).

```bash
# Example for JVM build
docker build -f src/main/docker/Dockerfile.jvm -t gcr.io/$PROJECT_ID/menu-service .
docker push gcr.io/$PROJECT_ID/menu-service
```

### 5. Deploy to Cloud Run

Enable the Cloud Run API and deploy the service.

```bash
gcloud services enable run.googleapis.com

gcloud run deploy menu-service \
    --image=gcr.io/$PROJECT_ID/menu-service:latest \
    --region=$REGION \
    --allow-unauthenticated \
    --set-env-vars DB_USER=$DB_USER \
    --set-env-vars DB_PASS=$DB_PASSWORD \
    --set-env-vars DB_DATABASE=$DB_DATABASE \
    --set-env-vars DB_HOST=$DB_HOST \
    --vpc-connector $SERVERLESS_VPC_CONNECTOR
```

After deployment, Cloud Run will provide a service URL. You can use this URL to test the deployed service using the API endpoints described above.