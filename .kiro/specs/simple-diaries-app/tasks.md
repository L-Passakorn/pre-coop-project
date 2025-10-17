# Implementation Plan

- [x] 1. Initialize backend Spring Boot project structure





  - Create Spring Boot 3.4.x project with Java 21 using Spring Initializr or Maven
  - Configure pom.xml with dependencies: Spring Web, Spring Security, Spring Data JPA, PostgreSQL driver, JWT library, JUnit, MockMvc, JaCoCo
  - Set up application.properties with database connection and JWT configuration placeholders
  - Create package structure: controller, service, repository, entity, dto, security, exception
  - _Requirements: 4.1, 4.7_

- [x] 2. Implement database entities and repositories


  - [x] 2.1 Create User entity with JPA annotations


    - Write User entity class with fields: id, email, password, fullName, createdAt, updatedAt
    - Add JPA annotations for table mapping, unique constraints, and timestamps
    - Implement relationship with DiaryEntry (OneToMany)
    - _Requirements: 1.1, 4.4_
  
  - [x] 2.2 Create DiaryEntry entity with JPA annotations


    - Write DiaryEntry entity class with fields: id, title, content, entryDate, user, createdAt, updatedAt
    - Add JPA annotations including indexes for performance
    - Implement relationship with User (ManyToOne)
    - _Requirements: 2.1, 2.6, 4.4_
  
  - [x] 2.3 Create UserRepository interface


    - Extend JpaRepository for User entity
    - Add custom query method: findByEmail
    - Add custom query method: existsByEmail
    - _Requirements: 1.1, 1.4_
  
  - [x] 2.4 Create DiaryEntryRepository interface


    - Extend JpaRepository for DiaryEntry entity
    - Add custom query methods for search: searchEntries with keyword and date range parameters
    - Add pagination support using Pageable
    - Add method to find entries by user ID
    - _Requirements: 2.2, 3.1, 3.2, 3.3, 3.4_

- [x] 3. Implement JWT authentication and security configuration




  - [x] 3.1 Create JWT utility class


    - Implement JwtTokenProvider with methods: generateToken, validateToken, getUserIdFromToken
    - Configure JWT secret key and expiration time from environment variables
    - _Requirements: 1.2, 1.6, 1.7_
  
  - [x] 3.2 Create authentication DTOs


    - Write RegisterRequest DTO with email, password, fullName fields
    - Write LoginRequest DTO with email, password fields
    - Write AuthResponse DTO with token, tokenType, expiresIn, user fields
    - Add validation annotations to DTOs
    - _Requirements: 1.1, 1.2, 6.2_
  
  - [x] 3.3 Implement UserDetailsService


    - Create UserDetailsServiceImpl implementing UserDetailsService
    - Override loadUserByUsername to fetch user from UserRepository
    - Map User entity to Spring Security UserDetails
    - _Requirements: 1.2, 4.3_
  
  - [x] 3.4 Create JWT authentication filter


    - Implement JwtAuthenticationFilter extending OncePerRequestFilter
    - Extract JWT token from Authorization header
    - Validate token and set authentication in SecurityContext
    - _Requirements: 1.7, 4.3_
  
  - [x] 3.5 Configure Spring Security


    - Create SecurityConfig class with @Configuration and @EnableWebSecurity
    - Configure BCrypt password encoder with strength 12
    - Set up security filter chain with JWT filter
    - Configure stateless session management
    - Define public endpoints (/api/auth/**) and protected endpoints
    - _Requirements: 1.1, 1.7, 4.3_






- [ ] 4. Implement authentication service and controller
  - [ ] 4.1 Create AuthService
    - Implement register method: validate email uniqueness, hash password with BCrypt, save user


    - Implement login method: authenticate credentials, generate JWT token
    - Handle authentication errors without revealing specific failure reasons
    - _Requirements: 1.1, 1.2, 1.4, 1.5_
  
  - [ ] 4.2 Create AuthController
    - Implement POST /api/auth/register endpoint
    - Implement POST /api/auth/login endpoint
    - Add request validation and error handling
    - Return appropriate HTTP status codes
    - _Requirements: 1.1, 1.2, 4.2, 4.5_
  





  - [ ]* 4.3 Write authentication tests
    - Create AuthServiceTest with unit tests for register and login methods
    - Create AuthControllerTest with MockMvc tests for registration and login endpoints
    - Test duplicate email registration scenario
    - Test invalid credentials scenario


    - Test JWT token generation and validation
    - _Requirements: 7.1, 7.2, 7.4_

- [ ] 5. Implement diary entry service layer
  - [ ] 5.1 Create DiaryEntry DTOs
    - Write DiaryEntryDto with all fields for responses
    - Write CreateDiaryEntryRequest with title, content, entryDate
    - Write UpdateDiaryEntryRequest with optional title, content, entryDate
    - Add validation annotations
    - _Requirements: 2.6, 6.2_
  
  - [x] 5.2 Implement DiaryEntryService







    - Implement createEntry method: validate input, associate with authenticated user, save entry
    - Implement getEntriesByUser method: fetch paginated entries for authenticated user only
    - Implement getEntryById method: fetch entry and verify ownership
    - Implement updateEntry method: verify ownership, update fields, save
    - Implement deleteEntry method: verify ownership, delete entry
    - Add authorization checks to prevent cross-user access
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6_
  
  - [ ]* 5.3 Write diary service tests
    - Create DiaryEntryServiceTest with mocked repository
    - Test all CRUD operations
    - Test authorization checks (verify 403 for unauthorized access)
    - Test validation scenarios
    - _Requirements: 7.1, 7.5, 7.6_

- [ ] 6. Implement diary entry controller
  - [ ] 6.1 Create DiaryEntryController
    - Implement POST /api/diary-entries endpoint for creating entries
    - Implement GET /api/diary-entries endpoint for listing user's entries with pagination
    - Implement GET /api/diary-entries/{id} endpoint for fetching single entry
    - Implement PUT /api/diary-entries/{id} endpoint for updating entry
    - Implement DELETE /api/diary-entries/{id} endpoint for deleting entry
    - Extract authenticated user from SecurityContext
    - Add request validation and error handling
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 4.2, 4.5_
  
  - [ ]* 6.2 Write diary controller tests
    - Create DiaryEntryControllerTest with MockMvc
    - Test all CRUD endpoints with @WithMockUser
    - Test unauthorized access scenarios (401 without token, 403 for wrong user)
    - Test validation error responses
    - _Requirements: 7.2, 7.5, 7.6_

- [ ] 7. Implement search and filter functionality
  - [ ] 7.1 Enhance DiaryEntryRepository with search methods
    - Implement custom query for keyword search in title and content
    - Implement query for date range filtering
    - Implement query for specific date filtering
    - Combine keyword and date filters in single query
    - Ensure all queries filter by user ID for data isolation
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.6_
  
  - [ ] 7.2 Create SearchService
    - Implement search method accepting keyword, startDate, endDate, date parameters
    - Handle optional parameters (null checks)
    - Return paginated results
    - _Requirements: 3.1, 3.2, 3.3, 3.4_
  
  - [ ] 7.3 Add search endpoint to DiaryEntryController
    - Implement GET /api/diary-entries/search endpoint
    - Accept query parameters: keyword, startDate, endDate, date, page, size
    - Return paginated search results
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 4.2_
  
  - [ ]* 7.4 Write search functionality tests
    - Create SearchServiceTest with test cases for keyword search
    - Test date range filtering
    - Test combined keyword and date filtering
    - Test empty results scenario
    - Test user data isolation in search
    - _Requirements: 7.1, 7.7_

- [ ] 8. Implement global exception handling
  - [ ] 8.1 Create custom exception classes
    - Create ResourceNotFoundException for 404 scenarios
    - Create UnauthorizedException for 401 scenarios
    - Create ForbiddenException for 403 scenarios
    - _Requirements: 4.6, 6.2, 6.3, 6.6_
  
  - [ ] 8.2 Create GlobalExceptionHandler
    - Implement @RestControllerAdvice class
    - Handle ResourceNotFoundException returning 404 with error response
    - Handle UnauthorizedException returning 401 with error response
    - Handle ForbiddenException returning 403 with error response
    - Handle MethodArgumentNotValidException returning 400 with validation errors
    - Handle generic exceptions returning 500 with generic message
    - Create ErrorResponse DTO with error code, message, timestamp, path
    - _Requirements: 4.6, 6.2, 6.3, 6.4, 6.6_

- [ ] 9. Configure JaCoCo for code coverage
  - Add JaCoCo plugin to pom.xml with 60% coverage threshold
  - Configure JaCoCo to generate reports in target/site/jacoco
  - Exclude configuration and DTO classes from coverage calculation
  - _Requirements: 7.3_

- [ ] 10. Initialize frontend Next.js project
  - [ ] 10.1 Update package.json with required dependencies
    - Add dependencies: zustand, @tanstack/react-query, axios, zod, react-hook-form, @hookform/resolvers
    - Add dependencies: framer-motion, gsap
    - Add dev dependencies for TypeScript types
    - _Requirements: 5.1, 5.3, 5.4, 5.5, 5.6_
  
  - [ ] 10.2 Initialize shadcn/ui
    - Run shadcn/ui init command to set up components
    - Configure components.json for Tailwind CSS integration
    - Install initial shadcn/ui components: button, input, textarea, label, card, form, toast
    - _Requirements: 5.2_
  
  - [ ] 10.3 Set up project structure
    - Create directory structure: app/(auth), app/(dashboard), components/ui, components/features, lib, hooks, stores, types
    - Create environment variables file template (.env.example) with NEXT_PUBLIC_API_URL
    - _Requirements: 5.1_

- [ ] 11. Implement API client and types
  - [ ] 11.1 Create TypeScript type definitions
    - Define User interface
    - Define DiaryEntry interface
    - Define CreateDiaryEntryDto, UpdateDiaryEntryDto interfaces
    - Define SearchFilters, PaginatedResponse interfaces
    - Define AuthResponse, RegisterRequest, LoginRequest interfaces
    - _Requirements: 5.1_
  
  - [ ] 11.2 Create Axios API client
    - Create axios instance with base URL from environment variable
    - Implement request interceptor to add JWT token to Authorization header
    - Implement response interceptor to handle 401 errors and trigger logout
    - _Requirements: 5.4, 5.7_
  
  - [ ] 11.3 Create API service functions
    - Implement auth API functions: register, login
    - Implement diary API functions: fetchDiaryEntries, fetchDiaryEntry, createDiaryEntry, updateDiaryEntry, deleteDiaryEntry, searchDiaryEntries
    - Add proper TypeScript typing for all functions
    - _Requirements: 5.4_

- [ ] 12. Implement authentication state management
  - [ ] 12.1 Create Zustand auth store
    - Define AuthState interface with user, token, isAuthenticated fields
    - Implement login action: call API, store token and user, persist to localStorage
    - Implement register action: call API, store token and user
    - Implement logout action: clear token and user, remove from localStorage
    - Implement checkAuth action: restore auth state from localStorage on app load
    - _Requirements: 5.3_
  
  - [ ] 12.2 Create TanStack Query provider
    - Set up QueryClientProvider in root layout
    - Configure default query options (staleTime, cacheTime, retry)
    - _Requirements: 5.4_

- [ ] 13. Implement authentication UI components
  - [ ] 13.1 Create login page
    - Create app/(auth)/login/page.tsx
    - Implement LoginForm component with email and password fields using shadcn/ui
    - Add form validation using react-hook-form and Zod schema
    - Integrate with auth store login action
    - Display validation errors inline
    - Add loading state during authentication
    - Redirect to dashboard on successful login
    - _Requirements: 5.1, 5.6, 5.7, 6.1_
  
  - [ ] 13.2 Create registration page
    - Create app/(auth)/register/page.tsx
    - Implement RegisterForm component with email, password, fullName fields
    - Add form validation with password strength requirements
    - Integrate with auth store register action
    - Display validation errors inline
    - Add loading state during registration
    - Redirect to dashboard on successful registration
    - _Requirements: 5.1, 5.6, 5.7, 6.1, 6.5_
  
  - [ ] 13.3 Create AuthGuard component
    - Implement HOC or wrapper to protect routes
    - Check authentication state from Zustand store
    - Redirect to login if not authenticated
    - Show loading state while checking auth
    - _Requirements: 5.1_

- [ ] 14. Implement diary list and dashboard
  - [ ] 14.1 Create TanStack Query hooks for diary operations
    - Create useDiaryEntries hook with pagination and filters
    - Create useDiaryEntry hook for single entry
    - Create useCreateDiaryEntry mutation hook with cache invalidation
    - Create useUpdateDiaryEntry mutation hook with cache invalidation
    - Create useDeleteDiaryEntry mutation hook with cache invalidation
    - _Requirements: 5.4_
  
  - [ ] 14.2 Create dashboard page
    - Create app/(dashboard)/dashboard/page.tsx wrapped with AuthGuard
    - Implement DiaryList component displaying paginated entries
    - Use useDiaryEntries hook to fetch data
    - Display loading skeleton while fetching
    - Display empty state when no entries exist
    - Add pagination controls
    - _Requirements: 5.1, 5.8, 6.3_
  
  - [ ] 14.3 Create DiaryCard component
    - Display entry title, preview of content, and date
    - Add edit and delete action buttons
    - Implement delete confirmation dialog using shadcn/ui Dialog
    - Add smooth animations on hover using Framer Motion
    - _Requirements: 5.2, 5.5_

- [ ] 15. Implement diary entry creation and editing
  - [ ] 15.1 Create diary form component
    - Implement DiaryForm component with title, content, entryDate fields
    - Use shadcn/ui form components (Input, Textarea, Calendar)
    - Add form validation using react-hook-form and Zod
    - Support both create and edit modes
    - Display validation errors inline
    - Add loading state during submission
    - _Requirements: 5.2, 5.6, 6.1_
  
  - [ ] 15.2 Create new diary entry page
    - Create app/(dashboard)/diary/new/page.tsx
    - Use DiaryForm component in create mode
    - Integrate with useCreateDiaryEntry mutation
    - Show success toast notification on creation
    - Redirect to dashboard after successful creation
    - _Requirements: 5.1, 5.7_
  
  - [ ] 15.3 Create edit diary entry page
    - Create app/(dashboard)/diary/[id]/page.tsx
    - Fetch existing entry using useDiaryEntry hook
    - Use DiaryForm component in edit mode with pre-filled data
    - Integrate with useUpdateDiaryEntry mutation
    - Show success toast notification on update
    - Handle 404 if entry not found
    - _Requirements: 5.1, 5.7, 6.3_

- [ ] 16. Implement search and filter UI
  - [ ] 16.1 Create SearchBar component
    - Implement search input with debounced onChange handler
    - Add date range picker using shadcn/ui Calendar
    - Add filter controls for specific date selection
    - Emit search and filter changes to parent component
    - _Requirements: 5.2_
  
  - [ ] 16.2 Integrate search into dashboard
    - Add SearchBar component to dashboard page
    - Update useDiaryEntries hook call with search filters
    - Display search results with same DiaryList component
    - Show empty state with appropriate message when no results found
    - Add clear filters button
    - _Requirements: 5.1, 6.3_

- [ ] 17. Implement error handling and loading states
  - [ ] 17.1 Create error boundary component
    - Implement ErrorBoundary component for React error catching
    - Display user-friendly error message
    - Add retry button
    - _Requirements: 5.7, 6.3_
  
  - [ ] 17.2 Create reusable UI components
    - Create LoadingSpinner component
    - Create EmptyState component with customizable message and icon
    - Create ErrorState component with retry functionality
    - _Requirements: 5.7, 6.3_
  
  - [ ] 17.3 Add toast notifications
    - Configure shadcn/ui Toast component
    - Add toast notifications for successful operations (create, update, delete)
    - Add toast notifications for errors
    - _Requirements: 5.7, 6.3_

- [ ] 18. Add animations and micro-interactions
  - [ ] 18.1 Implement page transitions
    - Create AnimatedPage wrapper using Framer Motion
    - Add fade-in animations for page loads
    - Add slide transitions between pages
    - _Requirements: 5.5_
  
  - [ ] 18.2 Add component animations
    - Add hover animations to DiaryCard using Framer Motion
    - Add button press animations
    - Add smooth transitions for form validation errors
    - Implement skeleton loaders with shimmer effect
    - _Requirements: 5.5_

- [ ] 19. Create Docker configuration
  - [ ] 19.1 Create backend Dockerfile
    - Implement multi-stage Dockerfile for Spring Boot application
    - Stage 1: Build with Maven using eclipse-temurin:21-jdk-alpine
    - Stage 2: Runtime with eclipse-temurin:21-jre-alpine
    - Copy JAR file and set ENTRYPOINT
    - Expose port 8080
    - _Requirements: 8.1_
  
  - [ ] 19.2 Create frontend Dockerfile
    - Implement multi-stage Dockerfile for Next.js application
    - Stage 1: Install dependencies
    - Stage 2: Build application with standalone output
    - Stage 3: Runtime with minimal Node.js image
    - Copy built files and set CMD
    - Expose port 3000
    - Configure Next.js for standalone output in next.config.ts
    - _Requirements: 8.2_
  
  - [ ] 19.3 Create Docker Compose file
    - Define postgres service with PostgreSQL 17-alpine image
    - Configure database environment variables and volume
    - Add health check for postgres service
    - Define backend service with build context and dependencies
    - Configure backend environment variables (database URL, JWT secret)
    - Define frontend service with build context and dependencies
    - Configure frontend environment variable (API URL)
    - Set up service dependencies and network
    - _Requirements: 8.3, 8.4, 8.5, 8.6_
  
  - [ ] 19.4 Create environment configuration files
    - Create .env.example file with all required environment variables
    - Document each environment variable
    - Add .env to .gitignore
    - _Requirements: 8.6_

- [ ] 20. Create GitHub Actions CI/CD workflow
  - [ ] 20.1 Create workflow file
    - Create .github/workflows/ci-cd.yml
    - Configure triggers: push to main/develop, pull requests
    - _Requirements: 9.1_
  
  - [ ] 20.2 Implement backend build and test job
    - Set up Java 21 environment
    - Cache Maven dependencies
    - Run Maven build (mvn clean package)
    - Run unit tests (mvn test)
    - Generate JaCoCo coverage report
    - Upload coverage report as artifact
    - Fail build if coverage below 60%
    - _Requirements: 9.2, 9.3, 9.4, 9.7_
  
  - [ ] 20.3 Implement frontend build job
    - Set up Node.js 20 environment
    - Cache npm dependencies
    - Run npm ci to install dependencies
    - Run ESLint for code quality check
    - Run npm build
    - _Requirements: 9.5, 9.6_
  
  - [ ] 20.4 Implement Docker build job
    - Add job dependency on backend and frontend jobs
    - Build backend Docker image
    - Build frontend Docker image
    - Optionally push images to container registry
    - _Requirements: 9.6, 9.8_

- [ ] 21. Create documentation and setup instructions
  - Update README.md with project overview, technology stack, and features
  - Add setup instructions for local development
  - Document Docker Compose usage
  - Add API documentation or link to Swagger/OpenAPI spec
  - Document environment variables
  - Add screenshots or demo GIF
  - _Requirements: All requirements_
