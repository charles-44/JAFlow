-- Create user and database keycloak
CREATE USER keycloak WITH PASSWORD 'password';
CREATE DATABASE keycloak OWNER keycloak;


-- Create user and database workflow
CREATE USER workflow WITH PASSWORD 'workflow';
CREATE DATABASE workflow OWNER workflow;
