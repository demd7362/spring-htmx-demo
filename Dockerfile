FROM openjdk:17-jdk-alpine

COPY build/libs/*.jar app.jar

COPY config.json etc/config.json

ENV SPRING_PROFILES_ACTIVE=dev

ENTRYPOINT ["java", "-jar", "app.jar"]
