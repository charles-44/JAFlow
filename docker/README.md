## 🐳 Docker Compose – Local Development Stack

This `docker-compose.yml` file provides a full local environment for developing and testing the JAFlow workflow engine. It includes the following services:

### 🔹 Services

- **PostgreSQL (v15)**  
  Provides a relational database used by the Spring Boot backend and Keycloak.

- **Keycloak (v26.0.0)**  
  Offers identity and access management for the JAFlow platform.

### 🔹 Setup Details

- **Postgres Initialization**  
  At startup, Postgres runs the script located at:  
  `./postgres/init/create_users_databases.sql`  
  This script creates the required schemas and databases for both Keycloak and the workflow engine.

- **Keycloak Realm Import**  
  Keycloak is launched with the `--import-realm` option. It automatically imports a realm from:  
  `./keycloak/realms/`  
  The realm included comes from the Keycloak Angular example project:  
  👉 [mauriciovigolo/keycloak-angular – standalone example](https://github.com/mauriciovigolo/keycloak-angular/tree/main/projects/examples/standalone)

### 🔹 Usage

Start the environment:

```bash
docker compose up
```

Stop the environment:

```bash
docker compose down
```

Access Keycloak at: [http://localhost:8080](http://localhost:8080)  
Login with:  
- Username: `admin`  
- Password: `admin`

Postgres runs on port `5432` with default credentials:
- Username: `postgres`
- Password: `password`

### 🔹 Volumes & Data

- Persistent volume: `postgres-db-data`
- Postgres init scripts: `./postgres/init/`
- Realm JSON files: `./keycloak/realms/`
