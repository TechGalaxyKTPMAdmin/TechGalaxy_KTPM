# === Build Stage ===
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# 1. Copy pom.xml để cache dependencies
COPY ./ConfigServer/pom.xml ./ConfigServer/pom.xml
RUN mvn -f ./ConfigServer/pom.xml dependency:go-offline

# 2. Copy toàn bộ source code
COPY ./ConfigServer ./ConfigServer

# 3. Build ứng dụng
WORKDIR /app/ConfigServer
RUN mvn clean package -DskipTests

# === Runtime Stage ===
FROM openjdk:17-jdk-slim
WORKDIR /app
# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy JAR đã build sang image runtime
COPY --from=build /app/ConfigServer/target/*.jar ConfigServer.jar

EXPOSE 8888
ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]
