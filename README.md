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
1. This application could be a part of microservice architecture, but for the solution a less time-consuming implementation is preferred
2. Regarding time limitations and minimal job state requirements - runnable dedicated jobs were considered as a better alternative
2. If multiple instances of this application are provided - sticky sessions could be used to route appropriate requests to the required instances
3. Virtual threads could be used to maximize cpu utilization and reduce IO thread blocking
4. Fail-fast policy is being considered to be default 
5. HTTP calls could provide short retry policy to avoid external service blinking
6. Kafka failures could be also provide fail-fast behaviour

### Documentation of any AI-assisted parts (what was generated, how you verified/improved it)
1. Asked DeepL and ChatGPT about Kafka configuration issues but had no appropriate answer nevertheless
2. Discussed system design concerns with DeepL and Chat GPT to pick a right technology for workers from candidates: Spring Quartz, Spring Batch, Runnable dedicated jobs
3. Minor framework and code issues


