#!/bin/bash

echo "1) CREATE BUILD -->"
mvn clean package -DskipTests -P staging

echo "2) COMPOSE DOWN -->"
docker-compose down

echo "3) BUILD APP IMAGE -->"
docker build -t springboot-demo:latest -f Dockerfile .

echo "4) REMOVE OLD DEAD IMAGES -->"
docker rmi $(docker images -f "dangling=true" -q)

echo "5) SET ENV --> SKIP ... env set with file staging.env in docker-compose"
#source env/env.sh

echo "6) COMPOSE UP -->"
docker-compose up