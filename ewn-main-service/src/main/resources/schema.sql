DROP TABLE IF EXISTS hits;

CREATE TABLE IF NOT EXISTS hits (
    id SERIAL PRIMARY KEY,
    app varchar(255),
    uri varchar(255),
    ip varchar(15),
    created timestamp
    )