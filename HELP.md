# Getting Started

1. Build project by using maven wrapper by command:
`.\mvnw clean install`
2. Launch docker stack using docker compose command:
`docker-compose up -d`
3. Open application swagger at url:
`http://localhost:8080/swagger-ui/index.html#/event-controller/eventStatus`
4. Use `/api/v1/event/status` handler providing `LIVE` / `NOT_LIVE` status to launch provisioning


