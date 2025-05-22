# === Build Stage ===
FROM eclipse-temurin:17 AS build
WORKDIR /app

# 1. Copy pom.xml để Maven tải dependencies (tận dụng cache)
COPY ./APIGateway/pom.xml ./APIGateway/pom.xml
COPY ./APIGateway/mvnw ./APIGateway/mvnw
COPY ./APIGateway/.mvn ./APIGateway/.mvn

RUN chmod +x ./APIGateway/mvnw

RUN ./APIGateway/mvnw -f ./APIGateway/pom.xml dependency:go-offline

# 2. Copy toàn bộ mã nguồn sau
COPY ./APIGateway ./APIGateway

# 3. Build project
WORKDIR /app/APIGateway
RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy jar từ stage build
COPY --from=build /app/APIGateway/target/*.jar APIGateway.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "APIGateway.jar"]
