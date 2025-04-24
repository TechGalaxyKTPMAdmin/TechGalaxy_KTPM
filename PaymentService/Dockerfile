# === Build Stage ===
FROM eclipse-temurin:17 AS build
WORKDIR /app

# 1. Copy pom.xml để cache dependencies
COPY ./PaymentService/pom.xml ./PaymentService/pom.xml
COPY ./PaymentService/mvnw ./PaymentService/mvnw
COPY ./PaymentService/.mvn ./PaymentService/.mvn

RUN ./PaymentService/mvnw -f ./PaymentService/pom.xml dependency:go-offline

# 2. Copy toàn bộ source code sau khi dependencies đã cache
COPY ./PaymentService ./PaymentService

# 3. Build ứng dụng
WORKDIR /app/PaymentService
RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17
WORKDIR /app

ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Ho_Chi_Minh

RUN apt-get update && \
    apt-get install -y tzdata wget && \
    ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata && \
    echo $TZ > /etc/timezone && \
    rm -rf /var/lib/apt/lists/*


# 4. Copy jar file từ stage build
COPY --from=build /app/PaymentService/target/*.jar PaymentService.jar

EXPOSE 9005
ENTRYPOINT ["java", "-jar", "PaymentService.jar"]
