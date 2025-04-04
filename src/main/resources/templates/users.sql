CREATE DATABASE jackpot_users;

USE jackpot_users;

CREATE TABLE users (
    id DECIMAL primary key AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    age INT
)