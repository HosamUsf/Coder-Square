CREATE TABLE votes
(
    vote_id    BIGSERIAL PRIMARY KEY,
    user_id    BIGINT,
    post_id    BIGINT,
    comment_id BIGINT,
    vote_type  VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (post_id) REFERENCES posts (post_id),
    FOREIGN KEY (comment_id) REFERENCES comments (comment_id),

    CHECK (
        (post_id IS NOT NULL AND comment_id IS NULL) OR
        (post_id IS NULL AND comment_id IS NOT NULL)
        )
);