CREATE TABLE student (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    insert_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    update_timestamp TIMESTAMP WITH TIME ZONE
);

CREATE TABLE course (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    insert_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    update_timestamp TIMESTAMP WITH TIME ZONE
);

CREATE TABLE student_course (
    student_id BIGINT NOT NULL REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id BIGINT NOT NULL REFERENCES course (id) ON UPDATE CASCADE ON DELETE CASCADE,
    rating BIGINT,
    insert_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    update_timestamp TIMESTAMP WITH TIME ZONE,

    CONSTRAINT student_course_pkey PRIMARY KEY (student_id, course_id)
);