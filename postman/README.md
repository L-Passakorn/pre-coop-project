# Postman Collection - Simple Diaries API

This directory contains a complete Postman collection with automatic environment variable management for testing the Simple Diaries API.

## 📦 What's Included

- **Simple-Diaries-API.postman_collection.json** - Complete API collection with all endpoints
- **Simple-Diaries-Local.postman_environment.json** - Environment configuration for local development
- **README.md** - This file

## 🚀 Quick Setup

### Step 1: Import Collection

1. Open Postman
2. Click **Import** button (top left)
3. Drag and drop `Simple-Diaries-API.postman_collection.json` or click "Upload Files"
4. Click **Import**

### Step 2: Import Environment

1. Click **Import** button again
2. Drag and drop `Simple-Diaries-Local.postman_environment.json`
3. Click **Import**

### Step 3: Select Environment

1. Look at the top-right corner of Postman
2. Click the environment dropdown (shows "No Environment" by default)
3. Select **"Simple Diaries - Local"**

## ✨ Features

### Automatic Token Management

The collection automatically handles JWT tokens:

- **Register/Login** requests automatically save the token to environment variables
- **All diary entry requests** automatically use the saved token
- No need to manually copy/paste tokens!

### Automatic Entry ID Tracking

When you create a diary entry, the ID is automatically saved and used in:
- Get Entry by ID
- Update Entry
- Delete Entry

### Console Logging

All requests include helpful console messages:
- ✅ Success messages with details
- ❌ Error notifications
- 📊 Pagination information

## 📋 Collection Structure

### 1. Authentication
- **Register User** - Create a new account (auto-saves token)
- **Login** - Login with existing account (auto-saves token)

### 2. Diary Entries
- **Create Diary Entry** - Create a new entry (auto-saves entry ID)
- **Get All Diary Entries** - List entries with pagination
- **Get Diary Entry by ID** - Get specific entry (uses saved ID)
- **Update Diary Entry** - Update entry (uses saved ID)
- **Delete Diary Entry** - Delete entry (uses saved ID)

### 3. Test Scenarios
- **Create Multiple Entries** - Pre-configured requests with different moods
  - Happy entry
  - Grateful entry
  - Peaceful entry

## 🎯 Testing Workflow

### First Time Setup

1. **Start the application** (see QUICK_START.md in root directory)
2. **Register a new user:**
   - Open "Authentication" → "Register User"
   - Click **Send**
   - Token is automatically saved! ✅

3. **Create some diary entries:**
   - Open "Diary Entries" → "Create Diary Entry"
   - Click **Send**
   - Entry ID is automatically saved! ✅

4. **Test other endpoints:**
   - All requests will automatically use your token
   - Get, Update, and Delete will use the last created entry ID

### Quick Test All Features

Run requests in this order:

1. **Register User** (or Login if already registered)
2. **Create Diary Entry** (creates entry with ID 1)
3. **Get All Diary Entries** (see your entry in the list)
4. **Get Diary Entry by ID** (retrieves the entry you just created)
5. **Update Diary Entry** (modifies the entry)
6. **Get Diary Entry by ID** (verify the update)
7. **Delete Diary Entry** (removes the entry)
8. **Get All Diary Entries** (verify it's deleted)

### Create Multiple Test Entries

Use the "Test Scenarios" folder to quickly create multiple entries:

1. Run "Create Multiple Entries (Happy)"
2. Run "Create Multiple Entries (Grateful)"
3. Run "Create Multiple Entries (Peaceful)"
4. Run "Get All Diary Entries" to see all entries

## 🔧 Environment Variables

The environment automatically manages these variables:

| Variable | Description | Auto-Set |
|----------|-------------|----------|
| `baseUrl` | API base URL (http://localhost:8080) | Manual |
| `token` | JWT authentication token | ✅ Auto |
| `username` | Current user's username | ✅ Auto |
| `email` | Current user's email | ✅ Auto |
| `lastEntryId` | ID of last created entry | ✅ Auto |

### Viewing Environment Variables

1. Click the **eye icon** (👁️) next to the environment dropdown
2. See current values of all variables
3. The `token` is marked as "secret" for security

### Manually Setting Variables

If needed, you can manually edit variables:

1. Click the **eye icon** (👁️)
2. Click **Edit** next to "Simple Diaries - Local"
3. Modify values
4. Click **Save**

## 📝 Request Examples

### Available Moods

When creating/updating entries, use these mood values:
- `HAPPY`
- `SAD`
- `NEUTRAL`
- `EXCITED`
- `ANXIOUS`
- `GRATEFUL`
- `ANGRY`
- `PEACEFUL`

### Sample Request Bodies

**Create Entry:**
```json
{
  "title": "My Day",
  "content": "Today was interesting...",
  "mood": "HAPPY",
  "tags": ["personal", "reflection"]
}
```

**Update Entry:**
```json
{
  "title": "My Day - Updated",
  "content": "Today was interesting... Update: It got even better!",
  "mood": "EXCITED",
  "tags": ["personal", "reflection", "growth"]
}
```

## 🐛 Troubleshooting

### Token Not Working

**Problem:** Getting 401 Unauthorized errors

**Solutions:**
1. Check if token is set: Click eye icon (👁️) and verify `token` has a value
2. Re-login: Run the "Login" request again
3. Check token expiration: Tokens expire after 24 hours by default
4. Verify environment is selected: Check top-right dropdown shows "Simple Diaries - Local"

### Entry ID Not Found

**Problem:** Getting 404 Not Found when accessing entry

**Solutions:**
1. Create an entry first: Run "Create Diary Entry"
2. Check `lastEntryId`: Click eye icon (👁️) and verify it has a value
3. Use correct ID: Manually set the ID in the URL if needed

### Connection Refused

**Problem:** Cannot connect to server

**Solutions:**
1. Start the application: `cd backend && ./mvnw.cmd spring-boot:run`
2. Check if running: Visit http://localhost:8080 in browser
3. Verify port: Make sure port 8080 is not in use by another application
4. Check database: Ensure PostgreSQL is running (`docker-compose up -d`)

### Environment Not Saving Variables

**Problem:** Variables reset after requests

**Solutions:**
1. Make sure environment is selected (top-right dropdown)
2. Check if environment is saved (no asterisk * next to name)
3. Re-import environment file if needed

## 💡 Tips & Tricks

### Running Multiple Requests

Use Postman's **Collection Runner**:
1. Click on collection name
2. Click **Run** button
3. Select requests to run
4. Click **Run Simple Diaries API**

### Viewing Console Output

1. Open Postman Console: View → Show Postman Console (or Ctrl+Alt+C)
2. See detailed logs for each request
3. View automatic messages from test scripts

### Testing Pagination

Modify query parameters in "Get All Diary Entries":
- `page=0&size=5` - First page, 5 items
- `page=1&size=5` - Second page, 5 items
- `page=0&size=20` - First page, 20 items

### Testing with Multiple Users

1. Register first user → Token saved
2. Create some entries
3. Click eye icon → Copy token value
4. Register second user → New token saved
5. Try to access first user's entries (should fail with 403)
6. Manually set first user's token back to test authorization

### Saving Responses as Examples

1. Send a request
2. Click **Save Response** → **Save as Example**
3. Examples appear under each request for reference

## 🔐 Security Notes

- The `token` variable is marked as "secret" in the environment
- Tokens are not visible in plain text in the environment viewer
- Never commit environment files with real tokens to version control
- Tokens expire after 24 hours by default

## 📚 Additional Resources

- **POSTMAN_TESTING_GUIDE.md** - Detailed testing guide with manual examples
- **QUICK_START.md** - How to start the application
- **API Documentation** - See controller files in `backend/src/main/java/com/diaries/controller/`

## 🆘 Need Help?

If you encounter issues:
1. Check the Postman Console for detailed error messages
2. Verify the application is running and accessible
3. Review the POSTMAN_TESTING_GUIDE.md for detailed endpoint documentation
4. Check application logs in the terminal where Spring Boot is running

---

Happy Testing! 🎉
