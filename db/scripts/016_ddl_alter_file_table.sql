ALTER TABLE file ADD COLUMN auto_post_id int REFERENCES auto_post(id) NOT NULL;
