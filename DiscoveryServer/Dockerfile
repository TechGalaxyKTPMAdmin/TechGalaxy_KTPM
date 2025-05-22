# === Build Stage ===
FROM eclipse-temurin:17 AS build

WORKDIR /app

# 1. Copy pom.xml trước để cache dependencies
COPY ./DiscoveryServer/pom.xml ./DiscoveryServer/pom.xml
COPY ./DiscoveryServer/mvnw ./DiscoveryServer/mvnw
COPY ./DiscoveryServer/.mvn ./DiscoveryServer/.mvn

RUN chmod +x ./DiscoveryServer/mvnw

RUN ./DiscoveryServer/mvnw -f ./DiscoveryServer/pom.xml dependency:go-offline

# 2. Copy toàn bộ source code
COPY ./DiscoveryServer ./DiscoveryServer

# 3. Build project
WORKDIR /app/DiscoveryServer
RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy jar file từ stage build
COPY --from=build /app/DiscoveryServer/target/*.jar DiscoveryServer.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "DiscoveryServer.jar"]