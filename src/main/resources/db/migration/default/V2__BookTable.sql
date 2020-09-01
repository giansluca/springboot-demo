CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(64) NOT NULL,
    book_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);

INSERT INTO book (title, book_timestamp) VALUES('The name of the rose', current_timestamp);
INSERT INTO book (title, book_timestamp) VALUES('The big Gatsby', current_timestamp);
INSERT INTO book (title, book_timestamp) VALUES('Atlas Obscura', current_timestamp);
INSERT INTO book (title, book_timestamp) VALUES('Big bomb', current_timestamp);
INSERT INTO book (title, book_timestamp) VALUES('Fishing time', current_timestamp);