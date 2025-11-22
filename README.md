Java Library Management System

A complete Library Management System built using Java 21, JDBC, MySQL, DAO Layer Architecture, and Maven.
This project demonstrates real backend development skills 

Features
Book Management

Add books (title, author, year)

View all books

Search books by title/author

Book availability status: Available / Issued

User Management

Add new users (name + email)

View user details

Validate user before issuing books

Issue / Return System

Issue a book to a user

Prevent issuing the same book twice

Return issued books

Track active loans (issued date, return date)

🗄 Database Integration (MySQL)

Dockerized MySQL support

JDBC connectivity

Proper relational schema

Foreign key relationships

Project Architecture (DAO Design Pattern)
src
 └── main/java/org/example
      ├── db
      │    └── DBConnection.java
      ├── dao
      │    ├── BookDAO.java
      │    ├── UserDAO.java
      │    └── LoanDAO.java
      ├── model
      │    ├── Book.java
      │    ├── User.java
      │    └── LoanRecord.java
      ├── service
      │    └── LibraryService.java
      └── Main.java


This follows an industry-standard layered architecture:

Model Layer → Structures data

DAO Layer → Handles SQL logic

Service Layer → Business logic

Main → Console UI

🗄 Database Schema (MySQL)
CREATE DATABASE library_db;
USE library_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150),
    author VARCHAR(100),
    year INT,
    is_issued BOOLEAN DEFAULT FALSE
);

CREATE TABLE loans (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    returned_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

JDBC Configuration

Update credentials in DBConnection.java:

private static final String URL =
    "jdbc:mysql://localhost:3306/library_db?useSSL=false&allowPublicKeyRetrieval=true";
private static final String USER = "root";
private static final String PASSWORD = "your_password";

How to Run the Project
1️Start MySQL (Docker example)
docker run --name mysql \
-e MYSQL_ROOT_PASSWORD=ankit@123 \
-p 3306:3306 -d mysql:latest

2️Apply SQL Schema

Import the SQL commands above using DBeaver / MySQL Workbench.

3️Run the Project

Inside IntelliJ IDEA:

Run → Main.java


You will see:

=== Library Management System ===
1. Add Book
2. Add User
3. View All Books
4. Search Books
5. Issue Book
6. Return Book
7. View Active Loans
8. Exit

Why This Project Is Strong for Interviews

This project demonstrates:

Core Java (OOP, Classes, Objects, Inheritance, Encapsulation)

JDBC Database Connectivity

MySQL RDBMS Design

DAO + Service Architecture

Error handling & Input validation

CRUD operations

Real-world issue/return system logic

Docker usage

Clean folder structure


License

This project is licensed under the MIT License.

⭐ Show Support

If this project helped you, please ⭐ star the repo!
