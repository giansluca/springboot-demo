FROM maven:3.8.3-openjdk-17-slim as builder

WORKDIR /usr/app
ADD pom.xml /app/pom.xml
RUN mvn -s settings.xml clean dependency:go-offline
ADD . /app
RUN mvn -s settings.xml package -DskipTests

RUN mkdir -p /opt/app
COPY --from=builder /app/springboot-demo/target/springboot-demo.jar /opt/app
