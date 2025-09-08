-- Users table
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  phone VARCHAR(15) NOT NULL,
  password_hash VARCHAR(255) NOT NULL
);

-- Insert users
INSERT INTO users (username, email, phone, password) VALUES
('ganesh', 'ganesh@example.com', '9876543210', '1234'),
('karthik y m', 'karthik@example.com', '9123456789', '1234');

-- Login activity table
CREATE TABLE logins(
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  login_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);
