CREATE TABLE review (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(512) NOT NULL,
    review_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    book_id BIGINT NOT NULL,

    FOREIGN KEY (book_id) REFERENCES book (id)
);

INSERT INTO review (text, review_timestamp, book_id) VALUES('Very good book', current_timestamp, 2);
INSERT INTO review (text, review_timestamp, book_id) VALUES('Beautiful', current_timestamp, 2);
INSERT INTO review (text, review_timestamp, book_id) VALUES('Very bad', current_timestamp, 2);
INSERT INTO review (text, review_timestamp, book_id) VALUES('Nice book', current_timestamp, 2);
INSERT INTO review (text, review_timestamp, book_id) VALUES('Not bad', current_timestamp, 1);
INSERT INTO review (text, review_timestamp, book_id) VALUES('It is annoying ', current_timestamp, 1);