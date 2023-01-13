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
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(64) NOT NULL,
    book_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);

INSERT INTO book (title, book_timestamp) VALUES('The name of the rose', current_timestamp);
INSERT INTO book (title, book_timestamp) VALUES('The big Gatsby', current_timestamp);
INSERT INTO book (title, book_timestamp) VALUES('Atlas Obscura', current_timestamp);
INSERT INTO book (title, book_timestamp) VALUES('Big bomb', current_timestamp);
INSERT INTO book (title, book_timestamp) VALUES('Fishing time', current_timestamp);

CREATE TABLE review (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(512) NOT NULL,
    review_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    book_id BIGINT NOT NULL,

    FOREIGN KEY (book_id) REFERENCES book (id)
);

CREATE TABLE book_detail (
    id BIGINT PRIMARY KEY,
    pages INTEGER NOT NULL,
    isbn VARCHAR(64),
    book_detail_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,

    FOREIGN KEY (id) REFERENCES book (id)
);

CREATE TABLE author (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    author_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE book_author (
    book_id BIGINT NOT NULL REFERENCES book (id) ON UPDATE CASCADE ON DELETE CASCADE,
    author_id BIGINT NOT NULL REFERENCES author (id) ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT book_author_pkey PRIMARY KEY (book_id, author_id)
);

CREATE TABLE student (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    insert_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    update_timestamp TIMESTAMP WITH TIME ZONE
);

INSERT INTO student (name, insert_timestamp, update_timestamp) VALUES('Damian', current_timestamp, current_timestamp);
INSERT INTO student (name, insert_timestamp, update_timestamp) VALUES('Sante', current_timestamp, current_timestamp);
INSERT INTO student (name, insert_timestamp, update_timestamp) VALUES('Peter', current_timestamp, current_timestamp);


CREATE TABLE course (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    insert_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    update_timestamp TIMESTAMP WITH TIME ZONE
);

INSERT INTO course (title, insert_timestamp, update_timestamp) VALUES('programming for all', current_timestamp, current_timestamp);
INSERT INTO course (title, insert_timestamp, update_timestamp) VALUES('pike fishing', current_timestamp, current_timestamp);
INSERT INTO course (title, insert_timestamp, update_timestamp) VALUES('painting', current_timestamp, current_timestamp);

CREATE TABLE student_course (
    student_id BIGINT NOT NULL REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id BIGINT NOT NULL REFERENCES course (id) ON UPDATE CASCADE ON DELETE CASCADE,
    rating BIGINT,
    insert_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    update_timestamp TIMESTAMP WITH TIME ZONE,

    CONSTRAINT student_course_pkey PRIMARY KEY (student_id, course_id)
);

INSERT INTO student_course (student_id, course_id, rating, insert_timestamp, update_timestamp) VALUES(1, 1, 8, current_timestamp, current_timestamp);
INSERT INTO student_course (student_id, course_id, rating, insert_timestamp, update_timestamp) VALUES(1, 2, 7, current_timestamp, current_timestamp);
INSERT INTO student_course (student_id, course_id, rating, insert_timestamp, update_timestamp) VALUES(2, 3, 9, current_timestamp, current_timestamp);
INSERT INTO student_course (student_id, course_id, rating, insert_timestamp, update_timestamp) VALUES(3, 3, 9, current_timestamp, current_timestamp);
