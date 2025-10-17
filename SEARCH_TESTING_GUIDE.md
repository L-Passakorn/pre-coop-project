# Search & Filter Testing Guide

## Search Endpoint

The search endpoint allows you to filter diary entries by keyword, date range, or specific date.

**Endpoint:** `GET /api/diary-entries/search`

**Authentication:** Required (Bearer token)

---

## Test Scenarios

### 1. Search by Keyword

Search for entries containing a keyword in title or content.

**Request:**
```
GET http://localhost:8080/api/diary-entries/search?keyword=project
Authorization: Bearer YOUR_TOKEN
```

**Query Parameters:**
- `keyword` - Search term (searches in title and content)
- `page` - Page number (default: 0)
- `size` - Page size (default: 10)

**Example Response:**
```json
{
  "content": [
    {
      "id": 1,
      "title": "My First Day",
      "content": "Today was an amazing day! I started working on a new project...",
      "entryDate": "2024-10-17",
      "userId": 1,
      "createdAt": "2024-10-17T10:30:00",
      "updatedAt": "2024-10-17T10:30:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```

---

### 2. Search by Date Range

Find entries within a specific date range.

**Request:**
```
GET http://localhost:8080/api/diary-entries/search?startDate=2024-10-01&endDate=2024-10-31
Authorization: Bearer YOUR_TOKEN
```

**Query Parameters:**
- `startDate` - Start date (format: YYYY-MM-DD)
- `endDate` - End date (format: YYYY-MM-DD)
- `page` - Page number (default: 0)
- `size` - Page size (default: 10)

---

### 3. Search by Specific Date

Find entries for a specific date.

**Request:**
```
GET http://localhost:8080/api/diary-entries/search?date=2024-10-17
Authorization: Bearer YOUR_TOKEN
```

**Query Parameters:**
- `date` - Specific date (format: YYYY-MM-DD)
- `page` - Page number (default: 0)
- `size` - Page size (default: 10)

---

### 4. Combined Search (Keyword + Date Range)

Search for keyword within a date range.

**Request:**
```
GET http://localhost:8080/api/diary-entries/search?keyword=work&startDate=2024-10-01&endDate=2024-10-31
Authorization: Bearer YOUR_TOKEN
```

**Query Parameters:**
- `keyword` - Search term
- `startDate` - Start date (format: YYYY-MM-DD)
- `endDate` - End date (format: YYYY-MM-DD)
- `page` - Page number (default: 0)
- `size` - Page size (default: 10)

---

### 5. No Filters (Get All)

If no filters are provided, returns all entries (same as GET /api/diary-entries).

**Request:**
```
GET http://localhost:8080/api/diary-entries/search
Authorization: Bearer YOUR_TOKEN
```

---

## Testing in Postman

### Setup

1. Make sure you're logged in and have a valid token
2. Create some test entries with different dates and content

### Test Data Setup

Create these entries for comprehensive testing:

**Entry 1:**
```json
{
  "title": "Work Project Started",
  "content": "Started working on the new authentication project today.",
  "entryDate": "2024-10-15"
}
```

**Entry 2:**
```json
{
  "title": "Weekend Relaxation",
  "content": "Spent the weekend reading and relaxing at home.",
  "entryDate": "2024-10-16"
}
```

**Entry 3:**
```json
{
  "title": "Project Progress",
  "content": "Made great progress on the project. Implemented search functionality.",
  "entryDate": "2024-10-17"
}
```

**Entry 4:**
```json
{
  "title": "Team Meeting",
  "content": "Had a productive team meeting to discuss project milestones.",
  "entryDate": "2024-10-18"
}
```

---

## Test Cases

### Test 1: Keyword Search
```
GET /api/diary-entries/search?keyword=project
Expected: Returns entries 1, 3, and 4 (contain "project")
```

### Test 2: Date Range
```
GET /api/diary-entries/search?startDate=2024-10-16&endDate=2024-10-17
Expected: Returns entries 2 and 3
```

### Test 3: Specific Date
```
GET /api/diary-entries/search?date=2024-10-17
Expected: Returns only entry 3
```

### Test 4: Keyword + Date Range
```
GET /api/diary-entries/search?keyword=project&startDate=2024-10-15&endDate=2024-10-17
Expected: Returns entries 1 and 3
```

### Test 5: Case Insensitive Search
```
GET /api/diary-entries/search?keyword=PROJECT
Expected: Returns entries 1, 3, and 4 (search is case-insensitive)
```

### Test 6: Partial Match
```
GET /api/diary-entries/search?keyword=relax
Expected: Returns entry 2 (contains "relaxing")
```

### Test 7: No Results
```
GET /api/diary-entries/search?keyword=vacation
Expected: Returns empty array if no entries contain "vacation"
```

### Test 8: Pagination
```
GET /api/diary-entries/search?keyword=project&page=0&size=2
Expected: Returns first 2 matching entries
```

---

## Expected Behaviors

### Search Logic

1. **Keyword Search:**
   - Searches in both title and content
   - Case-insensitive
   - Partial matches included

2. **Date Filters:**
   - `date` parameter takes priority over date range
   - Date range is inclusive (includes both start and end dates)
   - Dates must be in YYYY-MM-DD format

3. **User Isolation:**
   - Only returns entries belonging to the authenticated user
   - Cannot see other users' entries even if they match filters

4. **Pagination:**
   - Default page size: 10
   - Page numbers start at 0
   - Returns total count and page information

---

## Error Cases

### Invalid Date Format
```
GET /api/diary-entries/search?date=10/17/2024
Expected: 400 Bad Request
```

### Missing Token
```
GET /api/diary-entries/search?keyword=test
(No Authorization header)
Expected: 401 Unauthorized
```

### Invalid Token
```
GET /api/diary-entries/search?keyword=test
Authorization: Bearer invalid_token
Expected: 403 Forbidden
```

---

## Postman Collection Update

To add search to your Postman collection:

1. **Create New Request:**
   - Name: "Search Diary Entries"
   - Method: GET
   - URL: `{{baseUrl}}/api/diary-entries/search`

2. **Add Authorization:**
   - Type: Bearer Token
   - Token: `{{token}}`

3. **Add Query Parameters:**
   - `keyword` (optional)
   - `startDate` (optional)
   - `endDate` (optional)
   - `date` (optional)
   - `page` (default: 0)
   - `size` (default: 10)

4. **Add Test Script:**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    console.log('✅ Search returned ' + jsonData.totalElements + ' results');
    console.log('Page: ' + (jsonData.number + 1) + ' of ' + jsonData.totalPages);
}
```

---

## Quick Test Workflow

1. **Start the application**
2. **Login** to get a token
3. **Create 4 test entries** (use the test data above)
4. **Run each test case** and verify results
5. **Check pagination** with different page sizes
6. **Test edge cases** (empty results, invalid dates, etc.)

---

## Success Indicators

✅ Keyword search returns matching entries
✅ Date range filtering works correctly
✅ Specific date search returns only that date's entries
✅ Combined filters work together
✅ Search is case-insensitive
✅ Pagination works correctly
✅ User can only see their own entries
✅ Empty results return empty array (not error)

---

**Ready to test!** Start the application and try the search endpoint! 🔍
