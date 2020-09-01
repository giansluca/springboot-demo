CREATE TABLE student (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    insert_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    update_timestamp TIMESTAMP WITH TIME ZONE
);

INSERT INTO student (name, insert_timestamp) VALUES ('Gus Martin', current_timestamp);
INSERT INTO student (name, insert_timestamp) VALUES ('Baba Jaffa', current_timestamp);
INSERT INTO student (name, insert_timestamp) VALUES ('Terence Storm', current_timestamp);

CREATE TABLE course (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    insert_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    update_timestamp TIMESTAMP WITH TIME ZONE
);

INSERT INTO course (title, insert_timestamp) VALUES ('Algorithms and data structures', current_timestamp);
INSERT INTO course (title, insert_timestamp) VALUES ('Java 1', current_timestamp);
INSERT INTO course (title, insert_timestamp) VALUES ('Front end development', current_timestamp);

CREATE TABLE student_course (
    student_id BIGINT NOT NULL REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id BIGINT NOT NULL REFERENCES course (id) ON UPDATE CASCADE ON DELETE CASCADE,
    rating BIGINT,
    insert_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    update_timestamp TIMESTAMP WITH TIME ZONE,

    CONSTRAINT student_course_pkey PRIMARY KEY (student_id, course_id)
);