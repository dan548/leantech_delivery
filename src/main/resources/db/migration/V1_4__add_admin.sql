WITH userid AS (
    INSERT INTO users(login, password)
        VALUES ('delivery', '$2y$10$HCZbkD4m0VJDN7XFZU50huH0ORP8cIsLVLmfbSdmL8aXMD2ft87Iq')
        RETURNING id
)
INSERT INTO user_roles(user_id, role_id)
    SELECT id, 1 FROM userid;