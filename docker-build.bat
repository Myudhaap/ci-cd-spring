@echo off
docker-compose up --build -d
docker tag ci-cd-spring:1.0.0 mayutama23/ci-cd-spring:1.0.0
docker login
docker push mayutama23/ci-cd-spring:1.0.0