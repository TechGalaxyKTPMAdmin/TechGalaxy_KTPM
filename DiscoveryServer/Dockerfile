FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . ./DiscoveryServer
WORKDIR /app/DiscoveryServer
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/DiscoveryServer/target/DiscoveryServer-0.0.1-SNAPSHOT.jar DiscoveryServer.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","DiscoveryServer.jar"]