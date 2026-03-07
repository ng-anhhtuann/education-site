# education-site
E-Learning Web Initiative

## Run Locally With Docker Compose

From the repository root:

1. Ensure Firebase key exists at `eduhub-server/eduhub-key.json`.
2. Start everything:

```bash
docker compose up --build
```

Services:
- Client: `http://localhost:3000`
- Server API: `http://localhost:8080`
- MongoDB: `mongodb://localhost:27017`

Stop all services:

```bash
docker compose down
```

To also remove MongoDB data volume:

```bash
docker compose down -v
```
