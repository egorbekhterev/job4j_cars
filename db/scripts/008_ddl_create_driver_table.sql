create table driver
(
    id serial primary key,
    name TEXT NOT NULL,
    user_id int REFERENCES auto_user(id) UNIQUE NOT NULL
);
