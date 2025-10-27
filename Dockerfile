FROM eclipse-temurin:24-jdk-alpine-3.21
WORKDIR /app
COPY ../target/score-provisioner-*-SNAPSHOT.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]