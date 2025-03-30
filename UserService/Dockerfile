# === Build Stage ===
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# 1. Copy pom.xml để cache Maven dependencies
COPY ./UserService/pom.xml ./UserService/pom.xml
RUN mvn -f ./UserService/pom.xml dependency:go-offline

# 2. Copy toàn bộ mã nguồn
COPY ./UserService ./UserService

# 3. Build ứng dụng
WORKDIR /app/UserService
RUN mvn clean package -DskipTests

# === Runtime Stage ===
FROM openjdk:17-jdk-slim
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy JAR file vào image
COPY --from=build /app/UserService/target/*.jar UserService.jar

EXPOSE 9001
ENTRYPOINT ["java", "-jar", "UserService.jar"]
