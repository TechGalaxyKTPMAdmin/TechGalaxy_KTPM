FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./InventoryService ./InventoryService
WORKDIR /app/InventoryService
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/InventoryService/target/*.jar InventoryService.jar
EXPOSE 9006
ENTRYPOINT ["java", "-jar", "InventoryService.jar"]
