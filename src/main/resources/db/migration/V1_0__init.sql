CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    price INTEGER
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    login TEXT,
    password TEXT,
    role_id BIGINT REFERENCES roles
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    payment TEXT,
    delivery_guy_id BIGINT REFERENCES users,
    status TEXT,
    address TEXT,
    client_id BIGINT REFERENCES users
);

CREATE TABLE positions (
    id BIGSERIAL PRIMARY KEY,
    quantity INTEGER,
    price INTEGER,
    product_id BIGINT REFERENCES products ON DELETE SET NULL,
    order_id BIGINT REFERENCES orders ON DELETE CASCADE
);

INSERT INTO roles(name) VALUES ('ROLE_ADMIN');
INSERT INTO roles(name) VALUES ('ROLE_DELIVERY');
INSERT INTO roles(name) VALUES ('ROLE_CLIENT');