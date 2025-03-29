FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./PaymentService ./PaymentService
WORKDIR /app/PaymentService
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/PaymentService/target/*.jar PaymentService.jar
EXPOSE 9005
ENTRYPOINT ["java", "-jar", "PaymentService.jar"]