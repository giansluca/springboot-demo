FROM maven:3.8.7-eclipse-temurin-17-alpine as builder

WORKDIR /build/app
ADD . /build/app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
RUN mkdir -p /release/app
COPY --from=builder /build/app/target/springboot-demo.jar /release/app

