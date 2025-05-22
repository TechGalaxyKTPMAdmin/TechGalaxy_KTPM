# === Build Stage ===
FROM eclipse-temurin:17 AS build
WORKDIR /app

# 1. Copy pom.xml để cache dependencies
COPY ./InventoryService/pom.xml ./InventoryService/pom.xml
COPY ./InventoryService/mvnw ./InventoryService/mvnw
COPY ./InventoryService/.mvn ./InventoryService/.mvn

RUN chmod +x ./InventoryService/mvnw

RUN ./InventoryService/mvnw -f ./InventoryService/pom.xml dependency:go-offline

# 2. Copy toàn bộ source code sau
COPY ./InventoryService ./InventoryService

# 3. Build ứng dụng
WORKDIR /app/InventoryService
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy jar đã build vào image runtime
COPY --from=build /app/InventoryService/target/*.jar InventoryService.jar

EXPOSE 9006
ENTRYPOINT ["java", "-jar", "InventoryService.jar"]
