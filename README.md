# Library Management System (Spring Boot)

### Project Overview
This Library Management System is built using Spring Boot, following Clean Architecture principles for scalability and testability. 
The system provides API endpoints to manage books, patrons, and borrowing records with JWT-based authentication for security.

## Key Features:

- Clean Architecture: The project is divided into core layers to maintain separation of concerns:
    - Domain: Contains core business entities and repository interfaces.
    - Application: Contains service use cases and DTOs.
    - Adapter: Handles user interactions (controllers, exceptions).
    - Infrastructure: Manages external services like configurations, logging (AOP), and caching.
 
- RESTful API Endpoints for managing:
    - Books: Add, update, delete, and retrieve book information.
    - Patrons: Add, update, delete, and retrieve patron details.
    - Borrowing Records: Manage borrowing and returning of books by patrons.

- JWT-based Security to protect API endpoints, ensuring only authenticated users can access system resources.

- Aspect-Oriented Programming (AOP): Implements advanced logging and performance tracking across services using AOP to monitor critical operations.

- Caching: Utilizes Spring’s caching mechanisms to store frequently accessed data like book and patron details, improving system performance.

- Transactional Integrity: Uses Spring’s @Transactional annotation to ensure data consistency during borrowing and returning operations.

- Testing: Comprehensive testing with JUnit, Mockito, and SpringBootTest for controllers, services, and repositories to ensure robust functionality.
