# Use an OpenJDK base image
FROM openjdk:21-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/Project-1.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8089

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
