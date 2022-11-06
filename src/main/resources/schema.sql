
CREATE TABLE IF NOT EXISTS subdivision
(
    subdivision_id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    subdivision_name        VARCHAR (255)                              NOT NULL,
    subdivision_full_name   VARCHAR (255)                              NOT NULL,
    UNIQUE (subdivision_name)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_first_name VARCHAR(255)                               NOT NULL,
    user_last_name  VARCHAR(255)                               NOT NULL,
    email           VARCHAR(512)                               NOT NULL,
    login           VARCHAR(255)                                       ,
    subdivision_id  INTEGER REFERENCES subdivision (subdivision_id)
);

CREATE TABLE IF NOT EXISTS weekly_report
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id         INTEGER REFERENCES users (user_id)                 ,
    subdivision_id  INTEGER REFERENCES subdivision (subdivision_id)    ,
    creation_time   TIMESTAMP                                  NOT NULL,
    period_start    TIMESTAMP                                  NOT NULL,
    period_end      TIMESTAMP                                  NOT NULL,
    gas_consumption INTEGER                                    NOT NULL,
    gas_distance    INTEGER                                    NOT NULL,
    dt_consumption  INTEGER                                    NOT NULL,
    dt_distance     INTEGER                                    NOT NULL
);

CREATE TABLE IF NOT EXISTS plan_gas_consumption
(
    id                   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id              INTEGER REFERENCES users (user_id)                 ,
    subdivision_id       INTEGER REFERENCES subdivision (subdivision_id)    ,
    creation_time        TIMESTAMP                                  NOT NULL,
    period_start         TIMESTAMP                                  NOT NULL,
    period_end           TIMESTAMP                                  NOT NULL,
    plan_consumption_gas INTEGER                                    NOT NULL,
    plan_distance_gas    INTEGER                                    NOT NULL,
    plan_consumption_dt  INTEGER                                    NOT NULL,
    plan_distance_dt     INTEGER                                    NOT NULL
);


-- CREATE TABLE IF NOT EXISTS requests
-- (
--     request_id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--     user_id       BIGINT                                  NOT NULL,
--     description   VARCHAR(1024)                           NOT NULL,
--     creation_time TIMESTAMP                               NOT NULL,
--     CONSTRAINT pk_request PRIMARY KEY (request_id),
--     CONSTRAINT fk_request_on_user FOREIGN KEY (user_id) REFERENCES users (user_id)
-- );
--
-- CREATE TABLE IF NOT EXISTS items
-- (
--     item_id     BIGINT GENERATED BY DEFAULT AS IDENTITY             NOT NULL,
--     owner_id    BIGINT REFERENCES users (user_id) ON DELETE CASCADE NOT NULL,
--     item_name   VARCHAR(255)                                        NOT NULL,
--     description VARCHAR(1024),
--     available   BOOLEAN                                             NOT NULL,
--     request_id  BIGINT,
--     CONSTRAINT pk_item PRIMARY KEY (item_id),
--     CONSTRAINT fk_item_on_request FOREIGN KEY (request_id) REFERENCES requests (request_id)
-- );
--
-- CREATE TABLE IF NOT EXISTS bookings
-- (
--     booking_id    BIGINT GENERATED BY DEFAULT AS IDENTITY             NOT NULL,
--     booker_id     BIGINT REFERENCES users (user_id) ON DELETE CASCADE NOT NULL,
--     item_id       BIGINT REFERENCES items (item_id) ON DELETE CASCADE NOT NULL,
--     booking_start TIMESTAMP                                           NOT NULL,
--     booking_end   TIMESTAMP                                           NOT NULL,
--     state         VARCHAR(15)                                         NOT NULL,
--     CONSTRAINT pk_booking PRIMARY KEY (booking_id)
-- );
--
-- CREATE TABLE IF NOT EXISTS comments
-- (
--     comment_id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--     comment_text VARCHAR(4000)                           NOT NULL,
--     item_id      BIGINT,
--     author_id    BIGINT,
--     created      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
--     CONSTRAINT pk_comment PRIMARY KEY (comment_id),
--     CONSTRAINT FK_COMMENT_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (user_id),
--     CONSTRAINT FK_COMMENT_ON_ITEM FOREIGN KEY (item_id) REFERENCES items (item_id)
-- );



