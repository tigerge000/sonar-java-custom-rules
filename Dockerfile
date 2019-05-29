FROM registry.cn-hangzhou.aliyuncs.com/lm93129/java:8-jdk-alpine-y
LABEL MAINTAINER yzx <yangzixi@hthjsj.com>

VOLUME /tmp

RUN mkdir /app

WORKDIR /app

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

COPY /target/*.jar /app/app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "/app/app.jar"]
