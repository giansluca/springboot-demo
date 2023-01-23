DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE person (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(64) NOT NULL
);

INSERT INTO person (id, name) VALUES(uuid_generate_v1mc(), 'maria');
INSERT INTO person (id, name) VALUES(uuid_generate_v1mc(), 'terence');
INSERT INTO person (id, name) VALUES(uuid_generate_v1mc(), 'gians');

CREATE TABLE book (
    id BIGSERIAL,
    title VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT book_pk PRIMARY KEY (id)
);

INSERT INTO book (title, created_at, updated_at) VALUES('The name of the rose', current_timestamp, current_timestamp);
INSERT INTO book (title, created_at, updated_at) VALUES('The big Gatsby', current_timestamp, current_timestamp);
INSERT INTO book (title, created_at, updated_at) VALUES('Atlas Obscura', current_timestamp, current_timestamp);
INSERT INTO book (title, created_at, updated_at) VALUES('Big bomb', current_timestamp, current_timestamp);
INSERT INTO book (title, created_at, updated_at) VALUES('Fishing time', current_timestamp, current_timestamp);

CREATE TABLE book_detail (
    id BIGINT,
    pages INTEGER NOT NULL,
    isbn VARCHAR(64),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT book_detail_pk PRIMARY KEY (id),
    CONSTRAINT book_fk FOREIGN KEY (id) REFERENCES book(id)
);

CREATE TABLE review (
    id BIGSERIAL,
    text VARCHAR(1024) NOT NULL,
    book_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT review_pk PRIMARY KEY (id),
    CONSTRAINT book_fk FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE TABLE author (
    id BIGSERIAL,
    name VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT author_pk PRIMARY KEY (id)
);

CREATE TABLE book_author (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,

    CONSTRAINT book_author_pk PRIMARY KEY (book_id, author_id),
    CONSTRAINT book_fk FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT author_fk FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE student (
    id BIGSERIAL,
    name VARCHAR(64) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT student_pk PRIMARY KEY (id)
);

INSERT INTO student (name, created_at, updated_at) VALUES('Damian', current_timestamp, current_timestamp);
INSERT INTO student (name, created_at, updated_at) VALUES('Sante', current_timestamp, current_timestamp);
INSERT INTO student (name, created_at, updated_at) VALUES('Peter', current_timestamp, current_timestamp);


CREATE TABLE course (
    id BIGSERIAL,
    title VARCHAR(256) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT course_pk PRIMARY KEY (id)
);

INSERT INTO course (title, created_at, updated_at) VALUES('programming for all', current_timestamp, current_timestamp);
INSERT INTO course (title, created_at, updated_at) VALUES('pike fishing', current_timestamp, current_timestamp);
INSERT INTO course (title, created_at, updated_at) VALUES('painting', current_timestamp, current_timestamp);

CREATE TABLE student_course (
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    rating INTEGER,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT student_course_pk PRIMARY KEY (student_id, course_id),
    CONSTRAINT student_fk FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT course_fk FOREIGN KEY (course_id) REFERENCES course(id)
);

INSERT INTO student_course (student_id, course_id, rating, created_at, updated_at) VALUES(1, 1, 8, current_timestamp, current_timestamp);
INSERT INTO student_course (student_id, course_id, rating, created_at, updated_at) VALUES(1, 2, 7, current_timestamp, current_timestamp);
INSERT INTO student_course (student_id, course_id, rating, created_at, updated_at) VALUES(2, 3, 9, current_timestamp, current_timestamp);
INSERT INTO student_course (student_id, course_id, rating, created_at, updated_at) VALUES(3, 3, 9, current_timestamp, current_timestamp);
