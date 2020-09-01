CREATE TABLE author (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    author_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);

INSERT INTO author (name, author_timestamp) VALUES ('John Smith', current_timestamp);
INSERT INTO author (name, author_timestamp) VALUES ('Terence Obama', current_timestamp);
INSERT INTO author (name, author_timestamp) VALUES ('Sam Glass', current_timestamp);
INSERT INTO author (name, author_timestamp) VALUES ('Bill booking', current_timestamp);

CREATE TABLE book_author (
    book_id BIGINT NOT NULL REFERENCES book (id) ON UPDATE CASCADE ON DELETE CASCADE,
    author_id BIGINT NOT NULL REFERENCES author (id) ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT book_author_pkey PRIMARY KEY (book_id, author_id)
);

INSERT INTO book_author (book_id, author_id) VALUES (1, 1);
INSERT INTO book_author (book_id, author_id) VALUES (2, 2);
INSERT INTO book_author (book_id, author_id) VALUES (3, 3);
INSERT INTO book_author (book_id, author_id) VALUES (4, 3);
INSERT INTO book_author (book_id, author_id) VALUES (5, 4);

