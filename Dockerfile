# Docker Image for Jar embedded Tomcat 1
#FROM openjdk:11-slim

#COPY target/springboot-demo.jar springboot-demo.jar

#COPY env/wait-for-it.sh /wait-for-it.sh

#RUN chmod +x /wait-for-it.sh

#ENTRYPOINT ["./wait-for-it.sh", "mongo-db:27017", "--timeout=90", "--strict", "--", "java","-jar","/springboot-demo.jar"]

# Docker Image for Jar embedded Tomcat 2
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
