# === Build Stage ===
FROM eclipse-temurin:17 AS build
WORKDIR /app

# 1. Cache dependencies qua pom.xml
COPY ./NotificationService/pom.xml ./NotificationService/pom.xml
COPY ./NotificationService/mvnw ./NotificationService/mvnw
COPY ./NotificationService/.mvn ./NotificationService/.mvn

RUN ./NotificationService/mvnw -f ./NotificationService/pom.xml dependency:go-offline

# 2. Copy toàn bộ mã nguồn
COPY ./NotificationService ./NotificationService

# 3. Build WAR file
WORKDIR /app/NotificationService
RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17
WORKDIR /app

# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy WAR file từ stage build
COPY --from=build /app/NotificationService/target/*.war NotificationService.war

EXPOSE 9004

# 5. Dùng java -jar vẫn được nếu war là Spring Boot WAR
ENTRYPOINT ["java", "-jar", "NotificationService.war"]
