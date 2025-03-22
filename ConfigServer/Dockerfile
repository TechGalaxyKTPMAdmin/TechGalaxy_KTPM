FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./ConfigServer ./ConfigServer
WORKDIR /app/ConfigServer
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/ConfigServer/target/*.jar ConfigServer.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]
