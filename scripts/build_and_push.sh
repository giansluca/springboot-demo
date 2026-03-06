#!/bin/bash

# Define the image name
IMAGE_NAME="gianlucamori/springboot-demo:latest"

echo "Logging into Docker Hub..."
docker login

echo "Starting image build..."
# Note: Ensure you are in the directory containing the Dockerfile
docker buildx build -t $IMAGE_NAME .

echo "Pushing image to the registry..."
docker push $IMAGE_NAME

echo "Operation completed successfully!"
