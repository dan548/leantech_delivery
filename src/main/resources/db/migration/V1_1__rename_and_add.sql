CREATE TABLE user_roles(
    user_id BIGINT REFERENCES users(id),
    role_id BIGINT REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE orders RENAME COLUMN delivery_guy_id TO courier_id;
ALTER TABLE orders RENAME COLUMN client_id TO customer_id;
ALTER TABLE orders RENAME COLUMN payment TO payment_type;
ALTER TABLE orders ADD COLUMN created_at TEXT;
ALTER TABLE orders ADD COLUMN updated_at TEXT;

ALTER TABLE positions RENAME TO order_items;

CREATE TABLE payment_types(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE statuses(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

ALTER TABLE order_items DROP COLUMN price;

INSERT INTO payment_types(name) VALUES ('cash');
INSERT INTO payment_types(name) VALUES ('card');
INSERT INTO statuses(name) VALUES ('created');
INSERT INTO statuses(name) VALUES ('delivery');
INSERT INTO statuses(name) VALUES ('done');
