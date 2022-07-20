ALTER TABLE student
    ADD CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES book(book_id);

ALTER TABLE book
    ADD CONSTRAINT fk_issuer FOREIGN KEY (issuer) REFERENCES student(roll_number);