# Use the official OpenJDK base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/tms-0.0.1-SNAPSHOT.jar tms_app.jar

# Expose port 9090
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "tms_app.jar"]