-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Create people tables
CREATE TABLE IF NOT EXISTS people (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL UNIQUE,
    birth_date DATE NOT NULL,
    identifier_type VARCHAR(2) NOT NULL,
    min_monthly_payment DECIMAL(10, 2) NOT NULL,
    max_loan_amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Create loans tables
CREATE TABLE IF NOT EXISTS loans (
    id SERIAL PRIMARY KEY,
    person_identifier VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    number_of_installments INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL,
    first_payment_date TIMESTAMP,
    approval_date TIMESTAMP,
    signing_date TIMESTAMP,
    delinquency_date TIMESTAMP,
    next_payment_date TIMESTAMP,
    completion_date TIMESTAMP,
    cancellation_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (person_identifier) REFERENCES people(identifier) ON DELETE CASCADE
);

-- Create loansConfig tables
CREATE TABLE IF NOT EXISTS loan_config (
    id SERIAL PRIMARY KEY,
    identifier_type VARCHAR(2) NOT NULL UNIQUE,
    min_monthly_payment DECIMAL(10, 2) NOT NULL,
    max_loan_amount DECIMAL(10, 2) NOT NULL
);

-- Insert in users table
INSERT INTO users (full_name, email, password) VALUES
('João Silva', 'joao.silva@example.com', '$2a$10$xBjjsg8JtPK9kQH2MLyvcO8GlGixXkKJ1LcGFCrg40RNpSm/MqrGe'),
('Maria Oliveira', 'maria.oliveira@example.com', '$2a$10$xBjjsg8JtPK9kQH2MLyvcO8GlGixXkKJ1LcGFCrg40RNpSm/MqrGe');

-- Insert in people table
INSERT INTO people (name, identifier, birth_date, identifier_type, min_monthly_payment, max_loan_amount) VALUES
('Ana Santos', '12345678901', '1990-01-15', 'PF', 300.00, 10000.00), -- PF
('Empresa XYZ', '12345678901234', '2000-05-25', 'PJ', 1000.00, 100000.00), -- PJ
('José Augusto Aluno', '12345678', '1998-08-30', 'EU', 100.00, 10000.00), -- EU
('Carlos Eduardo Aposentado', '1234567890', '1954-12-09', 'AP', 400.00, 25000.00); -- AP

-- Insert in loans_config table
INSERT INTO loan_config (identifier_type, min_monthly_payment, max_loan_amount) VALUES
('PF', 300.00, 10000.00),
('PJ', 1000.00, 100000.00),
('EU', 100.00, 10000.00),
('AP', 400.00, 25000.00);