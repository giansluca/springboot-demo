FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /build/app
ADD . /build/app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-noble
RUN mkdir -p /release/app
COPY --from=builder /build/app/target/springboot-demo.jar /release/app

ENTRYPOINT ["java"]
CMD ["-jar", "/release/app/springboot-demo.jar"]

