FROM openjdk:8-alpine

ADD ./target/auth-service.jar /app/auth-service.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/app/auth-service.jar"]

EXPOSE 4000