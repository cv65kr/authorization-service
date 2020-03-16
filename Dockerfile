FROM openjdk:8-alpine

ADD ./target/auth-1.0.0.jar /app/auth-service.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/app/auth-service.jar"]

EXPOSE 4000