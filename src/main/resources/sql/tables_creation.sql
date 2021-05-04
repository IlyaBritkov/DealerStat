CREATE TABLE IF NOT EXISTS "user"
(
    id         integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name varchar(30)              NOT NULL,
    last_name  varchar(35)              NOT NULL,
    email      varchar(35)              NOT NULL UNIQUE,
    password   varchar                  NOT NULL,
    created_at timestamp with time zone NOT NULL,
    role       user_role_enum           NOT NULL,
    approved   boolean
);

CREATE TABLE IF NOT EXISTS game
(
    id          integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name        varchar(30)              NOT NULL,
    description varchar,
    created_at  timestamp with time zone NOT NULL,
    UNIQUE (name, description)
);

CREATE TABLE IF NOT EXISTS user_game
(
    id      integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id integer,
    game_id integer,
    CONSTRAINT user_game_unique UNIQUE (user_id,game_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES game (id)
);

CREATE TABLE IF NOT EXISTS feedback
(
    id         integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    message    varchar,
    rating     rating_enum              NOT NULL,
    trader_id  integer,
    game_id    integer,
    created_at timestamp with time zone NOT NULL,
    approved   boolean,
    CONSTRAINT fk_trader FOREIGN KEY (trader_id) REFERENCES "user" (id),
    CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES game (id)
);
