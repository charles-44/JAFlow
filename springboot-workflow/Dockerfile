FROM eclipse-temurin:21-jdk AS dev

WORKDIR /app

# Installer Maven wrapper et dépendances en cache
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# Copier tout sauf target/ pour accélérer le build
COPY src src

# Activer DevTools dans le JAR et debug à chaud
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.fork=false"]
