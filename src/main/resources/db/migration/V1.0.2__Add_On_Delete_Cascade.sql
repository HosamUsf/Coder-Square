-- Modify the votes table
ALTER TABLE votes
    DROP CONSTRAINT votes_user_id_fkey;

ALTER TABLE votes
    ADD CONSTRAINT votes_user_id_fkey
        FOREIGN KEY (user_id) REFERENCES users (user_id)
            ON DELETE CASCADE;

ALTER TABLE votes
    DROP CONSTRAINT votes_post_id_fkey;

ALTER TABLE votes
    ADD CONSTRAINT votes_post_id_fkey
        FOREIGN KEY (post_id) REFERENCES posts (post_id)
            ON DELETE CASCADE;

-- Modify the likes table
ALTER TABLE likes
    DROP CONSTRAINT likes_user_id_fkey;

ALTER TABLE likes
    ADD CONSTRAINT likes_user_id_fkey
        FOREIGN KEY (user_id) REFERENCES users (user_id)
            ON DELETE CASCADE;

ALTER TABLE likes
    DROP CONSTRAINT likes_post_id_fkey;

ALTER TABLE likes
    ADD CONSTRAINT likes_post_id_fkey
        FOREIGN KEY (post_id) REFERENCES posts (post_id)
            ON DELETE CASCADE;
