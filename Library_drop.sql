ALTER TABLE student
    DROP FOREIGN KEY fk_book_id;

ALTER TABLE book
    DROP FOREIGN KEY fk_issuer;

DROP TABLE book;
DROP TABLE librarian;
DROP TABLE student;
DROP TABLE super_admin;

DROP DATABASE Library;