# === Build Stage ===
FROM eclipse-temurin:17 AS build
WORKDIR /app

# 1. Copy pom.xml để cache dependencies
COPY ./OrderService/pom.xml ./OrderService/pom.xml
COPY ./OrderService/mvnw ./OrderService/mvnw
COPY ./OrderService/.mvn ./OrderService/.mvn

RUN ./OrderService/mvnw -f ./OrderService/pom.xml dependency:go-offline

# 2. Copy toàn bộ mã nguồn sau khi dependencies đã cache
COPY ./OrderService ./OrderService

# 3. Build ứng dụng
WORKDIR /app/OrderService
RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy jar file đã build sang runtime image
COPY --from=build /app/OrderService/target/*.jar OrderService.jar

EXPOSE 9003
ENTRYPOINT ["java", "-jar", "OrderService.jar"]
