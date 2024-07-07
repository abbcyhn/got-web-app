DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'relation_type') THEN
        CREATE TYPE relation_type AS ENUM ('PARENT', 'SIBLING', 'MARRIED_ENGAGED');
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'action_type') THEN
        CREATE TYPE action_type AS ENUM ('ABDUCTED', 'KILLED', 'SERVED', 'GUARDED');
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'house_name') THEN
        CREATE TYPE house_name AS ENUM ('TARGARYEN', 'GREYJOY', 'LANNISTER', 'STARK', 'BARATHEON', 'FREY', 'TARLY', 'TULLY', 'MARTELL', 'MORMONT', 'TYRELL', 'ARRYN', 'UMBER', 'BOLTON');
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS characters (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nickname VARCHAR(100),
    link TEXT,
    royal BOOLEAN DEFAULT FALSE,
    kingsguard BOOLEAN DEFAULT FALSE,
    image_full TEXT,
    image_thumb TEXT
);

CREATE TABLE IF NOT EXISTS relationships (
    id SERIAL PRIMARY KEY,
    character_id INT REFERENCES characters(id) ON DELETE CASCADE,
    relation_to INT REFERENCES characters(id),
    relation_type relation_type,
    UNIQUE (character_id, relation_to, relation_type)
);

CREATE TABLE IF NOT EXISTS actions (
    id SERIAL PRIMARY KEY,
    character_id INT REFERENCES characters(id) ON DELETE CASCADE,
    action_to INT REFERENCES characters(id),
    action_type action_type
);

CREATE TABLE IF NOT EXISTS allies (
    id SERIAL PRIMARY KEY,
    character_id INT REFERENCES characters(id) ON DELETE CASCADE,
    ally_id INT REFERENCES characters(id),
    UNIQUE (character_id, ally_id)
);

CREATE TABLE IF NOT EXISTS actors (
    id SERIAL PRIMARY KEY,
    character_id INT REFERENCES characters(id) ON DELETE SET NULL,
    name VARCHAR(100),
    link TEXT
);

CREATE TABLE IF NOT EXISTS active_seasons (
    id SERIAL PRIMARY KEY,
    actor_id INT REFERENCES actors(id) ON DELETE CASCADE,
    season_number INT,
    UNIQUE (actor_id, season_number)
);


CREATE TABLE IF NOT EXISTS houses (
    id SERIAL PRIMARY KEY,
    character_id INT REFERENCES characters(id) ON DELETE CASCADE,
    name house_name
);
