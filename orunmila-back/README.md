# Orunmila backend

Project and user management
their development and competence maps

## Used containers
- java build _adoptopenjdk/openjdk11_
- environment _alpine:3.10.3_
- database _mongo:latest_
- database management _mongo-express_

## Build
Build:
``docker image build . -t orunmila-back:1.0.0``<br/>
Run:
``docker run -d --name orunmila-back -p 8080:8080 orunmila-back:1.0.0``

## Helpfulness
- [about docker](https://habr.com/ru/company/ruvds/blog/485650/) <br/>
- [about mongo](https://www.youtube.com/watch?v=ssj0CGxv60k)

