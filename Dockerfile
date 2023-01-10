FROM maven:3.8.3-openjdk-17-slim as builder

WORKDIR /build/app
ADD . /build/app
RUN mvn clean package -DskipTests

FROM openjdk:17-buster
RUN mkdir -p /release/app
COPY --from=builder /build/app/target/springboot-demo.jar /release/app
