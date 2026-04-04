-- Table: user_model
CREATE TABLE user_model (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
);

-- Table: category
CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Table: ad_model
CREATE TABLE ad_model (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    price DOUBLE PRECISION,
    location VARCHAR(255),
    category_id BIGINT,
    user_id BIGINT,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user_model(id) ON DELETE SET NULL
);
CREATE OR REPLACE FUNCTION check_user_exists(
    p_email VARCHAR, 
    p_username VARCHAR, 
    p_phone VARCHAR
) 
RETURNS BOOLEAN AS $$
BEGIN
    RETURN EXISTS (
        SELECT 1 FROM user_model 
        WHERE email = p_email 
           OR username = p_username 
          
    );
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_user_by_username_or_email(
    p_identifier VARCHAR
) 
RETURNS SETOF user_model AS $$
BEGIN
    RETURN QUERY 
    SELECT * FROM user_model 
    WHERE email = p_identifier 
       OR username = p_identifier
    LIMIT 1; -- Ensures only one record is returned
END;
$$ LANGUAGE plpgsql;