FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./OrderService ./OrderService
WORKDIR /app/OrderService
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/OrderService/target/*.jar OrderService.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "OrderService.jar"]