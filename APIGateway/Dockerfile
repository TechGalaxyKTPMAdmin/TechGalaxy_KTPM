FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./APIGateway ./APIGateway
WORKDIR /app/APIGateway
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/APIGateway/target/*.jar APIGateway.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "APIGateway.jar"]
