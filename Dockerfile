ARG JAVA_VERSION
FROM openjdk:${JAVA_VERSION}-alpine

ADD ./target/auth-1.0.0.jar /app/auth-service.jar

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://0.0.0.0:4000/uaa/actuator/health || exit 1

ENTRYPOINT ["java", "-Xmx200m", "-jar", "-Dspring.profiles.active=prod", "/app/auth-service.jar"]

EXPOSE 4000