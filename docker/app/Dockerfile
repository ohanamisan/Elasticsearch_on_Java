FROM openjdk:jdk-alpine

VOLUME /tmp

RUN mkdir /app

COPY jar /app

WORKDIR /app

ENTRYPOINT ["java", "-jar", "ElasticSearchOnJava.jar"]