CREATE TABLE users (
    id INT PRIMARY KEY,
    balance DECIMAL(10,2) CHECK (balance >= 0)
);
