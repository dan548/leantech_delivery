INSERT INTO roles(name) VALUES ('ROLE_ADMIN');
INSERT INTO roles(name) VALUES ('ROLE_DELIVERY');
INSERT INTO roles(name) VALUES ('ROLE_CLIENT');

DROP TABLE IF EXISTS payment_types;
DROP TABLE IF EXISTS statuses;

CREATE TABLE payment_types(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE statuses(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

INSERT INTO payment_types(name) VALUES ('cash');
INSERT INTO payment_types(name) VALUES ('card');

INSERT INTO statuses(name) VALUES ('created');
INSERT INTO statuses(name) VALUES ('delivery');
INSERT INTO statuses(name) VALUES ('done');