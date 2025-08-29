# Notes Spring App

A Spring Boot application for managing notes. This project uses MongoDB for data storage and demonstrates best practices for configuration management.

## Features

* REST API for creating, reading, updating, and deleting notes.
* MongoDB integration.
* Secure configuration using environment variables.
* Ready to run locally.

## Getting Started

### Prerequisites

* Java 17 or later
* Maven or Gradle
* MongoDB Atlas cluster or local MongoDB instance
* IntelliJ IDEA or any IDE supporting Kotlin/Java

---

### Clone the Repository

```bash
git clone https://github.com/your-username/notes_spring_app.git
cd notes_spring_app
```

---

### MongoDB Setup
This project requires a MongoDB database. You can:
- [Install MongoDB locally](https://www.mongodb.com/docs/manual/installation/), or
- Use [MongoDB Atlas](https://www.mongodb.com/atlas/database) for a free cloud-hosted option.

Once you have a connection string, set it in your environment variables as `MONGODB_CONNECTION_STRING`.

---

### Configuration

This project uses environment variables to keep sensitive information out of version control.

1. **Environment Variables**

    * Example in `application.properties`:

      ```properties
      spring.application.name=notes_spring_app
      server.port = 9090
      spring.output.ansi.enabled=always
      spring.data.mongodb.uri = ${MONGODB_CONNECTION_STRING}
      spring.data.mongodb.auto-index-creation=true
      jwt.secret = ${JWT_BASE64}
      ```
    * Replace `${MONGODB_CONNECTION_STRING}` and `${JWT_BASE64}` with your own values using environment variables.
    * In IntelliJ/Android Studio:

        * Go to **Run → Edit Configurations → Environment Variables → Add...**
        * Add keys like `MONGODB_CONNECTION_STRING` and `JWT_BASE64` with your values.

2. **Sample Configuration File**

    * The repository includes an `application.properties.example` file with placeholders.
    * To set up your local configuration:

      ```bash
      cp src/main/resources/application.properties.example src/main/resources/application.properties
      ```
    * Edit the copied file or set the variables in your IDE.

3. **.gitignore**

    * Ensure the actual `application.properties` file is added to `.gitignore` so secrets are not committed.
    * Only commit the `.example` file.

---

### Run the Application

```bash
./mvnw spring-boot:run
```

Or using Gradle:

```bash
./gradlew bootRun
```

---

### API Endpoints

* `GET /notes` - Get a note for authintecated user.
* `POST /notes` - Create a new note.
* `DELETE /notes/{id}` - Delete a note.
* `POST /auth/register` - register new user.
* `POST /auth/login` - user login.

