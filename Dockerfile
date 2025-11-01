FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/score-provisioner-*-SNAPSHOT.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]