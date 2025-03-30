# === Build Stage ===
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# 1. Copy pom.xml để cache dependencies
COPY ./InventoryService/pom.xml ./InventoryService/pom.xml
RUN mvn -f ./InventoryService/pom.xml dependency:go-offline

# 2. Copy toàn bộ source code sau
COPY ./InventoryService ./InventoryService

# 3. Build ứng dụng
WORKDIR /app/InventoryService
RUN mvn clean package -DskipTests

# === Runtime Stage ===
FROM openjdk:17-jdk-slim
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy jar đã build vào image runtime
COPY --from=build /app/InventoryService/target/*.jar InventoryService.jar

EXPOSE 9006
ENTRYPOINT ["java", "-jar", "InventoryService.jar"]
