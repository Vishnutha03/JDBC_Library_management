CREATE DATABASE Library;
USE Library;

GRANT ALL PRIVILEGES ON Library.* TO 'user1'@'localhost';

CREATE TABLE Library.student (
    roll_number VARCHAR(45) NOT NULL,
    full_name VARCHAR(45) NOT NULL,
    book_id VARCHAR(45),
    CONSTRAINT pk_student PRIMARY KEY (roll_number)
);

CREATE TABLE Library.book (
    book_id VARCHAR(45) NOT NULL,
    book_name VARCHAR(45) NOT NULL,
    book_author VARCHAR(45) NOT NULL,
    publication_year INTEGER NOT NULL,
    issuer VARCHAR(45),
    CONSTRAINT pk_book PRIMARY KEY (book_id)
);

CREATE TABLE Library.librarian (
    librarian_id VARCHAR(45) NOT NULL,
    librarian_name VARCHAR(45) NOT NULL,
    librarian_password VARCHAR(45) NOT NULL,
    CONSTRAINT pk_librarian PRIMARY KEY (librarian_id)
);

CREATE TABLE Library.super_admin (
    super_admin_id VARCHAR(45) NOT NULL,
    super_admin_name VARCHAR(45) NOT NULL,
    super_admin_password VARCHAR(45) NOT NULL,
    CONSTRAINT pk_super_admin PRIMARY KEY (super_admin_id)
);
                