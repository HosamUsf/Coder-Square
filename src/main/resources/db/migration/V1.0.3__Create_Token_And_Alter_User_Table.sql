-- Token Table
CREATE TABLE Tokens
(
    token_id bigserial primary key ,
    confirmation_token    varchar(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expired_at TIMESTAMP ,
    confirmed_at TIMESTAMP ,
    user_id  BIGINT ,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

-- Add locked and enabled columns to the users table
ALTER TABLE users
    ADD COLUMN locked BOOLEAN DEFAULT FALSE,
    ADD COLUMN enabled BOOLEAN DEFAULT TRUE;
