CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(64) NOT NULL,
    book_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);