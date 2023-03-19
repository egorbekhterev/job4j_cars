create table history_owner
(
    id serial primary key,
    car_id int REFERENCES car(id) NOT NULL,
    driver_id int REFERENCES driver(id),
    startAt TIMESTAMP NOT NULL,
    endAt TIMESTAMP NOT NULL
);
