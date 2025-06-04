# Attendance Management System Prototype

This repository contains a minimal prototype implementing parts of the checklist described in the project overview. The backend is a Spring Boot application with H2 database that exposes REST endpoints for managing organizations, subscribers and attendance sessions.

## Backend

* Java 17 with Spring Boot
* In-memory H2 database
* Gradle build

### Running

```bash
cd backend
./gradlew bootRun
```

If the Gradle wrapper JAR is missing, regenerate it by running `gradle wrapper`.

This will start the backend on `http://localhost:8080`.

### Key Endpoints

* `POST /admin/entities` – create organization
* `POST /admin/entities/{id}/admins` – create admin under organization
* `POST /entities/{entityId}/subscribers` – add subscriber
* `POST /entities/{entityId}/sessions` – create attendance session
* `POST /attendance/log?sessionId={sessionId}&cardUid={uid}` – record attendance by card UID
* `GET /entities/{entityId}/sessions/{sessionId}/absentees` – list subscribers who have not attended

## Frontend

A React application skeleton is available in the `frontend` folder. After installing dependencies with `npm install`, it can be started using `npm start`.
All public images are replaced with online placeholders to keep the repository free of binary assets.
