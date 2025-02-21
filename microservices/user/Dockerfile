FROM eclipse-temurin:23.0.2_7-jre-alpine

EXPOSE 8080

RUN addgroup -S app && adduser -S app -G app

USER app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]