-- ========================================================
-- Car Rental Database Setup (MySQL)
-- ========================================================

-- 1. Create Database
CREATE DATABASE IF NOT EXISTS car_rental
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE car_rental;

-- 2. Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15),
    password_hash VARCHAR(255) NOT NULL,  -- store hashed password
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Cars Table
CREATE TABLE IF NOT EXISTS cars (
    car_id INT AUTO_INCREMENT PRIMARY KEY,
    car_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) DEFAULT 'Available',
    price_per_day DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Rentals Table
CREATE TABLE IF NOT EXISTS rentals (
    rental_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    car_id INT NOT NULL,
    rent_date DATE NOT NULL,
    return_date DATE NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (car_id) REFERENCES cars(car_id)
);

-- ========================================================
-- Insert Sample Data
-- ========================================================

-- Sample Users
INSERT INTO users (username, email, phone, password_hash) VALUES
('ganesh', 'ganesh@example.com', '9876543210', '1234'),
('karthik y m', 'karthik@example.com', '9123456789', '1234');

-- Sample Cars
INSERT INTO cars (car_name, status, price_per_day) VALUES
('BMW X5', 'Available', 2500.00),
('Nissan Altima', 'Available', 1800.00),
('TVS Apache', 'Available', 900.00);

-- Sample Rental (Ganesh rents BMW)
INSERT INTO rentals (user_id, car_id, rent_date, return_date, price)
VALUES (1, 1, '2025-08-20', '2025-08-25', 12500.00);

-- ========================================================
-- Check Data
-- ========================================================
SELECT * FROM users;
SELECT * FROM cars;
SELECT * FROM rentals;
