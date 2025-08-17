CREATE DATABASE jackpot;

USE jackpot_users;

CREATE TABLE IF NOT EXISTS roles(
    role_id DECIMAL primary key AUTO_INCREMENT,
    name    VARCHAR(25)
)

CREATE TABLE IF NOT EXISTS users(
    user_id  DECIMAL primary key AUTO_INCREMENT,
    username VARCHAR(50)   NOT NULL UNIQUE,
    password VARCHAR(1000) NOT NULL,
    roles    VARCHAR(25)
)

CREATE TABLE IF NOT EXISTS customers(
    customer_id    DECIMAL primary key AUTO_INCREMENT,
    name           VARCHAR(50) NOT NULL,
    email          VARCHAR(50) NOT NULL UNIQUE,
    age            INT
)