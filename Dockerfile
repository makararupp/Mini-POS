FROM openjdk:17 as build

MAINTAINER makarapos

COPY target/min_pos-0.0.1-SNAPSHOT.jar min_pos-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar", "/min_pos-0.0.1-SNAPSHOT.jar"]