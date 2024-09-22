# Use an official Maven image to build the app
FROM maven:3.8.1-jdk-11 AS build
WORKDIR /app

# Copy the pom.xml file and download project dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and package the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use an official Java runtime image to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (or whatever your app uses)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
