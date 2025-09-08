CREATE DATABASE IF NOT EXISTS car_rental;

USE car_rental;

CREATE TABLE rental (
    id INT AUTO_INCREMENT PRIMARY KEY,
    car_id VARCHAR(20),
    rent_date DATE,
    return_date DATE,
    price DOUBLE
);
