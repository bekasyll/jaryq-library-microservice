CREATE TABLE IF NOT EXISTS books
(
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    author           VARCHAR(255) NOT NULL,
    genre            VARCHAR(30)  NULL,
    isbn             VARCHAR(13)  NOT NULL UNIQUE,
    total_copies     INTEGER      NOT NULL,
    available_copies INTEGER      NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    created_by       VARCHAR(50)  NOT NULL,
    updated_at       TIMESTAMP,
    updated_by       VARCHAR(50)
);