FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/finanzapp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_finanzas.jar

EXPOSE 8862

ENTRYPOINT ["java","-jar","app_finanzas.jar"]