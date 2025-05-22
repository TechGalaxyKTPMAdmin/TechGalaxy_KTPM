# === Build Stage ===
FROM eclipse-temurin:17 AS build
WORKDIR /app

# 1. Copy pom.xml để cache dependencies
COPY ./ConfigServer/pom.xml ./ConfigServer/pom.xml
COPY ./ConfigServer/mvnw ./ConfigServer/mvnw
COPY ./ConfigServer/.mvn ./ConfigServer/.mvn

RUN chmod +x ./ConfigServer/mvnw

RUN ./ConfigServer/mvnw -f ./ConfigServer/pom.xml dependency:go-offline

# 2. Copy toàn bộ source code
COPY ./ConfigServer ./ConfigServer

# 3. Build ứng dụng
WORKDIR /app/ConfigServer
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17
WORKDIR /app
# Cài đặt wget
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 4. Copy JAR đã build sang image runtime
COPY --from=build /app/ConfigServer/target/*.jar ConfigServer.jar

EXPOSE 8888
ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]
