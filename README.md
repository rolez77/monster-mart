# MonsterMart

A web application for Pokemon card collectors to upload their cards and discover other users' collections.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [1. Clone the Repository](#1-clone-the-repository)
  - [2. Start the Database](#2-start-the-database)
  - [3. Configure Environment Variables](#3-configure-environment-variables)
  - [4. Run the Backend](#4-run-the-backend)
  - [5. Run the Frontend](#5-run-the-frontend)
- [Project Structure](#project-structure)
- [API Overview](#api-overview)
- [Environment Variables Reference](#environment-variables-reference)

---

## Features

- User registration and login with JWT authentication
- Upload a profile picture
- Add Pokemon cards with name, set, condition, price, and card image
- View your personal card collection on your profile
- Browse other users and their cards

---

## Tech Stack

| Layer      | Technology                          |
|------------|-------------------------------------|
| Frontend   | React 19, Vite, Chakra UI           |
| Backend    | Spring Boot 4, Java 17              |
| Database   | PostgreSQL 15                       |
| Auth       | JWT (JJWT), BCrypt                  |
| File Store | AWS S3                              |
| Container  | Docker / Docker Compose             |

---

## Prerequisites

Make sure the following are installed before you begin:

- [Node.js](https://nodejs.org/) (v18+) and npm
- [Java 17](https://adoptium.net/)
- [Maven](https://maven.apache.org/) (or use the included `mvnw` wrapper)
- [Docker](https://www.docker.com/) and Docker Compose
- An AWS account with an S3 bucket (for card/profile image storage)

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/monster-mart-project.git
cd monster-mart-project
```

### 2. Start the Database

The project uses Docker Compose to run PostgreSQL locally.

```bash
docker compose up -d
```

This starts a PostgreSQL 15 container with:
- **Host port:** `5433`
- **Database:** `monstermart`
- **User:** `rolez`
- **Password:** `password`

Data is persisted in a Docker volume (`postgres_data`), so it survives container restarts.

To stop the database:

```bash
docker compose down
```

To stop and wipe all data:

```bash
docker compose down -v
```

### 3. Configure Environment Variables

#### Backend

The backend reads AWS credentials from environment variables. Set these in your shell before running the backend:

```bash
export AWS_ACCESS_KEY=your_aws_access_key_id
export AWS_SECRET_KEY=your_aws_secret_access_key
export S3_BUCKET_NAME=your_s3_bucket_name
```

The default database connection (matching the Docker Compose setup) is already configured in `backend/src/main/resources/application.properties`. If you change the Docker Compose credentials, update that file to match.

#### Frontend

The frontend reads the API base URL from a `.env` file. A default is already included:

```
# frontend/.env
VITE_API_BASE_URL=http://localhost:8080
```

No changes are needed unless you run the backend on a different port.

### 4. Run the Backend

```bash
cd backend
./mvnw spring-boot:run
```

On Windows:

```bash
cd backend
mvnw.cmd spring-boot:run
```

The backend will start on **http://localhost:8080**. On first run, Hibernate will automatically create the `users` and `cards` tables in the database.

### 5. Run the Frontend

Open a new terminal tab:

```bash
cd frontend
npm install
npm run dev
```

The frontend will start on **http://localhost:5173**.

Open your browser and navigate to **http://localhost:5173** to use the app.

---

## Project Structure

```
monster-mart-project/
├── docker-compose.yml              # PostgreSQL service
├── frontend/                       # React + Vite app
│   ├── src/
│   │   ├── components/
│   │   │   ├── Cards/              # Add card form
│   │   │   ├── context/            # Auth context (JWT)
│   │   │   ├── homepage/           # Dashboard
│   │   │   ├── login/              # Login page
│   │   │   ├── profile/            # User profile & card display
│   │   │   └── signup/             # Registration
│   │   ├── hooks/                  # useCardInfo, useUserProfile
│   │   ├── services/
│   │   │   └── client.js           # Axios instance with auth headers
│   │   └── App.jsx                 # Route definitions
│   └── package.json
└── backend/                        # Spring Boot app
    └── src/main/java/com/rolez/backend/
        ├── auth/                   # Login endpoint & JWT response
        ├── jwt/                    # JWT generation & filter
        ├── security/               # Spring Security config & CORS
        ├── users/                  # User entity, service, controller
        ├── cards/                  # Card entity, service, controller
        ├── s3/                     # AWS S3 integration
        └── exception/              # Custom exceptions
```

---

## API Overview

All endpoints are prefixed with `http://localhost:8080`.

| Method | Endpoint                              | Auth Required | Description                        |
|--------|---------------------------------------|---------------|------------------------------------|
| POST   | `/api/v1/auth/login`                  | No            | Login and receive a JWT            |
| POST   | `/api/v1/users`                       | No            | Register a new user                |
| GET    | `/api/v1/users`                       | Yes           | Get all users                      |
| GET    | `/api/v1/users/{id}`                  | Yes           | Get a user by ID                   |
| PUT    | `/api/v1/users/{id}`                  | Yes           | Update user info                   |
| DELETE | `/api/v1/users/{id}`                  | Yes           | Delete a user                      |
| POST   | `/api/v1/users/{id}/profile-image`    | Yes           | Upload a profile picture           |
| GET    | `/api/v1/users/{id}/profile-image`    | Yes           | Get profile picture                |
| POST   | `/api/v1/cards`                       | Yes           | Add a new card                     |
| GET    | `/api/v1/cards`                       | Yes           | Get all cards                      |
| GET    | `/api/v1/cards/{id}`                  | Yes           | Get a card by ID                   |
| DELETE | `/api/v1/cards/{id}`                  | Yes           | Delete a card                      |
| POST   | `/api/v1/cards/{id}/card-image`       | Yes           | Upload a card image                |
| GET    | `/api/v1/cards/{id}/card-image`       | Yes           | Get a card image                   |

Authenticated requests must include the JWT in the `Authorization` header:

```
Authorization: Bearer <token>
```

---

## Environment Variables Reference

| Variable            | Where    | Description                               |
|---------------------|----------|-------------------------------------------|
| `AWS_ACCESS_KEY`    | Backend  | AWS IAM access key ID                     |
| `AWS_SECRET_KEY`    | Backend  | AWS IAM secret access key                 |
| `S3_BUCKET_NAME`    | Backend  | S3 bucket name for image storage          |
| `VITE_API_BASE_URL` | Frontend | Base URL of the backend API               |

---

## Notes

- Card conditions are: `perfect`, `near perfect`, or `rugged`
- JWT tokens are valid for 365 days
- Maximum upload size per file is 20 MB
- The backend CORS config allows requests from `http://localhost:3000` and `http://localhost:5173`
