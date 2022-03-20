FROM postgres
ENV POSTGRES_PASSWORD docker
ENV POSTGRES_DB softtech

FROM openjdk:11
ARG JAR_FILE=target/softtech-spring-boot-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]