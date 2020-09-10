FROM openjdk:11-slim

WORKDIR /usr/app

ENV DOCKERIZE_VERSION v0.6.1

RUN apt update && apt -y install wget

RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

COPY target/springboot-demo.jar springboot-demo.jar

CMD ["dockerize", "-wait", "tcp://mongo-db:27017", \
            "-wait", "tcp://postgres-db:5432", "-timeout", "60s", \
            "--" \
            ,"java", "-jar", "./springboot-demo.jar"]
