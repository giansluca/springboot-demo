CREATE TABLE book_detail (
    id BIGINT PRIMARY KEY,
    pages INTEGER NOT NULL,
    isbn VARCHAR(64),
    book_detail_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,

    FOREIGN KEY (id) REFERENCES book (id)
);
