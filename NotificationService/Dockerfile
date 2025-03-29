FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./NotificationService ./NotificationService
WORKDIR /app/NotificationService
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/NotificationService/target/*.war NotificationService.war
EXPOSE 9004
ENTRYPOINT ["java", "-jar", "NotificationService.war"]