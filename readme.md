# Course Management API (Java- SpringBoot-Redis Application)

This is a Spring Boot application that provides two APIs to manage course data. The application stores course information in a Redis cache to improve performance and reduce the load on the backend API. The two primary endpoints are:

1. **`/course/{courseId}`**: To fetch a specific course by its ID.
2. **`/courses`**: To fetch all courses available.

## Features
- **Spring Boot** framework used for the backend application.
- **Redis** used for caching course data to reduce response time and backend load.
- **Jackson JSON** for serializing and deserializing objects in Redis.
- **Error handling**: Proper logging and error management.
  
## Prerequisites
- **Java 11 or later** (This application is built using Java 11).
- **Spring Boot 2.x** (Spring Boot version that integrates with Redis).
- **Redis** should be installed and running on your local machine or a cloud instance.

### Dependencies
- **Spring Boot Web**: To handle web requests and APIs.
- **Spring Data Redis**: To interact with Redis for caching.
- **Lettuce** or **Jedis**: Redis client libraries used to connect to Redis.
- **Jackson**: For JSON serialization and deserialization.

## API Endpoints

### 1. `GET /course/{courseId}`
This endpoint retrieves a specific course by its ID.

#### Request
- **URL:** `/course/{courseId}`
- **Method:** `GET`
- **URL Params:** 
  - `courseId`: The unique ID of the course to retrieve.
  
#### Response
- **200 OK**: If the course is found, a JSON object representing the course is returned.
  
#### Example:
- **Request**: `GET /course/1`
- **Response**: 
  ```json
  {
    "userId": 1,
    "id": 1,
    "title": "Introduction to Artificial Intelligence",
    "body": "Learn the basics of Artificial Intelligence and its applications in various industries.",
    "link": "https://example.com/article1",
    "comment_count": 8
  }
### 2. `GET /courses`

This endpoint retrieves all available courses in the system. If the courses are available in the cache (Redis), they will be returned from there, improving the response time. If the courses are not in the cache, they will be fetched from the backend API, saved into the cache, and then returned.

#### Request

- **URL:** `/courses`
- **Method:** `GET`

#### Response

- **200 OK**: Returns a list of courses in JSON format.

#### Example Request:
#### Example Response:

```json
[
  {
    "userId": 1,
    "id": 1,
    "title": "Introduction to Artificial Intelligence",
    "body": "Learn the basics of Artificial Intelligence and its applications in various industries.",
    "link": "https://example.com/article1",
    "comment_count": 8
  },
  {
    "userId": 2,
    "id": 2,
    "title": "Web Development with React",
    "body": "Build modern web applications using React.js and explore its powerful features.",
    "link": "https://example.com/article2",
    "comment_count": 12
  }
]
