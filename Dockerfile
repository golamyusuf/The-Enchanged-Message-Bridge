# Use an official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file to the container and rename it
COPY target/The-Enchanted-Message-Bridge-0.0.1-SNAPSHOT.jar /app/the-enchanted-message-bridge.jar

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Set the command to run the jar file
ENTRYPOINT ["java", "-jar", "/app/the-enchanted-message-bridge.jar"]
