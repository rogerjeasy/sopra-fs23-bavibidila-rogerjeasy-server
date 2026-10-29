# SoPra FS23 User Management Server

A Spring Boot REST service for user registration, login, logout and profile management (view and edit username and birthday), written for the individual assignment of the Software Engineering Lab (SoPra) at the University of Zurich, spring semester 2023. It extends the course's server template and stores users with Spring Data JPA in an in-memory H2 database.

The matching front end is [sopra-fs23-bavibidila-rogerjeasy-client](https://github.com/rogerjeasy/sopra-fs23-bavibidila-rogerjeasy-client).

## REST API

Endpoints are defined in `src/main/java/ch/uzh/ifi/hase/soprafs23/controller/UserController.java` (plus a health route in `Application.java`). CORS is open to all origins and methods. No endpoint checks a token.

| Method | Path | Success | Errors | Notes |
|---|---|---|---|---|
| GET | `/` | 200 | | Plain-text message confirming the server is running. |
| GET | `/users` | 200 | | Lists all users. |
| POST | `/users` | 201 | 400 when the username is taken | Registers a user from `{ "username", "password" }` with status `OFFLINE` and a random token. |
| POST | `/users/login` | 200 | 500 on wrong credentials | Finds the user whose username and password match and sets the status to `ONLINE`. |
| GET | `/users/{id}` | 200 | | Returns the user entity. An unknown id returns an empty user, not 404. |
| PUT | `/users/{id}` | 200 | 400 when the new username is taken | Updates `username` and/or `birthDay`, issues a new token, sets status `ONLINE`. |
| POST | `/users/logout/{id}` | 200 | | Sets the user's status to `OFFLINE`. |

`GET /users`, `POST /users` and `POST /users/login` return a DTO with `id`, `username`, `password`, `status` (`ONLINE`/`OFFLINE`), `isLoggedIn`, `createDate` and `birthDay`, but no token. `GET` and `PUT /users/{id}` return the full entity, including the token.

## Tech stack

- Java 17, Spring Boot 2.4 (Web, Data JPA), MapStruct for DTO mapping
- H2 in-memory database (data is reset on every restart)
- JUnit 5, Mockito, Spring MockMvc
- Gradle 7.6 wrapper, Google App Engine standard (`app.yaml`), GitHub Actions

## Project structure

```
src/main/java/ch/uzh/ifi/hase/soprafs23/
  Application.java       entry point, "/" route, CORS config
  controller/            UserController (REST endpoints)
  service/               UserService (registration, update, logout logic)
  repository/            UserRepository (Spring Data JPA)
  entity/  rest/dto/  rest/mapper/  constant/  exceptions/
src/test/java/...        controller, mapper, service and repository tests
app.yaml                 App Engine config
.github/workflows/       deploy (main.yml) and PR test (pr.yml) workflows
```

## Getting started

### Prerequisites

Java 17 (on Windows, set `JAVA_HOME` to it). The Gradle wrapper is included (`./gradlew` on macOS/Linux, `gradlew.bat` on Windows). No environment variables are needed.

### Build, run and develop

```bash
./gradlew build      # compile and run tests (fails at the moment, see Tests)
./gradlew bootJar    # build the runnable jar without running tests
./gradlew bootRun    # start on http://localhost:8080
```

Development mode with automatic rebuild and reload: run `./gradlew build --continuous -xtest` in one terminal and `./gradlew bootRun` in another.

The H2 console is enabled at `http://localhost:8080/h2-console` while the server runs (JDBC URL `jdbc:h2:mem:testdb`, user `sa`, empty password, as set in `application.properties`).

To debug, start the server with `./gradlew bootRun --debug-jvm` and attach a remote debugger from your IDE.

### IDE setup

- IntelliJ: File > Open the project folder and import it as a Gradle project.
- VS Code: install `vmware.vscode-spring-boot`, `vscjava.vscode-spring-initializr`, `vscjava.vscode-spring-boot-dashboard` and `vscjava.vscode-java-pack`, build once with the Gradle Tasks extension, then start `soprafs23` from the Spring Boot Dashboard.

## Tests

```bash
./gradlew test
```

The suite has 14 tests. The service, repository and DTO mapper tests (9) pass, covering registration, duplicate-username rejection and logout. The 5 `UserControllerTest` tests (list, register, login, get one, update) currently fail to start: `UserController` also needs a `UserRepository`, which the `@WebMvcTest` does not mock. Adding a `@MockBean UserRepository` to the test class, or moving the login logic into `UserService`, would fix this.

## Deployment

- `app.yaml` targets the App Engine standard environment (`runtime: java17`, instance class F2).
- `.github/workflows/main.yml` deploys to App Engine on every push to `main`, using a `GCP_SERVICE_CREDENTIALS` repository secret.
- `.github/workflows/pr.yml` builds and tests pull requests against `main` with Java 17; it will fail until the controller tests are fixed.

The App Engine deployment from the course is no longer running.

## Known gaps

- Login with a wrong username or password does not return 401 or 404; the controller tries to save an empty user instead, which fails with 500.
- Registration and login responses carry no token, so the client stores `undefined` as its token.
- Responses include the stored password, and passwords are stored in plain text.
- Saving a profile without changing the username returns 400, because the uniqueness check also matches the user's own record.

## Context

Built on the server template from the University of Zurich SoPra course (Software Engineering Lab, FS23). The template provided the Spring Boot project layout, Gradle setup, App Engine configuration and workflows; the login, logout, profile view/edit endpoints and their tests are the owner's own work.

## License

Apache License 2.0, see [LICENSE](LICENSE).
