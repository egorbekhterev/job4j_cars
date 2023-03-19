ALTER TABLE car ADD COLUMN brand_id int REFERENCES brand(id) NOT NULL;
ALTER TABLE car ADD COLUMN body_type_id int REFERENCES body_type(id) NOT NULL;
