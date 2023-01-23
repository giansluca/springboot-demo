CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS person (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS book (
    id BIGSERIAL,
    title VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT book_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_detail (
    id BIGINT,
    pages INTEGER NOT NULL,
    isbn VARCHAR(64),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT book_detail_pk PRIMARY KEY (id),
    CONSTRAINT book_fk FOREIGN KEY (id) REFERENCES book(id)
);

CREATE TABLE IF NOT EXISTS review (
    id BIGSERIAL,
    text VARCHAR(1024) NOT NULL,
    book_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT review_pk PRIMARY KEY (id),
    CONSTRAINT book_fk FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE TABLE IF NOT EXISTS author (
    id BIGSERIAL,
    name VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT author_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_author (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,

    CONSTRAINT book_author_pk PRIMARY KEY (book_id, author_id),
    CONSTRAINT book_fk FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT author_fk FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE IF NOT EXISTS student (
    id BIGSERIAL,
    name VARCHAR(64) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT student_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS course (
    id BIGSERIAL,
    title VARCHAR(256) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT course_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS student_course (
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    rating INTEGER,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT student_course_pk PRIMARY KEY (student_id, course_id),
    CONSTRAINT student_fk FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT course_fk FOREIGN KEY (course_id) REFERENCES course(id)
);