# === Build Stage ===
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# 1. Copy pom.xml để cache Maven dependencies
COPY ./ProductService/pom.xml ./ProductService/pom.xml
RUN mvn -f ./ProductService/pom.xml dependency:go-offline

# 2. Copy toàn bộ mã nguồn
COPY ./ProductService ./ProductService

# 3. Build ứng dụng
WORKDIR /app/ProductService
RUN mvn clean package -DskipTests

# === Runtime Stage ===
FROM openjdk:17-jdk-slim
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy JAR từ build stage
COPY --from=build /app/ProductService/target/*.jar ProductService.jar

EXPOSE 9002
ENTRYPOINT ["java", "-jar", "ProductService.jar"]
