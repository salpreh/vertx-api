# Products

<img src="https://img.shields.io/badge/vert.x-4.4.2-purple.svg">

A demo web server REST API project using [Vert.x](http://start.vertx.io).

## Building

To launch your tests:
```sh
./mvnw clean test
```

To package your application:
```sh
./mvnw clean package
```

## Running

Boot a postgres DB. There is a docker-compose file in the root of the project that will do this for you:
```sh
docker-compose up -d
```

Run flyway migrations to create the database schema:
```sh
./mvnw flyway:migrate
```

To run your application:
```sh
./mvnw clean compile exec:java
```

## Help
- [Documentation](https://vertx.io/docs/)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/vert.x?sort=newest&pageSize=15)
- [User Group](https://groups.google.com/forum/?fromgroups#!forum/vertx)
- [Discord](https://discord.gg/6ry7aqPWXy)
- [Gitter](https://gitter.im/eclipse-vertx/vertx-users)

## Ref
- https://vertx.io/docs/vertx-web-api-service/java/#_using_vert_x_api_service
- https://how-to.vertx.io/hibernate-reactive-howto/
- https://vertx.io/docs/vertx-sql-client-templates/java/#_getting_started
- https://www.zsiegel.com/2019/07/03/dependency-injection-with-vertx
- https://taraskohut.medium.com/vert-x-micronaut-do-we-need-dependency-injection-in-the-microservices-world-84e43b3b228e
