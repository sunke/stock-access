# Stock Access Application

This project is a Spring Boot application that uses PostgreSQL as its database. The application is written in Kotlin and uses Maven for dependency management.

## Prerequisites

- Docker
- Docker Compose
- Java 17 or higher
- Maven

## Getting Started

### Starting PostgreSQL with Docker Compose

1. Ensure Docker and Docker Compose are installed on your machine.
2. Navigate to the project directory.
3. Run the following command under the subfolder `docker` to start the PostgreSQL service:

    ```sh
    docker compose up
    ```

   This command will start the PostgreSQL container and initialize the database using the `init.sql` script located in the `initdb` directory.

4. To stop the PostgreSQL service and remove the volumes, run:

    ```sh
    docker compose down --volume
    ```

### Running the Application

1. Ensure PostgreSQL is running (see the previous section).
2. Navigate to the project directory.
3. Build the project using Maven:

    ```sh
    mvn clean install
    ```

4. Run the Spring Boot application:

    ```sh
    mvn spring-boot:run
    ```

   The application will start on port 8080 by default.

### Configuration

The database configuration is specified in the `src/main/resources/application.properties` file:

```ini
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/stockdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration
server.port=8080

# Logging Configuration
logging.level.org.springframework=INFO
logging.level.com.example=DEBUG

# OpenAPI/Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# Security Configuration
spring.security.user.name=user
spring.security.user.password=password
```

### Accessing the Application
The application will be available at http://localhost:8080.
Swagger UI can be accessed at http://localhost:8080/swagger-ui.html.

### Testing the Application
1. Get all stocks (with pagination)
```sh
curl -u user:password -X GET "http://localhost:8080/api/stocks?page=0&size=5"
```

2. Get a specific stock
```sh
curl -u user:password -X GET "http://localhost:8080/api/stocks/1"
```

3. Create a new stock
```sh
curl -u user:password -X POST "http://localhost:8080/api/stocks" \
-H "Content-Type: application/json" \
-d '{"name":"Netflix Inc.","currentPrice":610.50}'
```

4. Update a stock price
```sh
curl -u user:password -X PATCH "http://localhost:8080/api/stocks/1" \
-H "Content-Type: application/json" \
-d '{"name":"Not Used","currentPrice":195.75}'
```

5. Delete a stock
```sh
curl -u user:password -X DELETE "http://localhost:8080/api/stocks/1"
```
