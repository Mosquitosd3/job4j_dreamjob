CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE users (
                           id SERIAL PRIMARY KEY,
                           name varchar(255),
                           email varchar(255),
                           password varchar(255)
);