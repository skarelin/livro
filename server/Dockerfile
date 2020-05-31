FROM openjdk:8-jdk-alpine

VOLUME /tmp

ARG JAR_FILE

COPY execution/server-5.1.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=production","-jar","/app.jar"]