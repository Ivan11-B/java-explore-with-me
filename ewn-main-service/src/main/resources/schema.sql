DROP TABLE IF EXISTS participations;
DROP TABLE IF EXISTS compilation_events;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS compilations;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    email varchar(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS compilations (
    id SERIAL PRIMARY KEY,
    title varchar(50) NOT NULL UNIQUE,
    pinned BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS events (
    id SERIAL PRIMARY KEY,
    annotation varchar(2000) NOT NULL,
    category_id INTEGER REFERENCES categories(id) ON DELETE CASCADE,
    created_on TIMESTAMP,
    description varchar(7000),
    event_date TIMESTAMP NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    lat DOUBLE PRECISION,
    lon DOUBLE PRECISION,
    paid BOOLEAN,
    participant_limit INTEGER,
    published_on TIMESTAMP,
    request_moderation BOOLEAN,
    title varchar(255) NOT NULL,
    state varchar(255),
    confirmed_request INTEGER,
    view INTEGER
);

CREATE TABLE IF NOT EXISTS compilation_events (
    compilation_id INTEGER NOT NULL REFERENCES compilations(id) ON DELETE CASCADE,
    event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    PRIMARY KEY (compilation_id, event_id)
);

CREATE TABLE IF NOT EXISTS participations (
    id SERIAL PRIMARY KEY,
    event_id INTEGER NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    created TIMESTAMP,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status varchar(255)
);