# Bookstore Management System - REST API (Java Spring Boot)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/Database-MySQL-blue)](https://www.mysql.com/)

A robust RESTful API backend for a Bookstore Management System built with Java and Spring Boot. This project enables efficient management of books, user authentication, and order processing, supporting both customers and admins. It includes JWT-based security, pagination, search functionality, and comprehensive error handling.

## 🚀 Features

- **Book Management**: CRUD operations for books, including search by title/author, pagination, and inventory tracking.
- **User Authentication**: Secure registration and login with JWT tokens, role-based access (Customer/Admin).
- **Order Processing**: Place orders, track status, and manage inventory deductions.
- **Security**: Spring Security with JWT, CORS support, and role-based endpoint restrictions.
- **API Documentation**: Auto-generated Swagger UI for easy endpoint exploration.
- **Database Integration**: MySQL for persistent storage with JPA/Hibernate.
- **Error Handling**: Global exception handling with meaningful HTTP status codes.
- **Pagination & Search**: Efficient handling of large datasets with pageable queries.

---

## 🛠 Tech Stack

- **Backend**: Java 21, Spring Boot 3.5.6
- **Security**: Spring Security, JWT (JJWT library)
- **Database**: MySQL
- **ORM**: Spring Data JPA
- **Validation**: Bean Validation (Jakarta)
- **Documentation**: Swagger/OpenAPI (SpringDoc)
- **Build Tool**: Maven
- **Other**: Lombok for boilerplate reduction

## 📋 Prerequisites

- Java 21 or higher
- MySQL Server
- Maven 3.6+
- Git

--- 

## 🔧 Installation & Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Sandeepkumar-S-18/Bookstore-Management-System.git
   cd Bookstore-Management-System
   ```
2. **Configure Database**:
   - Create a MySQL database (e.g., bookstore_db).
   - Update src/main/resources/application.properties with your MySQL credentials:
  ```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
  jwt.secret=your_base64_encoded_secret_key
  ```
3. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   - The application will start on `http://localhost:8080`.
4. **Access API Documentation**:
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - API Docs: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 📚 API Endpoints
### 1. **Authentication**
  - `**POST** /api/register` - Register a new user (Customer/Admin).
  - `**POST** /api/login` - Login and receive JWT token.
### 2. **Books (Public: GET; Admin: POST/PUT/DELETE)**
  - `**GET** /api/books` - Get paginated list of books.
  - `**GET** /api/books/{id}` - Get book details by ID.
  - `**GET** /api/books/search?q={keyword}` - Search books by title/author.
  - `**POST** /api/books` - Add new book (Admin only).
  - `**PUT** /api/books/{id}` - Update book (Admin only).
  - `**DELETE** /api/books/{id}` - Delete book (Admin only).
### 3. **Orders**
  - `**POST** /api/orders/user/{userId}` - Place an order (Customer only).
  - `**GET** /api/orders/{id}` - Get order details.
  - `**GET** /api/orders` - Get all orders (Admin only).
  - `**GET** /api/orders/user/{userId}` - Get user's orders.
  - `**PUT** /api/orders/{orderId}/status/{status}` - Update order status (Admin only).

For detailed request/response examples, refer to the Swagger documentation.

## 🧪 Testing
Use Postman or curl to test endpoints.

Include Authorization: **Bearer JWT_TOKEN** in headers for secured routes.



