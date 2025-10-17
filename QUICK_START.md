# Quick Start Guide - Simple Diaries API

## Step 1: Start PostgreSQL Database

Make sure Docker is running, then start the database:

```bash
docker-compose up -d
```

This will start PostgreSQL on `localhost:5432` with:
- Database: `diaries_db`
- Username: `diaries_user`
- Password: `changeme`

## Step 2: Start the Spring Boot Application

Open a terminal in the `backend` directory and run:

```bash
cd backend
./mvnw.cmd spring-boot:run
```

Or if you prefer to set JAVA_HOME explicitly:

```bash
cd backend
$env:JAVA_HOME="C:\Program Files\Java\jdk-25"; ./mvnw.cmd spring-boot:run
```

Wait for the application to start. You should see:
```
Started SimpleDiariesApplication in X.XXX seconds
```

## Step 3: Test with Postman

The API is now running at `http://localhost:8080`

Follow the **POSTMAN_TESTING_GUIDE.md** for detailed testing instructions.

### Quick Test Sequence:

1. **Register a user:**
   - POST `http://localhost:8080/api/auth/register`
   - Body: `{"username": "testuser", "email": "test@example.com", "password": "Pass123!"}`

2. **Copy the JWT token** from the response

3. **Create a diary entry:**
   - POST `http://localhost:8080/api/diary-entries`
   - Header: `Authorization: Bearer YOUR_TOKEN`
   - Body: `{"title": "Test", "content": "My first entry", "mood": "HAPPY", "tags": ["test"]}`

4. **Get all entries:**
   - GET `http://localhost:8080/api/diary-entries`
   - Header: `Authorization: Bearer YOUR_TOKEN`

## Troubleshooting

### Database Connection Issues
- Verify Docker is running: `docker ps`
- Check if PostgreSQL container is up: `docker-compose ps`
- Restart database: `docker-compose restart`

### Application Won't Start
- Check if port 8080 is already in use
- Verify Java version: `java -version` (should be Java 17+)
- Check application logs for errors

### Authentication Issues
- Make sure you're including the JWT token in the Authorization header
- Token format: `Bearer YOUR_TOKEN_HERE`
- Tokens expire after 24 hours by default

## Stopping the Application

1. Stop Spring Boot: Press `Ctrl+C` in the terminal
2. Stop PostgreSQL: `docker-compose down`

## Environment Variables (Optional)

You can override default settings by creating a `.env` file in the backend directory:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/diaries_db
SPRING_DATASOURCE_USERNAME=diaries_user
SPRING_DATASOURCE_PASSWORD=changeme
JWT_SECRET=your-super-secret-key-at-least-256-bits-long
```
