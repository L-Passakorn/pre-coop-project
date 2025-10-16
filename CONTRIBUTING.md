# Contributing to Simple Diaries

Thank you for your interest in contributing to Simple Diaries!

## Development Setup

### Prerequisites

- Java 21 or higher
- Node.js 18+ or Bun
- PostgreSQL 17.x (or use Docker Compose)
- Maven 3.8+ (or use the included wrapper)

### Getting Started

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd simple-diaries
   ```

2. **Set up environment variables**
   ```bash
   # Backend
   cp backend/.env.example backend/.env
   # Edit backend/.env with your configuration
   
   # Frontend
   cp frontend/.env.example frontend/.env
   # Edit frontend/.env with your configuration
   ```

3. **Start PostgreSQL**
   ```bash
   # Using Docker Compose
   docker-compose up postgres -d
   
   # Or install PostgreSQL locally
   ```

4. **Run the backend**
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

5. **Run the frontend**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

## Project Structure

```
.
├── backend/          # Spring Boot backend
├── frontend/         # Next.js frontend
├── .kiro/specs/     # Feature specifications
└── docker-compose.yml
```

## Development Workflow

1. Check the `.kiro/specs/simple-diaries-app/` directory for requirements and design
2. Review the `tasks.md` file for implementation tasks
3. Create a feature branch: `git checkout -b feature/your-feature-name`
4. Make your changes
5. Test your changes
6. Commit with descriptive messages
7. Push and create a pull request

## Code Style

### Backend (Java)
- Follow Java naming conventions
- Use Spring Boot best practices
- Write unit tests for services
- Document public APIs with JavaDoc

### Frontend (TypeScript/React)
- Follow TypeScript best practices
- Use functional components with hooks
- Write meaningful component names
- Keep components small and focused

## Testing

### Backend
```bash
cd backend
./mvnw test
./mvnw jacoco:report  # Generate coverage report
```

### Frontend
```bash
cd frontend
npm test
```

## Commit Messages

Follow conventional commits format:
- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation changes
- `style:` Code style changes
- `refactor:` Code refactoring
- `test:` Test changes
- `chore:` Build/tooling changes

Example: `feat: add user authentication endpoint`

## Questions?

Feel free to open an issue for any questions or concerns.
