CREATE TABLE review (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(512) NOT NULL,
    review_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    book_id BIGINT NOT NULL,

    FOREIGN KEY (book_id) REFERENCES book (id)
);