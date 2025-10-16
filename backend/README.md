# Simple Diaries Backend

Backend API for the Simple Diaries Application built with Spring Boot 3.4.x and Java 21.

## Technology Stack

- **Java**: 21 LTS
- **Spring Boot**: 3.4.0
- **Spring Security**: JWT-based authentication
- **Spring Data JPA**: Database operations
- **PostgreSQL**: 17.x database
- **JUnit 5**: Unit testing
- **MockMvc**: Integration testing
- **JaCoCo**: Code coverage (60% minimum)

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/diaries/
│   │   │   ├── controller/     # REST controllers
│   │   │   ├── service/        # Business logic
│   │   │   ├── repository/     # Data access layer
│   │   │   ├── entity/         # JPA entities
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── security/       # Security configuration & JWT
│   │   │   └── exception/      # Custom exceptions
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/diaries/   # Test classes
└── pom.xml
```

## Prerequisites

- **Java 21 LTS or higher** (REQUIRED - Spring Boot 3.4.x requires Java 21)
  - Download: [Adoptium](https://adoptium.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
  - Verify: `java -version` should show version 21 or higher
- Maven 3.8+ (or use included Maven wrapper `./mvnw`)
- PostgreSQL 17.x (or use Docker Compose)

## Configuration

Set the following environment variables or update `application.properties`:

- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `JWT_SECRET`: Secret key for JWT token generation (minimum 256 bits)

## Running the Application

### Using Maven Wrapper (Recommended)

```bash
# Build the project
./mvnw clean package

# Run the application
./mvnw spring-boot:run

# Run tests
./mvnw test

# Generate code coverage report
./mvnw jacoco:report
```

### Using Maven (if installed globally)

```bash
# Build the project
mvn clean package

# Run the application
mvn spring-boot:run

# Run tests
mvn test

# Generate code coverage report
mvn jacoco:report
```

### Using Docker

See the root `docker-compose.yml` file to run the entire stack.

## API Endpoints

The API will be available at `http://localhost:8080/api`

Documentation will be added as endpoints are implemented.

## Testing

Run tests with coverage:

```bash
./mvnw clean test jacoco:report
```

Coverage reports are generated in `target/site/jacoco/index.html`

## Code Coverage

The project enforces a minimum of 60% code coverage using JaCoCo.
