CREATE TABLE juridic_profiles (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    legal_name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    state_registration VARCHAR(20),
    trade_name VARCHAR(255),
    activity_sector VARCHAR(100),
    commercial_address VARCHAR(255),
    commercial_phone VARCHAR(20),
    website VARCHAR(255),
    commercial_email VARCHAR(255),
    company_description VARCHAR(255),
    company_logo VARCHAR(255),
    legal_representative_contact_name VARCHAR(255),
    legal_representative_contact_email VARCHAR(255),
    legal_representative_contact_phone VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT juridic_profiles_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);