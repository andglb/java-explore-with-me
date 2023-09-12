DROP TABLE IF EXISTS HITS;


CREATE TABLE IF NOT EXISTS HITS
(
    ID        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    APP       VARCHAR(32)                             NOT NULL,
    URI       VARCHAR(128)                            NOT NULL,
    IP        VARCHAR(16)                             NOT NULL,
    TIMESTAMP TIMESTAMP WITHOUT TIME ZONE             NOT NULL
);
