FROM maven:3.9.12-eclipse-temurin-21 AS builder

WORKDIR /build/app
ADD . /build/app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jammy
RUN mkdir -p /release/app
COPY --from=builder /build/app/target/springboot-demo.jar /release/app

