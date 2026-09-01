# Chat App

A real-time chat service built with Kotlin and Spring Boot.

The application provides REST APIs for user and room management and WebSocket communication for real-time messaging. Messages are persisted in a SQLite database so that chat history can be retrieved later.

## Technologies

- Kotlin
- Spring Boot
- Spring Web MVC
- Spring WebSocket
- Spring JDBC
- SQLite
- Gradle
- JDK 21

## Requirements

Before running the application, make sure you have:

- JDK 21
- Git
- IntelliJ IDEA or another Kotlin-compatible IDE

The project includes the Gradle Wrapper, so Gradle does not need to be installed separately.

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Ritaorah24/chatApp.git

2. Navigate into the project
cd chatApp

3. Build the application

On Windows:
./gradlew clean build
If that command does not work, use:
gradlew.bat clean build
A successful build should display:
BUILD SUCCESSFUL

Running the Application
Using IntelliJ IDEA

Run the ChatAppApplication class from IntelliJ IDEA.

Using the JAR

After building the application, the executable JAR will be created in:
build/libs/chatApp-0.0.1-SNAPSHOT.jar
Run it with:
java -jar build/libs/chatApp-0.0.1-SNAPSHOT.jar
The application runs on:
http://localhost:8081

REST API
1. Register a User

POST
/users
Request body:
{
  "userName": "john"
}

A successful request returns:
201 Created
If the username already exists:
409 Conflict
If the username is empty:
400 Bad Request

2. Create a Room

POST
/rooms
Request body:
{
  "roomName": "general"
}

A successful request returns:
201 Created
If the room already exists:
409 Conflict
If the room is empty:
400 Bad Request

3. Get All Rooms

GET
/rooms
This returns a list of all available chat rooms.

4. Get Message History

GET
/rooms/{roomName}/messages
Example:
/rooms/general/messages
This returns all messages saved for the specified room in chronological order.

WebSocket

The application uses WebSocket for real-time communication between users in a chat room.

WebSocket Endpoint
ws://localhost:8081/chat/{roomName}?user={username}

Example:
ws://localhost:8081/chat/general?user=john

Another user can connect to the same room:
ws://localhost:8081/chat/general?user=maria

How messaging works

When a user sends a message:

-The message is received through the WebSocket.
-The message is saved to the SQLite database.
-The saved message is converted to JSON.
-The message is sent to the other users connected to the same room.
-The message can later be retrieved using the message history REST endpoint.

Users must already exist in the database and the room must already exist before connecting.

Join and Leave Notifications

When a user joins a room, the other connected users receive a notification.

Example:
{
  "type": "NOTICE",
  "content": "john joined"
}
When a user leaves the room, the other connected users receive a notification.

Example:
{
  "type": "NOTICE",
  "content": "john left"
}

Database

The application uses SQLite for data persistence.

The database file is:
chat.db

The database schema is defined in:
src/main/resources/schema.sql

The database contains three tables:
Users

Stores registered users.
users

Rooms

Stores chat rooms.
rooms

Messages

Stores messages sent in chat rooms.
messages

The messages table stores:

-Message ID
-Room name
-Username
-Message content
-Creation timestamp

The messages table also uses foreign keys to connect messages to their corresponding users and rooms.

Exception Handling

The application uses custom exceptions and a global exception handler.

The application handles situations such as:

-Duplicate usernames
-Duplicate room names
-Invalid requests
-Empty usernames
-Empty room names

The global exception handler returns appropriate HTTP status codes and error messages.

Project Structure

chatApp/
│
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── org/chatappapi/chatapp/
│   │   │       ├── controller/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── schema.sql
│   │
│   └── test/
│       └── kotlin/
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
├── .gitignore
├── README.md
└── chat.db

Testing

The application includes Spring Boot tests.

Run the tests with:
./gradlew test

To build the application and run the tests:
./gradlew clean build

A successful build should display:
BUILD SUCCESSFUL

Building the JAR

To create the executable JAR:
./gradlew clean build

The JAR will be generated in:
build/libs/chatApp-0.0.1-SNAPSHOT.jar

The application can then be started independently of IntelliJ using:
java -jar build/libs/chatApp-0.0.1-SNAPSHOT.jar

Application Configuration

The application runs on port 8081.

The SQLite database configuration is located in:
src/main/resources/application.properties

Current configuration:
spring.application.name=chatApp
server.port=8081
spring.datasource.url=jdbc:sqlite:chat.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql

Future Improvements

Possible future improvements include:

-User authentication and authorization
-Private messaging
-Online/offline user status
-Message deletion
-Message editing
-Read receipts
-Typing indicators
-Frontend user interface
-Cloud deployment




