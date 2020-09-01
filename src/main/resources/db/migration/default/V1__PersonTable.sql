CREATE EXTENSION "uuid-ossp";

CREATE TABLE person (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(64) NOT NULL
);

INSERT INTO person (id, name) VALUES(uuid_generate_v1mc(), 'maria');
INSERT INTO person (id, name) VALUES(uuid_generate_v1mc(), 'terence');
INSERT INTO person (id, name) VALUES(uuid_generate_v1mc(), 'gians');

