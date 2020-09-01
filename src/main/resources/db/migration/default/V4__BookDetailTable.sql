CREATE TABLE book_detail (
    id BIGINT PRIMARY KEY,
    pages INTEGER NOT NULL,
    isbn VARCHAR(64),
    book_detail_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,

    FOREIGN KEY (id) REFERENCES book (id)
);

INSERT INTO book_detail (id, pages, isbn, book_detail_timestamp) VALUES(1, 250, 'ISBN-1', current_timestamp);
INSERT INTO book_detail (id, pages, isbn, book_detail_timestamp) VALUES(2, 160, 'ISBN-2', current_timestamp);
INSERT INTO book_detail (id, pages, isbn, book_detail_timestamp) VALUES(3, 160, 'ISBN-3', current_timestamp);
INSERT INTO book_detail (id, pages, isbn, book_detail_timestamp) VALUES(4, 160, 'ISBN-4', current_timestamp);
INSERT INTO book_detail (id, pages, isbn, book_detail_timestamp) VALUES(5, 160, 'ISBN-5', current_timestamp);
