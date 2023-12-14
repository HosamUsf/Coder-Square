CREATE TABLE users
(
    user_id    BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (username),
    UNIQUE (email)
);

CREATE TABLE posts
(
    post_id    BIGSERIAL PRIMARY KEY,
    user_id    BIGINT,
    title      VARCHAR(255) NOT NULL,
    category   VARCHAR(50),
    url        VARCHAR(255),
    points     INT       DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE comments
(
    comment_id BIGSERIAL PRIMARY KEY,
    user_id    BIGINT,
    post_id    BIGINT,
    text       TEXT NOT NULL,
    points     INT       DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (post_id) REFERENCES posts (post_id)
);

CREATE TABLE likes
(
    like_id BIGSERIAL PRIMARY KEY,
    user_id    BIGINT,
    post_id    BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (post_id) REFERENCES posts (post_id)
);
