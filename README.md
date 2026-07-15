# BackendSpring

A Spring Boot 4 REST API for managing students and employees, built as a hands-on learning project for the Spring MVC request pipeline — servlet filters, handler interceptors, profile-based configuration, and centralized exception handling.

The CRUD layer works and is reasonably conventional. The filter and interceptor layers are deliberately experimental: they exist to demonstrate ordering, registration styles, and request/response wrapping. See [Filters and interceptors](#filters-and-interceptors) before you run it, because they affect every request.

## Tech stack

| Component | Version / choice |
| --- | --- |
| Java | 21 (toolchain) |
| Spring Boot | 4.1.0 |
| Build | Gradle (wrapper included) |
| Database | PostgreSQL via Spring Data JPA / Hibernate |
| Other | Lombok, Jakarta Bean Validation, java-dotenv |

## Getting started

### Prerequisites

You need JDK 21 and a reachable PostgreSQL instance.

### Configure the database

The application loads a `.env` file at startup (in `BackendSpringApplication.main`, before Spring boots) and copies each entry into system properties, so the YAML placeholders can resolve. Create a `.env` in the project root — it is gitignored:

```
DB_URL=jdbc:postgresql://localhost:5432/backendspring
DB_USERNAME=postgres
DB_PASSWORD=yourpassword
```

The default profile only reads `DB_URL`; every named profile also reads `DB_USERNAME` and `DB_PASSWORD`. The `staging` profile additionally needs `SERVER_PORT`.

### Run

```bash
./gradlew bootRun
```

Or pick a profile:

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

Build a jar and run the tests:

```bash
./gradlew build
./gradlew test
```

## Profiles

| Profile | Port | `ddl-auto` | `app.message` |
| --- | --- | --- | --- |
| default (`application.yaml`) | 8084 | `update` | Hello from default profile |
| `dev` | 8080 | `update` | Hello from DEV |
| `test` | 8081 | `create-drop` | Hello from TEST |
| `staging` | `${SERVER_PORT}` | `validate` | Hello from STAGING |
| `prod` | 8080 | `validate` | Hello from PROD |

`GET /api/profile` and `GET /api/message` echo the active profile and its message back, which is a quick way to confirm which configuration actually loaded.

## API

Both resources expose the same CRUD shape. `DELETE` is a soft delete: it sets the `is_deleted` column and every query filters on it, so deleted rows stay in the table but disappear from the API.

| Method | Path | Notes |
| --- | --- | --- |
| `POST` | `/api/students` | Body validated; duplicate email returns 409 |
| `GET` | `/api/students` | Non-deleted only |
| `GET` | `/api/students/{id}` | 404 if missing or deleted |
| `PUT` | `/api/students/{id}` | Full replace, all fields required |
| `PATCH` | `/api/students/{id}` | Partial update, null fields ignored |
| `DELETE` | `/api/students/{id}` | Soft delete, returns 204 |

`/api/employees` mirrors this exactly. `GET /api/message` and `GET /api/profile` round out the surface.

A student requires `name`, `email`, `course`, and `age` (1–120); an employee requires `name`, `email`, and `password`.

### Error format

`GlobalExceptionHandler` maps exceptions onto a single `ApiResponse` record, so failures share one shape. Null fields are omitted.

Validation failure (400) collects every rejected field:

```json
{
  "message": "Validation failed",
  "status": false,
  "timestamp": "2026-07-15T10:30:00",
  "errors": {
    "email": "email must be a valid email address",
    "age": "age must be at least 1"
  }
}
```

Not found is 404, duplicate email is 409, both without the `errors` map.

## Filters and interceptors

This is the part of the project that exists to be learned from rather than reused. Two registration styles sit side by side.

**Auto-registered `@Component` filters.** Spring Boot maps any `Filter` bean to `/*`, so these run on *every* request, and their `@Order` value decides the sequence:

| Filter | Order | What it does |
| --- | --- | --- |
| `ResponseHeaderFilter` | 1 | Adds an `x-request-id` UUID header |
| `AuthFilter` | 1 | Rejects requests without `token: 12345` and `x-api-key: secret123` |
| `LoggingFilter` | 2 | Logs method, URI, status, and duration; sets `X-Request-ID` |
| `RequestFilter` | 2 | Reads and prints the raw request body |
| `ResponseBodyFilter` | 3 | Wraps the response body in an envelope via `ContentCachingResponseWrapper` |

**Explicitly registered filters.** `FilterConfig` registers `DummyFilter` (order 4, `/api/*` and `/admin/*`) and `SpringDemoFilter` (order 5, `/api/*`) through `FilterRegistrationBean`, which is how you scope a filter to specific URL patterns instead of everything.

**Interceptors.** `WebConfig` wires three `HandlerInterceptor`s, which run after the filter chain and have access to the resolved handler: `AuthenticationInterceptor` (order 1, `/api/**`, excluding login and public paths), `AuthorizationInterceptor` (order 2, rejects `x-user-role` other than `ADMIN` with 403), and `LoggingInterceptor` (order 3, logs request details plus the controller method it resolved to).

### Consequences worth knowing before you run it

These are real behaviors of the current code, not hypotheticals:

- **Every request needs auth headers.** `AuthFilter` is auto-registered against `/*`, so any request lacking `token: 12345` and `x-api-key: secret123` gets a 401 before reaching a controller.
- **Every response is re-wrapped.** `ResponseBodyFilter` nests the real payload under `originalResponse` and appends an `appName` field, so what you receive is not what the controller returned:
  ```json
  { "originalResponse": { "id": 1, "name": "Ada" }, "appName": "Student Management System" }
  ```
- **`RequestFilter` consumes the request body.** It calls `getReader()` without wrapping the request, and the servlet input stream can only be read once, so request bodies may not reach `@RequestBody` afterward. This is the classic reason to reach for `ContentCachingRequestWrapper` — the filter is left as-is to make the failure visible.
- **`ResponseHeaderFilter` and `LoggingFilter` both set a request-id header**, under different casings, at overlapping order values.

A working request therefore looks like:

```bash
curl -X POST http://localhost:8084/api/students \
  -H "Content-Type: application/json" \
  -H "token: 12345" \
  -H "x-api-key: secret123" \
  -d '{"name":"Ada","email":"ada@example.com","course":"CS","age":30}'
```

## Project layout

```
src/main/java/com/backendspring/
├── controller/    REST endpoints (Student, Employee, Message)
├── service/       Interfaces + impl/ package
├── repository/    Spring Data JPA repositories
├── entity/        JPA entities (Student, Employee)
├── dto/           Request/response records + ApiResponse
├── mapper/        Entity <-> DTO conversion
├── exception/     Domain exceptions + GlobalExceptionHandler
├── filter/        Servlet filters
├── interceptor/   Handler interceptors
└── config/        FilterConfig, WebConfig
```

Controllers stay thin and delegate to services; services own the business rules and throw domain exceptions; mappers keep entities out of the API surface. DTOs are Java records, and entities use Lombok builders.

## Not production-ready

Beyond the filter quirks above, this project is a learning sandbox and the auth is a placeholder:

- Credentials (`12345`, `secret123`) are hardcoded in `AuthFilter` and `AuthenticationInterceptor`.
- `AuthenticationInterceptor` and `AuthorizationInterceptor` only reject a header that is *present and wrong* — a missing `x-api-key` or `x-user-role` passes.
- `Employee.password` is stored as plaintext and no encoder is configured.
- Spring Security is not on the classpath; none of this is a substitute for it.
- Schema is managed by Hibernate `ddl-auto` rather than a migration tool like Flyway or Liquibase.
