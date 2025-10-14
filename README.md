## BibliotecaNova

Library management application written in Java (Swing for views) with a simple layered architecture (controllers, services, DAO, domain) and a MySQL database.

### Project Structure

- `src/main/java/org/example/`
  - `App.java` and `Main.java`: application bootstrap; wires services and shows the main menu.
  - `config/ConfigDb.java`: JDBC connection helper to the database.
  - `controllers/`: coordination between views and services
    - `AuthController.java`, `UserController.java`, `PartnerController.java`, `BookController.java`
  - `services/`: business logic and orchestration
    - `AuthorService.java`, `BookService.java`, `LoanService.java`, `UserService.java`
  - `dao/` and `dao/Impl/`: DAO interfaces and JDBC implementations
    - `AuthorDao.java`, `BookDao.java`, `LoanDao.java`, `RolDao.java`, `UserDao.java`
    - `Impl/AuthorDaoImpl.java`, `Impl/BookDaoImpl.java`, `Impl/LoanDaoImpl.java`, `Impl/RolDaoImpl.java`, `Impl/UserDaoImpl.java`
  - `domain/`: domain entities
    - `Author.java`, `Book.java`, `Loan.java`, `Partner.java`, `Rol.java`, `User.java`, `Users.java`
  - `views/`: Swing-based UI
    - `MainView.java`, `PartnerView.java`, `UserView.java`

### Features

- User registration and login with roles (`User`, `Partner`).
- Book management: create, edit, delete, list; track availability.
- Loans: create and return loans; auto-decrement/increment availability.
- Basic Swing menus for users and partners.

### Technology

- Java 17+ (recommended)
- Maven for build
- JDBC (raw) for persistence
- MySQL 8.x

### Database Schema (MySQL)

Run the following SQL in MySQL to create and seed the database. Review foreign keys and role names as needed.

```sql
CREATE database bibliotecaNova;
USE bibliotecaNova;

CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Seed roles (typo fix: 'Partner' instead of 'Parnter' if needed)
INSERT INTO roles (nombre) VALUES ('User'), ('Partner');

CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    asset BOOLEAN DEFAULT TRUE,
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE authors (
    id_author INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL
);

CREATE TABLE books (
    id_book INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    id_author INT,
    publication_year INT,
    availability INT,
    FOREIGN KEY (id_author) REFERENCES authors(id_author)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

CREATE TABLE loans (
    id_loan INT AUTO_INCREMENT PRIMARY KEY,
    id_book INT NOT NULL,
    id_partner INT NOT NULL,
    date_loan DATE NOT NULL,
    date_return DATE NOT NULL,
    returned BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_book) REFERENCES books(id_book)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_partner) REFERENCES users(id_user)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
```

### Configuration

Update `config/ConfigDb.java` with your MySQL connection details (host, database, user, password). Example environment variables or hardcoded credentials may be used depending on your setup.

Typical JDBC URL pattern:

```text
jdbc:mysql://localhost:3306/bibliotecaNova?useSSL=false&serverTimezone=UTC
```

### Notes and Tips

- Authors must exist (or be created) before referencing them from `books.id_author` due to the foreign key constraint.
- Availability is managed through `LoanService` when creating/returning loans.
- Role names in the DB must match what the application expects (e.g., `Partner`).

### License

This project is provided as-is for learning/demo purposes.


