-- liquibase formatted sql

-- changeset yuriy_kolosov:1
CREATE TABLE IF NOT EXISTS users (
  id bigserial PRIMARY KEY,
  email VARCHAR(255) UNIQUE,
  "password" VARCHAR(255) NOT NULL,
  "role" VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks (
  id bigserial PRIMARY KEY,
  header VARCHAR(255) NOT NULL,
  status VARCHAR(255) NOT NULL,
  priority VARCHAR(255) NOT NULL,
  author_id bigint NOT NULL,
  executor_id bigint NOT NULL,
  description VARCHAR(255) NOT NULL,
  CONSTRAINT fk_users_id_author FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE,
  CONSTRAINT fk_users_id_executor FOREIGN KEY (executor_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments (
  id bigserial PRIMARY KEY,
  task_id bigint NOT NULL,
  description VARCHAR(255) NOT NULL,
  CONSTRAINT fk_tasks_id FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE
);
