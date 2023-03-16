create table car
(
    id serial primary key,
    name TEXT not null,
    engine_id int REFERENCES engine(id) NOT NULL
);
