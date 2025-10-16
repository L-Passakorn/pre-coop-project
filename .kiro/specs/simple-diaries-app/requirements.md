# Requirements Document

## Introduction

The Simple Diaries App is a full-stack web application that enables users to create, manage, and search their personal daily diary entries securely. The application demonstrates professional development practices with modern technologies, including secure authentication, comprehensive testing, and containerized deployment. Users can maintain private diary entries with robust search capabilities, while the system ensures data privacy through proper authorization checks.

## Requirements

### Requirement 1: User Authentication and Security

**User Story:** As a user, I want to securely register, login, and logout of the application, so that my diary entries remain private and protected.

#### Acceptance Criteria

1. WHEN a new user submits valid registration information THEN the system SHALL create a new user account with a hashed password using BCrypt
2. WHEN a user submits valid login credentials THEN the system SHALL authenticate the user and return a JSON Web Token (JWT)
3. WHEN a user logs out THEN the system SHALL invalidate the current session token
4. IF a user attempts to register with an existing email THEN the system SHALL return an error message indicating the email is already in use
5. WHEN a user submits invalid login credentials THEN the system SHALL return an authentication error without revealing whether the email or password was incorrect
6. WHEN a JWT token expires THEN the system SHALL require the user to re-authenticate
7. IF a request is made without a valid JWT token to a protected endpoint THEN the system SHALL return a 401 Unauthorized response

### Requirement 2: Diary Entry Management (CRUD Operations)

**User Story:** As a logged-in user, I want to create, read, update, and delete my diary entries, so that I can maintain my personal daily journal.

#### Acceptance Criteria

1. WHEN an authenticated user creates a new diary entry THEN the system SHALL store the entry with the user's ID, content, title, and timestamp
2. WHEN an authenticated user requests their diary entries THEN the system SHALL return only entries belonging to that user
3. WHEN an authenticated user updates their diary entry THEN the system SHALL modify only the specified entry if it belongs to that user
4. WHEN an authenticated user deletes their diary entry THEN the system SHALL remove only the specified entry if it belongs to that user
5. IF a user attempts to access, modify, or delete another user's diary entry THEN the system SHALL return a 403 Forbidden response
6. WHEN a diary entry is created or updated THEN the system SHALL validate that required fields (title, content, date) are present and properly formatted
7. WHEN a user views a single diary entry THEN the system SHALL display the entry's title, content, creation date, and last modified date

### Requirement 3: Search and Filter Functionality

**User Story:** As a user, I want to search my diary entries by keywords and filter by date ranges, so that I can quickly find specific entries from my journal history.

#### Acceptance Criteria

1. WHEN a user performs a keyword search THEN the system SHALL return all diary entries containing the keyword in either the title or content using full-text search
2. WHEN a user filters by a date range THEN the system SHALL return all diary entries created within the specified start and end dates
3. WHEN a user filters by a specific date THEN the system SHALL return all diary entries created on that exact date
4. WHEN a user combines keyword search with date filtering THEN the system SHALL return entries matching both criteria
5. WHEN search or filter returns no results THEN the system SHALL display an appropriate empty state message
6. WHEN a user performs a search THEN the system SHALL only search within their own diary entries, not other users' entries

### Requirement 4: Backend API Architecture

**User Story:** As a developer, I want a well-structured RESTful API built with Spring Boot 3.4.x and Java 21, so that the backend is maintainable, scalable, and follows industry best practices.

#### Acceptance Criteria

1. WHEN the backend application starts THEN the system SHALL use Spring Boot 3.4.x with Java 21 LTS
2. WHEN API endpoints are designed THEN the system SHALL follow RESTful conventions with appropriate HTTP methods (GET, POST, PUT, DELETE)
3. WHEN the backend processes requests THEN the system SHALL use Spring Security with JWT for authentication and authorization
4. WHEN the backend connects to the database THEN the system SHALL use PostgreSQL 17.x as the data store
5. WHEN API responses are returned THEN the system SHALL use appropriate HTTP status codes and consistent JSON response formats
6. WHEN errors occur THEN the system SHALL return structured error responses with meaningful messages
7. WHEN the application is structured THEN the system SHALL follow layered architecture (Controller, Service, Repository layers)

### Requirement 5: Frontend Application

**User Story:** As a user, I want a modern, responsive, and intuitive user interface built with Next.js 15.x, so that I can easily manage my diary entries across different devices.

#### Acceptance Criteria

1. WHEN the frontend application is built THEN the system SHALL use Next.js 15.x with App Router and TypeScript
2. WHEN UI components are implemented THEN the system SHALL use shadcn/ui components styled with Tailwind CSS
3. WHEN global state is managed THEN the system SHALL use Zustand for client-side state management
4. WHEN server data is fetched THEN the system SHALL use TanStack Query for server state management and caching
5. WHEN users interact with the UI THEN the system SHALL provide smooth animations and micro-interactions using GSAP and/or Framer Motion
6. WHEN forms are submitted THEN the system SHALL validate input on the client-side before sending requests to the backend
7. WHEN API errors occur THEN the system SHALL display user-friendly error messages
8. WHEN the application is accessed on different screen sizes THEN the system SHALL provide a responsive layout

### Requirement 6: Validation and Error Handling

**User Story:** As a user, I want clear feedback when I make mistakes or when errors occur, so that I can understand what went wrong and how to fix it.

#### Acceptance Criteria

1. WHEN a user submits a form with invalid data THEN the system SHALL display field-specific validation errors on the frontend
2. WHEN the backend receives invalid data THEN the system SHALL validate the request and return detailed validation errors
3. WHEN a network error occurs THEN the system SHALL display a user-friendly error message with retry options
4. WHEN a server error occurs THEN the system SHALL log the error details and return a generic error message to the user
5. WHEN validation fails on password requirements THEN the system SHALL clearly indicate the password strength requirements
6. WHEN a user attempts an unauthorized action THEN the system SHALL display an appropriate access denied message

### Requirement 7: Testing and Quality Assurance

**User Story:** As a developer, I want comprehensive unit tests for critical business logic, so that the application is reliable and regressions are caught early.

#### Acceptance Criteria

1. WHEN unit tests are written THEN the system SHALL use JUnit for testing framework
2. WHEN controller endpoints are tested THEN the system SHALL use MockMvc for integration testing
3. WHEN business logic is tested THEN the system SHALL achieve a minimum of 60% code coverage measured by JaCoCo
4. WHEN authentication logic is tested THEN the system SHALL include unit tests for registration, login, and JWT validation
5. WHEN diary CRUD operations are tested THEN the system SHALL include unit tests for all create, read, update, and delete operations
6. WHEN authorization is tested THEN the system SHALL verify that users cannot access other users' diary entries
7. WHEN search functionality is tested THEN the system SHALL include tests for keyword search and date filtering

### Requirement 8: Containerization and Local Development

**User Story:** As a developer, I want the entire application stack containerized with Docker, so that I can easily set up and run the application locally without complex configuration.

#### Acceptance Criteria

1. WHEN Docker configuration is provided THEN the system SHALL include a Dockerfile for the backend application
2. WHEN Docker configuration is provided THEN the system SHALL include a Dockerfile for the frontend application
3. WHEN Docker Compose is used THEN the system SHALL orchestrate the backend, frontend, and PostgreSQL database containers
4. WHEN the application is started with Docker Compose THEN the system SHALL automatically set up the database with required schemas
5. WHEN containers are running THEN the system SHALL enable communication between frontend, backend, and database services
6. WHEN environment variables are needed THEN the system SHALL use Docker Compose environment configuration
7. WHEN a developer runs docker-compose up THEN the system SHALL start all services and make the application accessible

### Requirement 9: CI/CD Pipeline

**User Story:** As a developer, I want an automated CI/CD pipeline using GitHub Actions, so that code quality is maintained and builds are automated on every push.

#### Acceptance Criteria

1. WHEN code is pushed to the repository THEN the system SHALL trigger a GitHub Actions workflow
2. WHEN the CI pipeline runs THEN the system SHALL build the backend application
3. WHEN the CI pipeline runs THEN the system SHALL execute all unit tests
4. WHEN the CI pipeline runs THEN the system SHALL generate a code coverage report using JaCoCo
5. WHEN the CI pipeline runs THEN the system SHALL perform code quality checks
6. WHEN the CI pipeline runs THEN the system SHALL build Docker images for both frontend and backend
7. IF any step in the pipeline fails THEN the system SHALL mark the build as failed and notify the developer
8. WHEN all pipeline steps succeed THEN the system SHALL mark the build as successful
