CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    price INTEGER
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    payment TEXT,
    delivery_guy_id BIGSERIAL REFERENCES users,
    status TEXT,
    address TEXT,
    client_id BIGSERIAL REFERENCES users
);

CREATE TABLE positions (
    id BIGSERIAL PRIMARY KEY,
    quantity INTEGER,
    price INTEGER,
    product_id BIGSERIAL REFERENCES products ON DELETE SET NULL,
    order_id BIGSERIAL REFERENCES orders ON DELETE CASCADE
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    password TEXT
);

CREATE TABLE authorities (
    id BIGSERIAL REFERENCES users ON DELETE CASCADE,
    authority TEXT,
    PRIMARY KEY(id, authority)
);