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
- PostgreSQL 17.x

### Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend will run on `http://localhost:8080`

See [backend/README.md](backend/README.md) for more details.

### Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend will run on `http://localhost:3000`

See [frontend/README.md](frontend/README.md) for more details.

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
