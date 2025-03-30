# === Build Stage ===
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
<<<<<<< Updated upstream
COPY ./DiscoveryServer ./DiscoveryServer
=======

# 1. Copy pom.xml trước để cache dependencies
COPY ./DiscoveryServer/pom.xml ./DiscoveryServer/pom.xml
RUN mvn -f ./DiscoveryServer/pom.xml dependency:go-offline

# 2. Copy toàn bộ source code
COPY ./DiscoveryServer ./DiscoveryServer

# 3. Build project
>>>>>>> Stashed changes
WORKDIR /app/DiscoveryServer
RUN mvn clean package -DskipTests

# === Runtime Stage ===
FROM openjdk:17-jdk-slim
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy jar file từ stage build
COPY --from=build /app/DiscoveryServer/target/*.jar DiscoveryServer.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "DiscoveryServer.jar"]