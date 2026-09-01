# Build stage
FROM gradle:8.5-jdk21 as builder

WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src
COPY gradle ./gradle
COPY gradlew gradlew.bat ./

RUN chmod +x ./gradlew && ./gradlew clean bootJar

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the JAR from builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port (Render will set this via PORT env var)
EXPOSE 8081

# Run the application
CMD ["java", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]