CREATE TABLE IF NOT EXISTS characters (
    name TEXT NOT NULL COLLATE NOCASE PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS snapshots (
    character_name   TEXT    NOT NULL COLLATE NOCASE,
    version          INTEGER NOT NULL,
    shot_at          TEXT    NOT NULL,
    action           TEXT    NOT NULL,
    race_name        TEXT    NOT NULL,
    profession_name  TEXT    NOT NULL,
    invested_points  TEXT    NOT NULL,
    PRIMARY KEY (character_name, version),
    FOREIGN KEY (character_name) REFERENCES characters(name)
);