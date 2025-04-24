# === Build Stage ===
FROM eclipse-temurin:17 AS build
WORKDIR /app

# 1. Copy pom.xml để cache Maven dependencies
COPY ./ProductService/pom.xml ./ProductService/pom.xml
COPY ./ProductService/mvnw ./ProductService/mvnw
COPY ./ProductService/.mvn ./ProductService/.mvn

RUN ./ProductService/mvnw -f ./ProductService/pom.xml dependency:go-offline

# 2. Copy toàn bộ mã nguồn
COPY ./ProductService ./ProductService

# 3. Build ứng dụng
WORKDIR /app/ProductService
RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy JAR từ build stage
COPY --from=build /app/ProductService/target/*.jar ProductService.jar

EXPOSE 9002
ENTRYPOINT ["java", "-jar", "ProductService.jar"]
