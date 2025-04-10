CREATE TABLE IF NOT EXISTS hello (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

INSERT INTO hello (name) VALUES ('Welcome from init.sql');
