-- init_scripts/init.sql
CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255),
    phone VARCHAR(20),
    password VARCHAR(255),
    ip VARCHAR(45),
    name VARCHAR(100),
    last_name VARCHAR(100),
    PRIMARY KEY (id)
);

INSERT INTO user (email, phone, password, ip, name, last_name)
VALUES ('ahmed@example.com', '123-456-7890', 'hashed_password_here', '192.168.1.1', 'Ahmed', 'Ali');