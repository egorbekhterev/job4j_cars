CREATE TABLE participates
(
    id SERIAL PRIMARY KEY,
    user_id int NOT NULL REFERENCES auto_user(id),
    post_id int NOT NULL REFERENCES auto_post(id)
);
