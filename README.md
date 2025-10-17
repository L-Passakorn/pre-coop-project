# Simple Diaries Application

A full-stack diary application with user authentication and diary entry management.

## Project Structure

```
.
├── backend/          # Spring Boot backend (Java 21)
├── frontend/         # Next.js frontend (React 19)
└── .kiro/           # Kiro specs and configuration
    └── specs/
        └── simple-diaries-app/
```

## Technology Stack

### Backend
- Spring Boot 3.4.x
- Java 21
- PostgreSQL 17.x
- Spring Security with JWT
- JUnit 5 & MockMvc
- JaCoCo (code coverage)

### Frontend
- Next.js 15.x
- React 19.x
- TypeScript 5.x
- Tailwind CSS 4.x

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.8+
- Node.js 18+ or Bun
- Docker & Docker Compose
- PostgreSQL 17.x (via Docker)

### Quick Start

See [QUICK_START.md](QUICK_START.md) for detailed setup instructions.

**TL;DR:**
```bash
# Start database
docker-compose up -d

# Start backend
cd backend
./mvnw spring-boot:run

# Start frontend (in another terminal)
cd frontend
npm install
npm run dev
```

- Backend: `http://localhost:8080`
- Frontend: `http://localhost:3000`

### API Testing with Postman

Import the Postman collection for easy API testing:

1. Open Postman
2. Import `postman/Simple-Diaries-API.postman_collection.json`
3. Import `postman/Simple-Diaries-Local.postman_environment.json`
4. Select "Simple Diaries - Local" environment
5. Start testing!

See [postman/README.md](postman/README.md) for detailed usage guide.

**Features:**
- ✅ Automatic JWT token management
- ✅ All CRUD operations for diary entries
- ✅ Pre-configured test scenarios

## Features

- User authentication (register/login)
- Create, read, update, and delete diary entries
- Secure JWT-based authentication
- Responsive UI design
- RESTful API

## Development

This project follows a spec-driven development approach. See `.kiro/specs/simple-diaries-app/` for:
- Requirements document
- Design document
- Implementation tasks

## License

MIT
