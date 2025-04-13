FROM eclipse-temurin:21-jdk AS dev

WORKDIR /app

# copy jar file to container
COPY target/springboot-container-1.0-SNAPSHOT.jar ./


# launch application
CMD ["java", "-jar", "/app/springboot-container-1.0-SNAPSHOT.jar"]
