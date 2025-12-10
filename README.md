# Banking App Backend (Spring Boot, Java)

This project is a **simple educational banking application backend** built with **Java Spring Boot**. It was used to help explore core backend concepts, RESTful APIs, and Spring Boot features, including **Spring Security**.



## Overview

The backend supports common banking operations and user management. This project was primarily built to **learn Spring Boot and Spring Security** while implementing banking workflows

Key learning objectives include:
- REST API design with Spring Boot controllers  
- Service layer architecture and dependency injection  
- DTOs and entity mapping  
- CRUD operations for users, accounts, and bills  
- Money transfer functionality with validation  
- **User authentication and registration with Spring Security**  
- Role-based access and secure endpoints  

## Features

### User Management
- Create, read, update, and delete users by ID or email  
- Partial and full updates using PATCH and PUT  
- List all users  
- Register and authenticate users securely with Spring Security  

### Account Management
- Create, read, update, and delete accounts by ID or IBAN  
- Partial and full updates using PATCH and PUT  
- List all accounts or accounts filtered by user ID/email  
- Transfer money between accounts with proper validation  

### Bill Management
- Retrieve bills associated with a user by email  
- Map entities to DTOs for secure API responses  

### Miscellaneous
- A simple welcome endpoint for testing
- Structured exception handling and proper HTTP response codes  

## Tech Stack

- Java 17+  
- Spring Boot 3.x  
- Spring Security  
- Spring Data JPA  
- H2 / PostgreSQL (or any SQL database)  
- Maven or Gradle  
