ALTER TABLE orders
ALTER COLUMN created_at TYPE timestamp USING created_at::timestamp without time zone,
ALTER COLUMN updated_at TYPE timestamp USING updated_at::timestamp without time zone;