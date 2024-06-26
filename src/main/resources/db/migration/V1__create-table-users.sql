CREATE TABLE users (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_email_validated BOOLEAN DEFAULT FALSE,
    role VARCHAR(255) NOT NULL CHECK (role IN ('ADMIN', 'USER', 'SELLER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);