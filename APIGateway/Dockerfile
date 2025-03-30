# === Build Stage ===
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# 1. Copy pom.xml để Maven tải dependencies (tận dụng cache)
COPY ./APIGateway/pom.xml ./APIGateway/pom.xml
RUN mvn -f ./APIGateway/pom.xml dependency:go-offline

# 2. Copy toàn bộ mã nguồn sau
COPY ./APIGateway ./APIGateway

# 3. Build project
WORKDIR /app/APIGateway
RUN mvn clean package -DskipTests

# === Runtime Stage ===
FROM openjdk:17-jdk-slim
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy jar từ stage build
COPY --from=build /app/APIGateway/target/*.jar APIGateway.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "APIGateway.jar"]
