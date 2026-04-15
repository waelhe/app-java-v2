# ============================================================================
# Marketplace Platform - Multi-stage Docker Build
# Base Image: Eclipse Temurin 25 (Jakarta EE 11 compatible)
# ============================================================================

# Stage 1: Build
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon -x test

# Stage 2: Runtime
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/marketplace-application/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]