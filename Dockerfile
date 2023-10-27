FROM openjdk:11-jdk-slim-buster
MAINTAINER george kyritsas
COPY target/watering-system-1.0-SNAPSHOT.jar watering-system.jar
ENTRYPOINT ["java", "-jar", "watering-system.jar"]