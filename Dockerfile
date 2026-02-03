# Stage 1: Build the application with Maven
FROM openjdk:17-jdk-alpine AS build

WORKDIR /app

# Copy maven wrapper and pom files
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the jar file (skip tests)
RUN ./mvnw package -DskipTests

# Stage 2: Create smaller runtime image
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy the jar from the build stage and rename it for simplicity
COPY --from=build /app/target/first-deploy-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to the outside
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]