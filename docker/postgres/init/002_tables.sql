CREATE TABLE users
(
    userId        uuid PRIMARY KEY,
    user_name     VARCHAR(100) UNIQUE NOT NULL,
    email         TEXT UNIQUE  NOT NULL,
    password_hash TEXT NOT NULL,
    user_status   user_status  NOT NULL,
    user_role     user_role NOT NULL,
    created_at    TIMESTAMPTZ  NOT NULL,
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT now()
);
CREATE TABLE users_for_test
(
    userId        uuid PRIMARY KEY,
    user_name     VARCHAR(100) UNIQUE NOT NULL,
    email         TEXT UNIQUE  NOT NULL,
    password_hash TEXT NOT NULL,
    user_status   user_status  NOT NULL,
    user_role     user_role NOT NULL,
    created_at    TIMESTAMPTZ  NOT NULL,
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT now()
);