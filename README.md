# Flights API

A full-stack flight booking application with a Spring Boot REST API backend and a Next.js frontend. It manages flights, airports, and bookings with optimistic locking to handle concurrent seat reservations safely.

## Table of Contents

- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Running the App](#running-the-app)
- [API Endpoints](#api-endpoints)
- [Architecture](#architecture)
- [Testing](#testing)

## Tech Stack

- **Backend**: Java 21, Spring Boot 4, Spring Data JPA / Hibernate, MariaDB
- **Frontend**: Next.js (TypeScript)
- **Build**: Gradle (multi-module — `commons`, `server`, `client`)

## Prerequisites

- Java 21+
- Node.js (for the frontend)
- MariaDB running on `localhost:3306`
  - Database: `flights`
  - User / password: `demo` / `demo`

The schema is created automatically on startup and dropped on shutdown (development mode).

## Running the App

Start the backend:

```bash
./gradlew bootRun
```

The API is available at `http://localhost:8080`.

Then start the frontend dev server:

```bash
cd client
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

## API Endpoints

### Flights — `/api/flights`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/flights` | List flights (optional query params: `flightNumber`, `origin`, `destination`, `airline`) |
| `GET` | `/api/flights/{id}` | Get a flight by ID |
| `POST` | `/api/flights` | Create a new flight |
| `DELETE` | `/api/flights/{id}` | Delete a flight |
| `PATCH` | `/api/flights/{id}/book` | Book seats (body: `{ seats, version }`) |

### Airports — `/api/airports`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/airports` | List airports (optional query param: `search` — matches code, name, or city) |

### Error responses

All errors follow a consistent JSON shape:

```json
{
  "message": "...",
  "reason": "...",
  "timestamp": "..."
}
```

HTTP status codes: `400` invalid request, `404` not found, `409` concurrent booking conflict.

## Architecture

```
commons/          Shared JPA entities (Flight, Airport) and enums
server/           Spring Boot REST API
  api/            Controllers + global exception handler
  services/       Business logic (booking, seat management, airport upsert)
  database/       Spring Data JPA repositories with custom JPQL queries
  dto/            Request DTOs (BookingRequest)
  exceptions/     ServerException hierarchy mapped to HTTP statuses
  config/         CORS and other Spring beans
client/           Next.js frontend
```

Key design decisions:

- **Optimistic locking** — `Flight` has a JPA `@Version` field. The booking endpoint requires the client to pass the current version; a mismatch returns `409 Conflict`.
- **Prices in cents** — stored as `long` to avoid floating-point issues.
- **Auto-deletion** — a flight is automatically removed when its available seats reach zero after a booking.
- **Airport upsert** — missing airports are created on the fly when a flight is created.

## Testing

```bash
./gradlew test                                          # run all tests
./gradlew test --tests "server.services.FlightServiceTest"  # run a single class
```

Service tests use hand-rolled in-memory fakes (`TestFlightRepository`, `TestAirportRepository`). Controller tests use `MockMvc` with mocked services inside a full Spring Boot test context.
