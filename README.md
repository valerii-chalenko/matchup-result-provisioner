# Getting Started

1. Build project by using maven wrapper by command:
`.\mvnw clean install`
2. Launch docker stack using docker compose command:
`docker-compose up -d`
3. Open application swagger at url:
`http://localhost:8080/swagger-ui/index.html#/event-controller/eventStatus`
4. Use `/api/v1/event/status` handler providing `LIVE` / `NOT_LIVE` status to launch provisioning or process requests from `score-request.http` file
5. For local debug - please change kafka broker host ip within `docker-compose.yaml` file

### How to run any included tests
Test are executed by maven within mentioned above command

### A summary of your design decisions
1. This is a light app implementation, aimed on simple & minimal app demo.
2. Runnable dedicated jobs are considered to be a better alternative at the moment, but for the microservice architecture - some job / batch framework is highly preferred.
3. Current app does not support multiple instances, because caching and jot maintenance is handled locally.
4. Virtual threads (java 25) could be used to maximize cpu utilization and reduce IO thread blocking.
5. Fail-fast policy is being considered to be default to simplify work flow. 
6. HTTP calls and Kafka provide basic retry policy to avoid common problems.