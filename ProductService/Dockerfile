FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./ProductService ./ProductService
WORKDIR /app/ProductService
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/ProductService/target/*.jar ProductService.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "ProductService.jar"]