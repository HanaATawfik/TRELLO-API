# Trello Backend Application

## Overview

This repository contains the backend implementation of a Trello-like application. The application provides functionalities for creating boards, lists, and cards to organize tasks effectively. Users can collaborate with team members, assign tasks, and track progress. The backend is built using Java EE technologies, ensuring robust performance, scalability, and security.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Setup and Installation](#setup-and-installation)
- [API Endpoints](#api-endpoints)
- [Detailed Explanation](#detailed-explanation)
- [Contributing](#contributing)
- [License](#license)

## Features

### User Management

- **Registration:** Allows new users to register with a valid email and password.
- **Login:** Allows registered users to log in using their credentials.
- **Profile Management:** Users can update their profile information.

### Board Management

- **Create Board:** Users can create new boards.
- **View Boards:** Users can view all boards they have access to.
- **Invite Collaborators:** Users can invite other users to collaborate on a board.
- **Delete Board:** Users can delete boards they have created.

### List Management

- **Create Lists:** Users can create lists within boards to categorize tasks.
- **Delete Lists:** Users can delete lists within boards.

### Card Management

- **Create Cards:** Users can create cards within lists to represent tasks.
- **Move Cards:** Users can move cards between lists.
- **Assign Cards:** Users can assign cards to collaborators.
- **Add Descriptions and Comments:** Users can add descriptions and comments to cards.

### Messaging System

- **Real-Time Updates:** Provides real-time updates for any changes made to boards, lists, or cards.

### Security Authorization

- **Role-Based Access Control:** Ensures that only authorized users can perform certain actions.

## Technologies

- **Java EE:** For building the enterprise-grade backend.
- **JAX-RS:** For creating RESTful web services.
- **EJB:** For managing business logic.
- **JPA:** For database operations.
- **PostgreSQL:** As the relational database.
- **WildFly:** As the application server.

## Architecture

The application follows a layered architecture to separate concerns and improve maintainability:

1. **Presentation Layer:** Handles incoming requests and sends responses.
2. **Service Layer:** Contains business logic and communicates with the data layer.
3. **Data Layer:** Manages database interactions using JPA entities and repositories.

### Directory Structure

```
src/main/java
├── dto
│   ├── boardDTO.java
│   ├── cardDTO.java
│   ├── listDTO.java
│   ├── userDTO.java
├── model
│   ├── board.java
│   ├── card.java
│   ├── list.java
│   ├── user.java
├── service
│   ├── boardService.java
│   ├── cardService.java
│   ├── listService.java
│   ├── userService.java
```

## Setup and Installation

### Prerequisites

- **Java JDK 8 or higher**
- **Maven**
- **PostgreSQL**
- **WildFly Application Server**

### Steps

1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/trello-backend.git
   cd trello-backend
   ```

2. **Set up the database:**
   - Create a PostgreSQL database.
   - Update the `persistence.xml` file with your database configuration.

3. **Build and deploy the application:**
   - Use Maven to build the project:
     ```sh
     mvn clean install
     ```
   - Deploy the generated WAR file to your application server.

4. **Configure the application server:**
   - Ensure the application server is configured to use the necessary datasources and security settings.

## API Endpoints

### User Management

- **Register User:** `POST /api/users/register`
- **Login User:** `POST /api/users/login`
- **Update Profile:** `PUT /api/users/profile`

### Board Management

- **Create Board:** `POST /api/board/create`
- **View Boards:** `GET /api/boards`
- **Invite Collaborator:** `POST /api/board/invite`
- **Delete Board:** `DELETE /api/board/delete`

### List Management

- **Create List:** `POST /api/list/create`
- **Delete List:** `DELETE /api/list/delete`

### Card Management

- **Create Card:** `POST /api/card/create`
- **Move Card:** `POST /api/card/move`
- **Assign Card:** `POST /api/card/assign`
- **Add Description and Comments:** `POST /api/card/comment`

## Detailed Explanation

### Board Service

The `boardService` class handles operations related to boards, including creation, deletion, and collaboration management. It uses JPA to interact with the database and EJB for business logic.

#### Create Board

```java
@POST
@RolesAllowed({"TeamLeader"})
@Path("create")
public Response createBoard(boardDTO input) {
    try {
        board board = input.getBoard();
        user user = entityManager.find(user.class, input.getUser().getId());
        
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user").build();
        }
        
        entityManager.persist(board);
        user.getBoards().add(board);
        entityManager.merge(user);
        
        return Response.status(Response.Status.CREATED).entity("Board created successfully").build();
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to create board").build();
    }
}
```

This method creates a new board, associates it with the user, and persists it to the database.

#### Get Collaborators

```java
@GET
@RolesAllowed("TeamLeader")
@Path("collaborators/{boardId}")
public Response getCollaborators(@PathParam("boardId") int boardId) {
    try {
        board board = entityManager
            .createQuery("SELECT b FROM board b LEFT JOIN FETCH b.collaborators WHERE b.id = :boardId", board.class)
            .setParameter("boardId", boardId)
            .getSingleResult();
            
        return Response.ok(board.getCollaborators()).build();
    } catch (NoResultException e) {
        return Response.status(Response.Status.NOT_FOUND).entity("Board does not exist").build();
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to create board").build();
    }
}
```

This method retrieves all collaborators for a given board by querying the database and returning the result.

### Card Service

The `cardService` class handles operations related to cards, including creation, assignment, and movement between lists.

#### Create Card

```java
@POST
@RolesAllowed({"TeamLeader", "Collaborator"})
@Path("create")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Response createCard(cardDTO input) {
    try {
        list list = entityManager.find(list.class, input.getListId());
        user user = userservice.fetchCurrentUser();
        
        String title = input.getTitle();
        String description = input
        ...
```

This method creates a new card, associates it with a list, and persists it to the database.

## Contributing

Contributions are welcome! Please read the [contributing guidelines](CONTRIBUTING.md) for more details.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

By providing a detailed explanation of the services, endpoints, and technologies used, this README aims to offer a comprehensive guide to the repository. It covers setup, usage, and an in-depth look at the core functionalities, making it easier for other developers to understand and contribute to the project.
