FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./UserService ./UserService
WORKDIR /app/UserService
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/UserService/target/*.jar UserService.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "UserService.jar"]
